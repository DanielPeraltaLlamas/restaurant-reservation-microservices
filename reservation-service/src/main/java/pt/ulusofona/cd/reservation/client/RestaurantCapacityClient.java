package pt.ulusofona.cd.reservation.client;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pt.ulusofona.cd.reservation.dto.SeatsRequest;

@FeignClient(name = "restaurant-service", url = "http://restaurant-service:8081", contextId = "restaurant-capacity-client")
public interface RestaurantCapacityClient {
    @PostMapping("/api/restaurants/{id}/availability/release")
    void releaseSeats(@PathVariable Long id, @RequestBody SeatsRequest req);
    @PostMapping("/api/restaurants/{id}/availability/confirm")
    void confirmSeats(@PathVariable Long id, @RequestBody SeatsRequest req);
}
