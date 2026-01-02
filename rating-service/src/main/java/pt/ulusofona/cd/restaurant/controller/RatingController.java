package pt.ulusofona.cd.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.restaurant.dto.RatingRequest;
import pt.ulusofona.cd.restaurant.dto.RatingResponse;
import pt.ulusofona.cd.restaurant.dto.RestaurantRatingSummary;
import pt.ulusofona.cd.restaurant.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
@Slf4j
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingResponse> createRating(@Valid @RequestBody RatingRequest request) {
        RatingResponse response = ratingService.createRating(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<RatingResponse>> getRatingsByRestaurant(
            @PathVariable Long restaurantId) {
        List<RatingResponse> ratings = ratingService.getRatingsByRestaurant(restaurantId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        RatingResponse rating = ratingService.getRatingById(id);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/restaurant/{restaurantId}/summary")
    public ResponseEntity<RestaurantRatingSummary> getRestaurantRatingSummary(
            @PathVariable Long restaurantId) {
        RestaurantRatingSummary summary = ratingService.getRestaurantRatingSummary(restaurantId);
        return ResponseEntity.ok(summary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }


}
