package pt.ulusofona.cd.reservation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ulusofona.cd.reservation.dto.AvailabilitySlotDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "restaurant-service", url = "http://restaurant-service:8081", contextId = "restaurant-availability-client")
public interface RestaurantAvailabilityClient
{
    @GetMapping("/api/restaurants/{id}/availability")
    List<AvailabilitySlotDto> getAvailability(
            @PathVariable Long id,
            @RequestParam LocalDateTime dateTime,
            @RequestParam int partySize);
}
