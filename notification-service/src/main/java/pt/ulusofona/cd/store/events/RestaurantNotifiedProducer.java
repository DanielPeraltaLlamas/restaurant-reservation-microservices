package pt.ulusofona.cd.store.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pt.ulusofona.cd.store.dto.MessageEnvelope;
import pt.ulusofona.cd.store.dto.RestaurantNotifiedPayload;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantNotifiedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${notification.events.restaurant-notified-events}")
    private String RESTAURANT_NOTIFIED_TOPIC;

    public void sendRestaurantNotifiedEvent(RestaurantNotifiedPayload payload) {

        MessageEnvelope<RestaurantNotifiedPayload> envelope =
                new MessageEnvelope<>(
                        "notification.sent",          // type
                        payload.getStatus(),          // SENT
                        payload.getOccurredAt(),      // timestamp
                        payload.getTraceId(),         // correlationId
                        payload                       // payload
                );

        kafkaTemplate.send(RESTAURANT_NOTIFIED_TOPIC, payload.getReservationId(), envelope)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Sent RestaurantNotified event: " + payload.getNotificationId());
                    } else {
                        System.err.println("Failed to send RestaurantNotified event: " + ex.getMessage());
                    }
                });
    }
}

