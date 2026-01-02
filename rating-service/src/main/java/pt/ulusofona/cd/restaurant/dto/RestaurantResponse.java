package pt.ulusofona.cd.restaurant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponse
{
    private Long id;
    private String name;
    private String city;
    private String country;
    private String phone;
    private String email;
}
