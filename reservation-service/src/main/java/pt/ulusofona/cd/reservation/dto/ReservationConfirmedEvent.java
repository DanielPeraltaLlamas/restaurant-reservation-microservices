package pt.ulusofona.cd.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant confirmedAt;
}