package pt.ulusofona.cd.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDto
{
    private Long id;
    private String name;
    private String city;
    private String country;
    private String phone;
    private String email;
}
