package pt.ulusofona.cd.store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantNotifiedPayload {

    private String eventType;     // reservation.created / reservation.confirmed / reservation.cancelled
    private String status;        // SENT, FAILED, RETRYING

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant occurredAt;

    private String traceId;

    private String notificationId;
    private String reservationId;
    private String recipient;
}

