package pt.ulusofona.cd.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationConfirmedEvent
{
    private String reservationId;
    private Long restaurantId;
    private String customerEmail;
    private Instant scheduledAt;
    private Instant confirmedAt;
}