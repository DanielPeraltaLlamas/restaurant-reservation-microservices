package pt.ulusofona.cd.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ulusofona.cd.restaurant.model.Rating;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByRestaurantId(Long restaurantId);

    List<Rating> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.restaurantId = :restaurantId")
    Optional<Double> findAverageScoreByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.restaurantId = :restaurantId")
    Long countByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.restaurantId = :restaurantId AND r.score = :score")
    Long countByRestaurantIdAndScore(@Param("restaurantId") Long restaurantId, @Param("score") Integer score);

    boolean existsByRestaurantIdAndCustomerEmail(Long restaurantId, String customerEmail);
}

