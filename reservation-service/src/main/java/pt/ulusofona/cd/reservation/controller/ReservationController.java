package pt.ulusofona.cd.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.reservation.dto.ReservationRequest;
import pt.ulusofona.cd.reservation.dto.ReservationResponse;
import pt.ulusofona.cd.reservation.mapper.ReservationMapper;
import pt.ulusofona.cd.reservation.mapper.ReservationMapper;
import pt.ulusofona.cd.reservation.model.Reservation;

import jakarta.validation.Valid;
import pt.ulusofona.cd.reservation.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController
{

    private final ReservationService reservationService;

    //Create reservation
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest reservation)
    {
        ReservationResponse created = reservationService.createReservation(reservation);

        return ResponseEntity.ok(created);
    }

    //Confirm reservation
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<ReservationResponse> confirmReservation(@PathVariable Long id)
    {
        ReservationResponse confirmed = reservationService.confirmReservation(id);
        return ResponseEntity.ok(confirmed);
    }

    //Cancel reservation
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable Long id)
    {
        ReservationResponse response = reservationService.cancelReservation(id);
        //reservationService.discontinueProducts(cancelled.getId());
        return ResponseEntity.ok(response);
    }


    //Get all reservations
    @GetMapping public ResponseEntity<List<ReservationResponse>> getAllReservations()
    {
        List<ReservationResponse> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    //Get reservations of a restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByRestaurant (@PathVariable Long restaurantId)
    {
        List<ReservationResponse> reservations = reservationService.getReservationsByRestaurant(restaurantId);
        return ResponseEntity.ok(reservations);
    }

    //All reservations in a specific date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByDate(@PathVariable String date)
    {
        List<ReservationResponse> reservations = reservationService.getReservationsByDate(date);
        return ResponseEntity.ok(reservations);
    }


    //Update reservation
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable Long id, @Valid @RequestBody ReservationRequest request)
    {
        ReservationResponse response = reservationService.updateReservation(id, request);
        return ResponseEntity.ok(response);
    }

    //Reservations in a specific status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByStatus(@PathVariable String status)
    {
        List<ReservationResponse> reservations = reservationService.getReservationsByStatus(status);
        return ResponseEntity.ok(reservations);
    }

    //Delete reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id)
    {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }



}