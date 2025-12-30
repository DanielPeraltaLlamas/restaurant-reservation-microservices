package pt.ulusofona.cd.reservation.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pt.ulusofona.cd.reservation.dto.*;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationEventProducer
{

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${reservation.events.reservation-created-events}")
    private String RESERVATION_CREATED_TOPIC;

    @Value("${reservation.events.reservation-confirmed-events}")
    private String RESERVATION_CONFIRMED_TOPIC;

    @Value("${reservation.events.reservation-cancelled-events}")
    private String RESERVATION_CANCELLED_TOPIC;

    public  void sendReservationCreatedEvent(ReservationCreatedEvent payload, String traceId) {
        MessageEnvelope<ReservationCreatedEvent> envelope = new MessageEnvelope<>(
                "reservation.created",
                "PENDING",
                Instant.now(),
                traceId,
                payload
        );

        kafkaTemplate.send(RESERVATION_CREATED_TOPIC, payload.getReservationId(), envelope)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Sent ReservationCreated event: " + payload.getReservationId());
                    } else {
                        System.err.println("Failed to send ReservationCreated event: " + ex.getMessage());
                    }
                });
    }

    public  void sendReservationConfirmedEvent(ReservationConfirmedEvent payload, String traceId) {
        MessageEnvelope<ReservationConfirmedEvent> envelope = new MessageEnvelope<>("reservation.confirmed", "CONFIRMED", Instant.now(), traceId, payload);

        kafkaTemplate.send(RESERVATION_CONFIRMED_TOPIC, payload.getReservationId(), envelope)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Sent ReservationConfirmed event: " + payload.getReservationId());
                    } else {
                        System.err.println("Failed to send ReservationConfirmed event: " + ex.getMessage());
                    }
                });
    }

    public  void sendReservationCancelledEvent(ReservationCancelledEvent payload, String traceId) {
        MessageEnvelope<ReservationCancelledEvent> envelope = new MessageEnvelope<>(
                "reservation.cancelled",
                "CANCELLED",
                Instant.now(),
                traceId,
                payload
        );

        kafkaTemplate.send(RESERVATION_CANCELLED_TOPIC, payload.getReservationId(), envelope)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Sent ReservationCancelled event: " + payload.getReservationId());
                    } else {
                        System.err.println("Failed to send ReservationCancelled event: " + ex.getMessage());
                    }
                });
    }




}