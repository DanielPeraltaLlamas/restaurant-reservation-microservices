package pt.ulusofona.cd.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.reservation.client.RestaurantAvailabilityClient;
import pt.ulusofona.cd.reservation.client.RestaurantCapacityClient;
import pt.ulusofona.cd.reservation.client.RestaurantClient;
import pt.ulusofona.cd.reservation.dto.*;
import pt.ulusofona.cd.reservation.events.ReservationEventProducer;
import pt.ulusofona.cd.reservation.exception.*;
import pt.ulusofona.cd.reservation.mapper.ReservationMapper;
import pt.ulusofona.cd.reservation.model.Reservation;
import pt.ulusofona.cd.reservation.repository.ReservationRepository;

import java.time.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService
{

    private final ReservationRepository reservationRepository;
    private final RestaurantAvailabilityClient restaurantAvailabilityClient;
    private final RestaurantCapacityClient restaurantCapacityClient;
    private final RestaurantClient restaurantClient;
    private final ReservationEventProducer reservationEventProducer;


    @Transactional
    public ReservationResponse createReservation(ReservationRequest request)
    {

        Instant now = Instant.now();
        if (request.getScheduledAt().isBefore(LocalDateTime.now()))
        {
            throw new IllegalArgumentException("scheduledAt must be in the future");
        }

        if (request.getPartySize() <= 0)
        {
            throw new IllegalArgumentException("partySize must be greater than zero");
        }

        //Check if restaurant exists
        try {
            restaurantClient.getRestaurant(request.getRestaurantId());
        } catch (Exception e) {
            throw new RestaurantNotFoundException(request.getRestaurantId());
        }

        //Check if slot is available
        boolean available = false;
        List<AvailabilitySlotDto> availableSlots = restaurantAvailabilityClient.getAvailability(request.getRestaurantId(), request.getScheduledAt(), request.getPartySize());
        for (AvailabilitySlotDto slot:availableSlots)
        {
            //Slot found
            if(slot.getId().equals(request.getAvailabilitySlotId()))    available = true;
        }

        if(!available)  throw new IllegalArgumentException("Availability slot is not valid.");

        Reservation reservation = ReservationMapper.toEntity(request);
        reservation = reservationRepository.save(reservation);


        if (reservation.getId() == null) {
            throw new IllegalStateException("Failed to generate reservation ID");
        }


        ReservationCreatedEvent event = new ReservationCreatedEvent(
                reservation.getId().toString(),
                reservation.getRestaurantId(),
                reservation.getAvailabilitySlotId(),
                reservation.getCustomerName(),
                reservation.getCustomerEmail(),
                reservation.getCustomerPhone(),
                reservation.getPartySize(),
                reservation.getScheduledAt(),
                reservation.getCreatedAt()
        );

        String traceId = UUID.randomUUID().toString();
        reservationEventProducer.sendReservationCreatedEvent(event, traceId);



        return ReservationMapper.toResponse(reservation);
    }

    public ReservationResponse getReservationById(Long id)
    {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
        return ReservationMapper.toResponse(reservation);
    }

    public List<ReservationResponse> getAllReservations()
    {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ReservationResponse> getReservationsByRestaurant(Long restaurantId)
    {

        try {

            restaurantClient.getRestaurant(restaurantId);
        } catch (Exception e) {
            throw new RestaurantNotFoundException(restaurantId);
        }

        return reservationRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(ReservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ReservationResponse> getReservationsByDate(String date)
    {
        LocalDate localDate;
        try
        {
            localDate = LocalDate.parse(date);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }


        Instant startOfDay = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        return reservationRepository.findByScheduledAtBetween(startOfDay, endOfDay)
                .stream()
                .map(ReservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ReservationResponse> getReservationsByCustomer(String email)
    {
        return reservationRepository.findByCustomerEmail(email)
                .stream()
                .map(ReservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponse updateReservation(Long id, ReservationRequest request)
    {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));


        if (request.getRestaurantId() != null &&
                !request.getRestaurantId().equals(reservation.getRestaurantId())) {
            try {
                restaurantClient.getRestaurant(request.getRestaurantId());
            } catch (Exception e) {
                throw new RestaurantNotFoundException(request.getRestaurantId());
            }
        }


        if (request.getScheduledAt() != null &&
                request.getScheduledAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("scheduledAt must be in the future");
        }


        if (request.getPartySize() != null && request.getPartySize() <= 0) {
            throw new IllegalArgumentException("partySize must be greater than zero");
        }


        if (!"PENDING".equals(reservation.getStatus()) &&
                !"CONFIRMED".equals(reservation.getStatus())) {
            throw new IllegalArgumentException("Cannot update reservation with status: " + reservation.getStatus());
        }


        if (request.getCustomerName() != null) {
            reservation.setCustomerName(request.getCustomerName());
        }

        if (request.getCustomerEmail() != null) {
            reservation.setCustomerEmail(request.getCustomerEmail());
        }

        if (request.getCustomerPhone() != null) {
            reservation.setCustomerPhone(request.getCustomerPhone());
        }


        reservationRepository.save(reservation);

        return ReservationMapper.toResponse(reservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));


        if (!"PENDING".equals(reservation.getStatus())) {
            throw new IllegalArgumentException(
                    "Cannot delete reservation with status: " + reservation.getStatus());
        }

        reservationRepository.delete(reservation);
    }

    public List<ReservationResponse> getReservationsByStatus(String status) {

        List<String> validStatuses = List.of("PENDING", "CONFIRMED", "CANCELLED");
        if (!validStatuses.contains(status.toUpperCase())) {
            throw new IllegalArgumentException("Invalid status. Allowed values: " + validStatuses);
        }

        return reservationRepository.findByStatus(status.toUpperCase())
                .stream()
                .map(ReservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponse confirmReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));


        if (!"PENDING".equals(reservation.getStatus())) {
            throw new IllegalArgumentException(
                    reservation.getStatus()+
                    "confirm. Only PENDING reservations can be confirmed."
            );
        }

        
        restaurantCapacityClient.confirmSeats(reservation.getRestaurantId(), new SeatsRequest(reservation.getAvailabilitySlotId(), reservation.getPartySize()));

        reservation.setStatus("CONFIRMED");
        reservation.setConfirmedAt(Instant.now());
        reservationRepository.save(reservation);



        ReservationConfirmedEvent event = new ReservationConfirmedEvent(
                reservation.getId().toString(),
                reservation.getRestaurantId(),
                reservation.getAvailabilitySlotId(),
                reservation.getCustomerEmail(),
                reservation.getScheduledAt(),
                reservation.getConfirmedAt()
        );

        String traceId = UUID.randomUUID().toString();
        reservationEventProducer.sendReservationConfirmedEvent(event, traceId);


        return ReservationMapper.toResponse(reservation);
    }

    @Transactional
    public ReservationResponse cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));


        if (!"PENDING".equals(reservation.getStatus()) &&
                !"CONFIRMED".equals(reservation.getStatus())) {
            throw new IllegalArgumentException(
                    reservation.getStatus()+
                    "cancel. Only PENDING or CONFIRMED reservations can be cancelled."
            );
        }

        String previousStatus = reservation.getStatus();
        reservation.setStatus("CANCELLED");
        reservation.setCancelledAt(Instant.now());
        reservationRepository.save(reservation);


        if ("CONFIRMED".equals(previousStatus))
        {
            //Seats are available again
            restaurantCapacityClient.releaseSeats(reservation.getRestaurantId(), new SeatsRequest(reservation.getAvailabilitySlotId(), reservation.getPartySize()));
        }

        ReservationCancelledEvent event = new ReservationCancelledEvent(
                reservation.getId().toString(),
                reservation.getRestaurantId(),
                reservation.getAvailabilitySlotId(),
                reservation.getCustomerEmail(),
                previousStatus,
                "Customer cancellation",
                reservation.getCancelledAt()
        );

        String traceId = UUID.randomUUID().toString();
        reservationEventProducer.sendReservationCancelledEvent(event, traceId);

        return ReservationMapper.toResponse(reservation);
    }


}

