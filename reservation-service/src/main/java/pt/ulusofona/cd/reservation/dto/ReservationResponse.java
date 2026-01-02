package pt.ulusofona.cd.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponse
{
    private Long id;
    private Long restaurantId;
    private Long availabilitySlotId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Integer partySize;
    private String status;
    private LocalDateTime scheduledAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant confirmedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant cancelledAt;
}