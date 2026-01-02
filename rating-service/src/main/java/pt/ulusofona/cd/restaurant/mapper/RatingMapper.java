package pt.ulusofona.cd.restaurant.mapper;


import org.springframework.stereotype.Component;
import pt.ulusofona.cd.restaurant.dto.RatingRequest;
import pt.ulusofona.cd.restaurant.dto.RatingResponse;
import pt.ulusofona.cd.restaurant.model.Rating;

@Component
public class RatingMapper {

    public Rating toEntity(RatingRequest request) {
        return Rating.builder()
                .restaurantId(request.getRestaurantId())
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .score(request.getScore())
                .comment(request.getComment())
                .build();
    }

    public RatingResponse toResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .restaurantId(rating.getRestaurantId())
                .customerName(rating.getCustomerName())
                .score(rating.getScore())
                .comment(rating.getComment())
                .createdAt(rating.getCreatedAt())
                .build();
    }
}