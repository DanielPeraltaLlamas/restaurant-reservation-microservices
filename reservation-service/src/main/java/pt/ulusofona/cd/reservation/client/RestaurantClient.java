package pt.ulusofona.cd.reservation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.reservation.dto.AvailabilitySlotRequest;
import pt.ulusofona.cd.reservation.dto.AvailabilitySlotResponse;
import pt.ulusofona.cd.reservation.dto.RestaurantResponse;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@FeignClient(name = "restaurant-service", url = "http://restaurant-service:8081")
public interface RestaurantClient {

    @GetMapping("/api/restaurants/{restaurantId}")
    RestaurantResponse getRestaurant(@PathVariable("restaurantId") Long restaurantId);

    @GetMapping("/api/restaurants/slots")
    List<AvailabilitySlotResponse> getAllSlots();

    @PutMapping("/api/restaurants/slots/{id}")
    AvailabilitySlotResponse updateAvailabilitySlot(@PathVariable("id") Long id, @RequestBody AvailabilitySlotRequest request);





}