package pt.ulusofona.cd.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationConfirmedEvent
{
    private String reservationId;
    private Long restaurantId;
    private Long availabilitySlotId;
    private String customerEmail;
    private LocalDateTime scheduledAt;
    private Instant confirmedAt;
}