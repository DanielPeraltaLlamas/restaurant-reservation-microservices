package pt.ulusofona.cd.restaurant.mapper;

import pt.ulusofona.cd.restaurant.dto.AvailabilitySlotRequest;
import pt.ulusofona.cd.restaurant.dto.AvailabilitySlotResponse;
import pt.ulusofona.cd.restaurant.model.AvailabilitySlot;

public class AvailabilitySlotMapper
{
    public static AvailabilitySlot toEntity(AvailabilitySlotRequest dto)
    {
        AvailabilitySlot s = new AvailabilitySlot();

        s.setRestaurantId(dto.getRestaurantId());
        s.setDate(dto.getDate());
        s.setCapacity(dto.getCapacity());
        s.setSeatsAvailable(dto.getSeatsAvailable());
        s.setStartTime(dto.getStartTime());
        s.setEndTime(dto.getEndTime());

        return s;
    }

    public static AvailabilitySlotResponse toResponse(AvailabilitySlot entity)
    {
        AvailabilitySlotResponse s = new AvailabilitySlotResponse();

        s.setId(entity.getId());
        s.setRestaurantId(entity.getRestaurantId());
        s.setDate(entity.getDate());
        s.setCapacity(entity.getCapacity());
        s.setSeatsAvailable(entity.getSeatsAvailable());
        s.setStartTime(entity.getStartTime());
        s.setEndTime(entity.getEndTime());

        return s;
    }
}
