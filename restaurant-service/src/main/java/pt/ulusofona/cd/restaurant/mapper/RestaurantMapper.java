package pt.ulusofona.cd.restaurant.mapper;

import pt.ulusofona.cd.restaurant.dto.RestaurantMenuResponse;
import pt.ulusofona.cd.restaurant.dto.RestaurantRequest;
import pt.ulusofona.cd.restaurant.dto.RestaurantResponse;
import pt.ulusofona.cd.restaurant.model.MenuItem;
import pt.ulusofona.cd.restaurant.model.Restaurant;

import java.util.List;

public class RestaurantMapper
{
    public static Restaurant toEntity(RestaurantRequest dto)
    {
        Restaurant r = new Restaurant();
        r.setName(dto.getName().trim());
        r.setCity(dto.getCity());
        r.setCountry(dto.getCountry());
        r.setPhone(dto.getPhone());
        r.setEmail(dto.getEmail());
        return r;
    }

    public static RestaurantResponse toResponse(Restaurant entity) {
        RestaurantResponse r = new RestaurantResponse();
        r.setId(entity.getId());
        r.setName(entity.getName());
        r.setCity(entity.getCity());
        r.setCountry(entity.getCountry());
        r.setPhone(entity.getPhone());
        r.setEmail(entity.getEmail());
        return r;
    }

    //Creates menu dto with restaurant data and item list
    public static RestaurantMenuResponse toMenuResponse(Restaurant restaurant, List<MenuItem> menu)
    {
        RestaurantMenuResponse r = new RestaurantMenuResponse();

        r.setId(restaurant.getId());
        r.setName(restaurant.getName());
        r.setCity(restaurant.getCity());
        r.setCountry(restaurant.getCountry());
        r.setPhone(restaurant.getPhone());
        r.setEmail(restaurant.getEmail());
        r.setMenu(menu);

        return r;
    }
}
