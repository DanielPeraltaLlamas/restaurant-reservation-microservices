package pt.ulusofona.cd.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ulusofona.cd.restaurant.model.MenuItem;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long>
{
    List<MenuItem> findByRestaurantId(Long restaurantId);
}
