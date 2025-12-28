package pt.ulusofona.cd.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequest
{
    @NotBlank(message= "Name is required")
    private String name;

    @NotBlank(message= "City is required")
    private String city;

    @NotBlank(message= "Country is required")
    private String country;

    @NotBlank(message= "Phone is required")
    private String phone;

    @NotBlank(message= "Email is required")
    private String email;
}
