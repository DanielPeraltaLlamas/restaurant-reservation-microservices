package pt.ulusofona.cd.restaurant.dto;


import lombok.Getter;
import lombok.Setter;
import pt.ulusofona.cd.restaurant.model.MenuItem;

import java.util.List;

//Dto that contains restaurant info and its items

@Getter
@Setter
public class RestaurantMenuResponse
{
    private Long id;
    private String name;
    private String city;
    private String country;
    private String phone;
    private String email;

    private List<MenuItem> menu;
}
