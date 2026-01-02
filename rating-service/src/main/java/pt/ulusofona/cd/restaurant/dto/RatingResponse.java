package pt.ulusofona.cd.restaurant.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingResponse {
    private Long id;
    private Long restaurantId;
    private String customerName;
    private Integer score;
    private String comment;
    private LocalDateTime createdAt;
}