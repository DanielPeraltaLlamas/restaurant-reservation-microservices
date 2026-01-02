package pt.ulusofona.cd.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant cancelledAt;
}
