package pt.ulusofona.cd.reservation.exception;

public class RestaurantNotFoundException extends RuntimeException
{
    public RestaurantNotFoundException(String message)
    {
        super(message);
    }

    public RestaurantNotFoundException(Long restaurantId)
    {
        super("Restaurant "+restaurantId+" not found");
    }
}
