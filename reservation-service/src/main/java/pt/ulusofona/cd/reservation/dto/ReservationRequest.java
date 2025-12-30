package pt.ulusofona.cd.reservation.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequest
{

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    @NotBlank(message = "Customer name is required")
    @Size(max = 100, message = "Customer name must be less than 100 characters")
    private String customerName;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String customerEmail;

    @Digits(integer = 15, fraction = 0, message = "Phone must contain only numbers")
    @Size(min = 8, max = 15, message = "Phone must be between 8 and 15 digits")
    @Size(max = 20, message = "Phone must be less than 20 characters")
    private String customerPhone;

    @NotNull(message = "Party size is required")
    @Min(value = 1, message = "Party size must be greater than zero")
    @Max(value = 50, message = "Party size cannot exceed 50")
    private Integer partySize;

    @NotNull(message = "Scheduled date and time is required")
    @Future(message = "Scheduled time must be in the future")
    private Instant scheduledAt;


}