package pt.ulusofona.cd.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreatedEvent
{
    private String reservationId;
    private Long restaurantId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Integer partySize;
    private Instant scheduledAt;
    private Instant createdAt;
}