package pt.ulusofona.cd.restaurant.mapper;

import pt.ulusofona.cd.restaurant.dto.MenuItemRequest;
import pt.ulusofona.cd.restaurant.dto.MenuItemResponse;
import pt.ulusofona.cd.restaurant.model.MenuItem;


public class MenuItemMapper
{
    public static MenuItem toEntity(MenuItemRequest dto)
    {
        MenuItem i = new MenuItem();

        i.setRestaurantId(dto.getRestaurantId());
        i.setName(dto.getName());
        i.setDescription(dto.getDescription());
        i.setPrice(dto.getPrice());
        i.setCurrency(dto.getCurrency());

        return i;
    }

    public static MenuItemResponse toResponse(MenuItem entity)
    {
        MenuItemResponse i = new MenuItemResponse();

        i.setRestaurantId(entity.getRestaurantId());
        i.setId(entity.getId());
        i.setName(entity.getName());
        i.setDescription(entity.getDescription());
        i.setPrice(entity.getPrice());
        i.setCurrency(entity.getCurrency());

        return i;
    }
}
