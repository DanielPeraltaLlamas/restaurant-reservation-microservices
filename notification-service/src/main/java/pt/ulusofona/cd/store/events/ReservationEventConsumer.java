package pt.ulusofona.cd.store.events;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import pt.ulusofona.cd.store.dto.MessageEnvelope;
import pt.ulusofona.cd.store.dto.ReservationCreatedEvent;
import pt.ulusofona.cd.store.dto.ReservationConfirmedEvent;
import pt.ulusofona.cd.store.dto.ReservationCancelledEvent;
import pt.ulusofona.cd.store.service.NotificationService;

@Component
@RequiredArgsConstructor
public class ReservationEventConsumer {

    private final NotificationService notificationService;

    // ---------------------------------------------------------
    //   EVENTO: RESERVATION CREATED
    // ---------------------------------------------------------
    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 3000, multiplier = 2.0),
            dltTopicSuffix = ".DLT"
    )
    @KafkaListener(
            topics = "${reservation.events.reservation-created-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onReservationCreated(MessageEnvelope<ReservationCreatedEvent> message) {

        System.out.println("Evento recibido: " + message.getType());
        System.out.println("ReservationId: " + message.getPayload().getReservationId());
        System.out.println("Correlation ID: " + message.getCorrelationId());
        System.out.println("Timestamp: " + message.getTimestamp());

        ReservationCreatedEvent payload = message.getPayload();

        if (payload.getReservationId().startsWith("FAIL")) {
            throw new RuntimeException("Fallo simulado para " + payload.getReservationId());
        }

        notificationService.handleReservationCreated(payload, message.getCorrelationId());
    }

    // ---------------------------------------------------------
    //   EVENTO: RESERVATION CONFIRMED
    // ---------------------------------------------------------
    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 3000, multiplier = 2.0),
            dltTopicSuffix = ".DLT"
    )
    @KafkaListener(
            topics = "${reservation.events.reservation-confirmed-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onReservationConfirmed(MessageEnvelope<ReservationConfirmedEvent> message) {

        System.out.println("Evento recibido: " + message.getType());
        System.out.println("ReservationId: " + message.getPayload().getReservationId());
        System.out.println("Correlation ID: " + message.getCorrelationId());
        System.out.println("Timestamp: " + message.getTimestamp());

        ReservationConfirmedEvent payload = message.getPayload();

        if (payload.getReservationId().startsWith("FAIL")) {
            throw new RuntimeException("Fallo simulado para " + payload.getReservationId());
        }

        notificationService.handleReservationConfirmed(payload, message.getCorrelationId());
    }

    // ---------------------------------------------------------
    //   EVENTO: RESERVATION CANCELLED
    // ---------------------------------------------------------
    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 3000, multiplier = 2.0),
            dltTopicSuffix = ".DLT"
    )
    @KafkaListener(
            topics = "${reservation.events.reservation-cancelled-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onReservationCancelled(MessageEnvelope<ReservationCancelledEvent> message) {

        System.out.println("Evento recibido: " + message.getType());
        System.out.println("ReservationId: " + message.getPayload().getReservationId());
        System.out.println("Correlation ID: " + message.getCorrelationId());
        System.out.println("Timestamp: " + message.getTimestamp());

        ReservationCancelledEvent payload = message.getPayload();

        if (payload.getReservationId().startsWith("FAIL")) {
            throw new RuntimeException("Fallo simulado para " + payload.getReservationId());
        }

        notificationService.handleReservationCancelled(payload, message.getCorrelationId());
    }

    // ---------------------------------------------------------
    //   DEAD LETTER TOPIC HANDLER
    // ---------------------------------------------------------
    @DltHandler
    public void handleDlt(Object failedMessage) {
        System.err.println("Mensaje enviado al DLT: " + failedMessage);
    }
}

