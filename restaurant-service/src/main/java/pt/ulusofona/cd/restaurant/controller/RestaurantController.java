package pt.ulusofona.cd.restaurant.controller;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.restaurant.dto.*;
import pt.ulusofona.cd.restaurant.mapper.AvailabilitySlotMapper;
import pt.ulusofona.cd.restaurant.mapper.MenuItemMapper;
import pt.ulusofona.cd.restaurant.mapper.RestaurantMapper;
import pt.ulusofona.cd.restaurant.model.AvailabilitySlot;
import pt.ulusofona.cd.restaurant.model.MenuItem;
import pt.ulusofona.cd.restaurant.model.Restaurant;
import pt.ulusofona.cd.restaurant.service.RestaurantService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController
{
    private final RestaurantService service;

    //Create restaurant
    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantRequest request) {
        Restaurant created = service.createRestaurant(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RestaurantMapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse>  getRestaurant(@PathVariable Long id)
    {
        Restaurant restaurant = service.getRestaurantById(id);
        return ResponseEntity.ok(RestaurantMapper.toResponse(restaurant));
    }

    //Get all restaurants and menus
    @GetMapping("/menus")
    public ResponseEntity<List<RestaurantMenuResponse>>  getRestaurantMenus()
    {
        List<RestaurantMenuResponse> menus = service.getRestaurantMenus();

        return ResponseEntity.ok(menus);
    }

    //Get all available slots for a restaurant at a given date and time
    @GetMapping("/slots/available")
    public ResponseEntity<List<AvailabilitySlotResponse>>  getAvailableSlots(@RequestParam Long restaurantId, @RequestParam LocalDate date, @RequestParam LocalTime time)
    {
        List<AvailabilitySlot> slots = service.getAvailableSlots(restaurantId, date, time);
        List<AvailabilitySlotResponse> responseList = slots.stream()
                .map(AvailabilitySlotMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    //Get all available slots for a restaurant given date and party size
    @GetMapping("/{id}/availability")
    public ResponseEntity<List<AvailabilitySlotResponse>>  getAvailability(@PathVariable Long id, @RequestParam @DateTimeFormat(pattern = "d/M/yy, HH:mm") LocalDateTime dateTime, @RequestParam int partySize)
    {
        List<AvailabilitySlot> slots = service.getAvailability(id, dateTime, partySize);
        List<AvailabilitySlotResponse> responseList = slots.stream()
                .map(AvailabilitySlotMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    //Confirm seats for reservation
    @PostMapping("/{id}/availability/confirm")
    public void confirmSeats(@PathVariable Long id, @RequestBody SeatsRequest request)
    {
        service.confirmSeats(id, request);
    }

    //Release seats when reservation is cancelled
    @PostMapping("/{id}/availability/release")
    public void releaseSeats(@PathVariable Long id, @RequestBody SeatsRequest request)
    {
        service.releaseSeats(id, request);
    }

    //Create menu item
    @PostMapping("/items")
    public ResponseEntity<MenuItemResponse> createItem(@Valid @RequestBody MenuItemRequest request) {
        MenuItem created = service.createMenuItem(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MenuItemMapper.toResponse(created));
    }




    //Get all menu items
    @GetMapping("/items")
    public ResponseEntity<List<MenuItemResponse>>  getItems()
    {
        List<MenuItem> items = service.getMenuItems();
        List<MenuItemResponse> responseList = items.stream()
                .map(MenuItemMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    //Update menu item
    @PutMapping("/items/{id}")
    public ResponseEntity<MenuItemResponse> updateItem(@PathVariable Long id, @Valid @RequestBody MenuItemRequest request)
    {
        MenuItem updated = service.updateMenuItem(id, request);
        return ResponseEntity.ok(MenuItemMapper.toResponse(updated));
    }

    //Delete menu item
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id)
    {
        service.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }




    //Get all restaurants
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>>  getRestaurants()
    {
        List<Restaurant> restaurants = service.getRestaurants();
        List<RestaurantResponse> responseList = restaurants.stream()
                .map(RestaurantMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    //Update restaurant
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantRequest request)
    {
        Restaurant updated = service.updateRestaurant(id, request);
        return ResponseEntity.ok(RestaurantMapper.toResponse(updated));
    }

    //Delete restaurant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id)
    {
        service.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }



    //Create availability slot
    @PostMapping("/slots")
    public ResponseEntity<AvailabilitySlotResponse> createSlot(@Valid @RequestBody AvailabilitySlotRequest request) {
        AvailabilitySlot created = service.createAvailabilitySlot(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AvailabilitySlotMapper.toResponse(created));
    }


    //Get all slots
    @GetMapping("/slots")
    public ResponseEntity<List<AvailabilitySlotResponse>>  getSlots()
    {
        List<AvailabilitySlot> slots = service.getAvailabilitySlots();
        List<AvailabilitySlotResponse> responseList = slots.stream()
                .map(AvailabilitySlotMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    //Update slot
    @PutMapping("/slots/{id}")
    public ResponseEntity<AvailabilitySlotResponse> updateSlot(@PathVariable Long id, @Valid @RequestBody AvailabilitySlotRequest request)
    {
        AvailabilitySlot updated = service.updateAvailabilitySlot(id, request);
        return ResponseEntity.ok(AvailabilitySlotMapper.toResponse(updated));
    }

    //Delete slot
    @DeleteMapping("/slots/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id)
    {
        service.deleteAvailabilitySlot(id);
        return ResponseEntity.noContent().build();
    }
}
