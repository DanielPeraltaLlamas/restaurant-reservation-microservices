package pt.ulusofona.cd.reservation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.reservation.dto.AvailabilitySlotDto;
import pt.ulusofona.cd.reservation.dto.RestaurantDto;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@FeignClient(name = "restaurant-service", url = "http://restaurant-service:8081", contextId = "restaurant-client")
public interface RestaurantClient {

    @GetMapping("/api/restaurants/{id}")
    RestaurantDto getRestaurant(@PathVariable("id") Long restaurantId);

    @GetMapping("/api/restaurants/slots")
    List<AvailabilitySlotDto> getAllSlots();

    @PutMapping("/api/restaurants/slots/{id}")
    AvailabilitySlotDto updateAvailabilitySlot(@PathVariable("id") Long id, @RequestBody AvailabilitySlotDto request);





}
