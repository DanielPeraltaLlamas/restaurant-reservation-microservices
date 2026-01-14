package pt.ulusofona.cd.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ulusofona.cd.store.model.Notification;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {


    Optional<Notification> findByNotificationId(String notificationId);
    List<Notification> findByReservationId(String reservationId);
    List<Notification> findByStatus(String status);
    List<Notification> findByRecipient(String recipient);
    @Query("SELECT n FROM Notification n WHERE n.status = :status")
    List<Notification> findByStatusQuery(@Param("status") String status);
}


