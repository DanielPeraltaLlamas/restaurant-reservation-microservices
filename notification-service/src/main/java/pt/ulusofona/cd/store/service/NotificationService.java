package pt.ulusofona.cd.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.store.dto.RestaurantNotifiedPayload;
import pt.ulusofona.cd.store.events.RestaurantNotifiedProducer;
import pt.ulusofona.cd.store.model.Notification;
import pt.ulusofona.cd.store.repository.*;
import pt.ulusofona.cd.store.dto.ReservationCreatedEvent;
import pt.ulusofona.cd.store.dto.ReservationConfirmedEvent;
import pt.ulusofona.cd.store.dto.ReservationCancelledEvent;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RestaurantNotifiedProducer restaurantNotifiedProducer;
    private final NotificationRepository notificationRepository;

    // ---------------------------------------------------------
    //   RESERVATION CREATED
    // ---------------------------------------------------------
    @Transactional
    public void handleReservationCreated(ReservationCreatedEvent event, String traceId) {

        // 1. Enviar notificación (email, webhook, etc.)
        sendNotificationToRestaurant(event.getRestaurantId(), event.getCustomerEmail());

        // 2. Crear payload del evento de auditoría
        RestaurantNotifiedPayload payload = new RestaurantNotifiedPayload(
                "reservation.created",
                "SENT",
                Instant.now(),
                traceId,
                UUID.randomUUID().toString(),
                event.getReservationId(),
                event.getCustomerEmail()
        );

        // 3. Guardar en BD
        saveNotification(payload);

        // 4. Producir evento Kafka
        restaurantNotifiedProducer.sendRestaurantNotifiedEvent(payload);
    }

    // ---------------------------------------------------------
    //   RESERVATION CONFIRMED
    // ---------------------------------------------------------
    @Transactional
    public void handleReservationConfirmed(ReservationConfirmedEvent event, String traceId) {

        sendNotificationToRestaurant(event.getRestaurantId(), event.getCustomerEmail());

        RestaurantNotifiedPayload payload = new RestaurantNotifiedPayload(
                "reservation.confirmed",
                "SENT",
                Instant.now(),
                traceId,
                UUID.randomUUID().toString(),
                event.getReservationId(),
                event.getCustomerEmail()
        );

        saveNotification(payload);
        restaurantNotifiedProducer.sendRestaurantNotifiedEvent(payload);
    }

    // ---------------------------------------------------------
    //   RESERVATION CANCELLED
    // ---------------------------------------------------------
    @Transactional
    public void handleReservationCancelled(ReservationCancelledEvent event, String traceId) {

        sendNotificationToRestaurant(event.getRestaurantId(), event.getCustomerEmail());

        RestaurantNotifiedPayload payload = new RestaurantNotifiedPayload(
                "reservation.cancelled",
                "SENT",
                Instant.now(),
                traceId,
                UUID.randomUUID().toString(),
                event.getReservationId(),
                event.getCustomerEmail()
        );

        saveNotification(payload);
        restaurantNotifiedProducer.sendRestaurantNotifiedEvent(payload);
    }

    // ---------------------------------------------------------
    //   MÉTODOS INTERNOS
    // ---------------------------------------------------------

    private void sendNotificationToRestaurant(Long restaurantId, String recipient) {
        System.out.println("Notificando al restaurante " + restaurantId + " → " + recipient);
        // Aquí iría tu lógica real de email/webhook
    }

    private void saveNotification(RestaurantNotifiedPayload payload) {
        Notification notification = new Notification(
                null,
                payload.getNotificationId(),
                payload.getReservationId(),
                payload.getRecipient(),
                payload.getEventType(),
                payload.getStatus(),
                payload.getOccurredAt(),
                payload.getTraceId(),
                null,
                null
        );
        notificationRepository.save(notification);
    }
}

