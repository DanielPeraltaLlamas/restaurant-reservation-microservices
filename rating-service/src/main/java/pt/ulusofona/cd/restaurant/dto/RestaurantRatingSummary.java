package pt.ulusofona.cd.restaurant.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRatingSummary {
    private Long restaurantId;
    private Double averageScore;
    private Long totalRatings;
    private Integer oneStar;
    private Integer twoStars;
    private Integer threeStars;
    private Integer fourStars;
    private Integer fiveStars;
}
