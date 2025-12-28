package pt.ulusofona.cd.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ulusofona.cd.restaurant.model.Restaurant;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


}
