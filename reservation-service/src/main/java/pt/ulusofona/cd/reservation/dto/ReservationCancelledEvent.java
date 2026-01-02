package pt.ulusofona.cd.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCancelledEvent
{
    private String reservationId;
    private Long restaurantId;
    private Long availabilitySlotId;
    private String customerEmail;
    private String previousStatus;
    private String cancellationReason;
    private Instant cancelledAt;
}
