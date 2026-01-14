package pt.ulusofona.cd.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
//Dont need it because i created inside the payload
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantNotifiedEvent<T> {
    private String eventType;
    private String status;
    private String occurredAt;
    private String traceId;
    private T payload;
}
