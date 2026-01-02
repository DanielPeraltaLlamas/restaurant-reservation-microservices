package pt.ulusofona.cd.restaurant.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.restaurant.client.RestaurantClient;
import pt.ulusofona.cd.restaurant.dto.RatingRequest;
import pt.ulusofona.cd.restaurant.dto.RatingResponse;
import pt.ulusofona.cd.restaurant.dto.RestaurantRatingSummary;
import pt.ulusofona.cd.restaurant.dto.RestaurantResponse;
import pt.ulusofona.cd.restaurant.exception.DuplicateRatingException;
import pt.ulusofona.cd.restaurant.exception.RatingNotFoundException;
import pt.ulusofona.cd.restaurant.mapper.RatingMapper;
import pt.ulusofona.cd.restaurant.model.Rating;
import pt.ulusofona.cd.restaurant.repository.RatingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final RestaurantClient restaurantClient;

    @Transactional
    public RatingResponse createRating(RatingRequest request) {

        /*
        try {
            ResponseEntity<RestaurantResponse> response = restaurantClient.getRestaurant(request.getRestaurantId());
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new IllegalArgumentException("Restaurant not found with ID: " + request.getRestaurantId());
            }
        } catch (Exception e) {

            throw new IllegalArgumentException("Restaurant not found with ID: " + request.getRestaurantId());
        }

        if (ratingRepository.existsByRestaurantIdAndCustomerEmail(
                request.getRestaurantId(), request.getCustomerEmail())) {
            throw new DuplicateRatingException(
                    "Customer with email " + request.getCustomerEmail() +
                            " has already rated restaurant " + request.getRestaurantId()
            );
        }
        */
        Rating rating = ratingMapper.toEntity(request);
        Rating savedRating = ratingRepository.save(rating);


        return ratingMapper.toResponse(savedRating);
    }

    @Transactional
    public List<RatingResponse> getRatingsByRestaurant(Long restaurantId) {

        return ratingRepository.findByRestaurantIdOrderByCreatedAtDesc(restaurantId)
                .stream()
                .map(ratingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RatingResponse getRatingById(Long id) {

        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException("Rating not found with ID: " + id));
        return ratingMapper.toResponse(rating);
    }

    @Transactional
    public RestaurantRatingSummary getRestaurantRatingSummary(Long restaurantId) {


        Double averageScore = ratingRepository.findAverageScoreByRestaurantId(restaurantId)
                .orElse(0.0);

        Long totalRatings = ratingRepository.countByRestaurantId(restaurantId);

        return RestaurantRatingSummary.builder()
                .restaurantId(restaurantId)
                .averageScore(Math.round(averageScore * 10.0) / 10.0) // Redondear a 1 decimal
                .totalRatings(totalRatings)
                .oneStar(Math.toIntExact(ratingRepository.countByRestaurantIdAndScore(restaurantId, 1)))
                .twoStars(Math.toIntExact(ratingRepository.countByRestaurantIdAndScore(restaurantId, 2)))
                .threeStars(Math.toIntExact(ratingRepository.countByRestaurantIdAndScore(restaurantId, 3)))
                .fourStars(Math.toIntExact(ratingRepository.countByRestaurantIdAndScore(restaurantId, 4)))
                .fiveStars(Math.toIntExact(ratingRepository.countByRestaurantIdAndScore(restaurantId, 5)))
                .build();
    }

    @Transactional
    public void deleteRating(Long id) {

        if (!ratingRepository.existsById(id)) {
            throw new RatingNotFoundException("Rating not found with ID: " + id);
        }
        ratingRepository.deleteById(id);

    }
}
