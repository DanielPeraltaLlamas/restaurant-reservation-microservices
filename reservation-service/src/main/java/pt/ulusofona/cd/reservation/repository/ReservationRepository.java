package pt.ulusofona.cd.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ulusofona.cd.reservation.model.Reservation;

import java.time.Instant;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>
{
    List<Reservation> findByRestaurantId(Long restaurantId);
    List<Reservation> findByRestaurantIdOrderByScheduledAtAsc(Long restaurantId);
    List<Reservation> findByRestaurantIdAndStatus(Long restaurantId, String status);

    List<Reservation> findByCustomerEmail(String customerEmail);
    List<Reservation> findByCustomerEmailOrderByScheduledAtDesc(String customerEmail);
    List<Reservation> findByCustomerEmailAndStatus(String customerEmail, String status);

    List<Reservation> findByStatus(String status);
    List<Reservation> findByStatusOrderByScheduledAtAsc(String status);

    List<Reservation> findByScheduledAtBetween(Instant start, Instant end);
    List<Reservation> findByRestaurantIdAndScheduledAtBetween(Long restaurantId, Instant start, Instant end);
}
