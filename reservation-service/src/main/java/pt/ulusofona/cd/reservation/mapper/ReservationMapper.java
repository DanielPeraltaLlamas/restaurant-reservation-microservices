package pt.ulusofona.cd.reservation.mapper;

import pt.ulusofona.cd.reservation.dto.ReservationRequest;
import pt.ulusofona.cd.reservation.dto.ReservationResponse;
import pt.ulusofona.cd.reservation.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;


public class ReservationMapper
{

    public static Reservation toEntity(ReservationRequest dto)
    {
        Reservation reservation = new Reservation();
        reservation.setRestaurantId(dto.getRestaurantId());
        reservation.setAvailabilitySlotId(dto.getAvailabilitySlotId());
        reservation.setCustomerName(dto.getCustomerName());
        reservation.setCustomerEmail(dto.getCustomerEmail());
        reservation.setCustomerPhone(dto.getCustomerPhone());
        reservation.setPartySize(dto.getPartySize());
        reservation.setScheduledAt(dto.getScheduledAt());
        reservation.setStatus("PENDING");

        return reservation;
    }

    public static ReservationResponse toResponse(Reservation entity)
    {
        ReservationResponse response = new ReservationResponse();
        response.setId(entity.getId());
        response.setRestaurantId(entity.getRestaurantId());
        response.setAvailabilitySlotId(entity.getAvailabilitySlotId());
        response.setCustomerName(entity.getCustomerName());
        response.setCustomerEmail(entity.getCustomerEmail());
        response.setCustomerPhone(entity.getCustomerPhone());
        response.setPartySize(entity.getPartySize());
        response.setStatus(entity.getStatus());
        response.setScheduledAt(entity.getScheduledAt());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setConfirmedAt(entity.getConfirmedAt());
        response.setCancelledAt(entity.getCancelledAt());

        return response;
    }

}
