package pt.ulusofona.cd.store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "notification_id", nullable = false, length = 50)
    private String notificationId;

    @Column(name = "reservation_id", nullable = false, length = 50)
    private String reservationId;

    @Column(name = "restaurant_recipient", nullable = false, length = 150)
    private String recipient;

    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType; // reservation.created / confirmed / cancelled

    @Column(name = "status", nullable = false, length = 20)
    private String status; // SENT, FAILED, RETRYING

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "trace_id", nullable = false, length = 100)
    private String traceId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}

