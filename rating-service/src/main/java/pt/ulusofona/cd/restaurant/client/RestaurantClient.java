package pt.ulusofona.cd.restaurant.client;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.ulusofona.cd.restaurant.dto.RestaurantResponse;

@FeignClient(name = "restaurant-service", url = "http://restaurant-service:8081", contextId = "restaurant-client")
public interface RestaurantClient {

    @GetMapping("/api/restaurants/{id}")
    ResponseEntity<RestaurantResponse> getRestaurant(@PathVariable("id") Long restaurantId);
}

