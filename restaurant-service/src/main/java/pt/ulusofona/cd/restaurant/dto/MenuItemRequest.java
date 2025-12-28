package pt.ulusofona.cd.restaurant.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuItemRequest
{
    @NotNull(message= "Restaurant ID is required")
    private Long restaurantId;

    @NotBlank(message= "Name is required")
    private String name;

    @NotNull(message= "Description is required")
    private String description;

    @NotNull(message= "Price is required")
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal price;

    @NotNull(message= "Currency is required")
    private String currency;
}
