package pt.ulusofona.cd.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.restaurant.dto.*;
import pt.ulusofona.cd.restaurant.exception.AvailabilitySlotNotFoundException;
import pt.ulusofona.cd.restaurant.exception.RestaurantNotFoundException;
import pt.ulusofona.cd.restaurant.mapper.AvailabilitySlotMapper;
import pt.ulusofona.cd.restaurant.mapper.MenuItemMapper;
import pt.ulusofona.cd.restaurant.mapper.RestaurantMapper;
import pt.ulusofona.cd.restaurant.model.AvailabilitySlot;
import pt.ulusofona.cd.restaurant.model.MenuItem;
import pt.ulusofona.cd.restaurant.model.Restaurant;
import pt.ulusofona.cd.restaurant.repository.AvailabilitySlotRepository;
import pt.ulusofona.cd.restaurant.repository.MenuItemRepository;
import pt.ulusofona.cd.restaurant.repository.RestaurantRepository;
import pt.ulusofona.cd.restaurant.exception.ItemNotFoundException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService
{

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final AvailabilitySlotRepository availabilitySlotRepository;

    @Transactional
    public MenuItem createMenuItem(MenuItemRequest request)
    {
        MenuItem item = MenuItemMapper.toEntity(request);
        return menuItemRepository.save(item);
    }

    @Transactional
    public MenuItem updateMenuItem(Long id, MenuItemRequest details)
    {
        MenuItem item = getMenuItemById(id);

        item.setCurrency(details.getCurrency());
        item.setName(details.getName());
        item.setDescription(details.getDescription());
        item.setPrice(details.getPrice());


        item.setUpdatedAt(Instant.now());
        return item;

    }

    @Transactional
    public void deleteMenuItem(Long id)
    {
        MenuItem item = getMenuItemById(id);
        menuItemRepository.delete(item);
    }

    @Transactional
    public List<MenuItem> getMenuItems()
    {
        return menuItemRepository.findAll();
    }

    @Transactional
    public MenuItem getMenuItemById(Long id)
    {
        return menuItemRepository.findById(id).orElseThrow(() ->new ItemNotFoundException("Menu item with id " + id + " not found."));
    }

    @Transactional
    public List<MenuItem> getRestaurantMenu(Long restaurantId)
    {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    @Transactional
    public List<RestaurantMenuResponse> getRestaurantMenus()
    {
        List<Restaurant> restaurants = this.getRestaurants();
        List<RestaurantMenuResponse> menus = new ArrayList<RestaurantMenuResponse>();
        for(Restaurant restaurant:restaurants)
        {
            menus.add(RestaurantMapper.toMenuResponse(restaurant, this.getRestaurantMenu(restaurant.getId())));
        }

        return menus;
    }

    @Transactional
    public List<AvailabilitySlot> getAvailableSlots(Long restaurantId, LocalDate date, LocalTime time)
    {
        List<AvailabilitySlot> possibleSlots = availabilitySlotRepository.findByRestaurantIdAndDate(restaurantId, date);
        List<AvailabilitySlot> slots = new ArrayList<AvailabilitySlot>();
        for(AvailabilitySlot slot:possibleSlots)
        {
            //If time is between start and end, it's available
            if(time.isAfter(slot.getStartTime()) && time.isBefore(slot.getEndTime()))
            {
                slots.add(slot);
            }
        }

        return slots;
    }

    //Get availability with number of seats
    @Transactional
    public List<AvailabilitySlot> getAvailability(Long restaurantId, LocalDateTime dateTime, int partySize)
    {
        List<AvailabilitySlot> possibleSlots = this.getAvailableSlots(restaurantId, dateTime.toLocalDate(), dateTime.toLocalTime());
        List<AvailabilitySlot> slots = new ArrayList<AvailabilitySlot>();
        for(AvailabilitySlot slot:possibleSlots)
        {
            //If slot has enough seats, add it to the list
            if(slot.getSeatsAvailable() >= partySize)
            {
                slots.add(slot);
            }
        }

        return slots;
    }

    @Transactional
    public void releaseSeats(Long id, SeatsRequest request)
    {
        AvailabilitySlot slot = this.getAvailabilitySlotById(request.getSlotId());
        if(!id.equals(slot.getRestaurantId()))
        {
            throw new IllegalArgumentException("Restaurant ID provided does not match availability slot.");
        }

        slot.setSeatsAvailable(slot.getSeatsAvailable() + request.getPartySize());
    }

    @Transactional
    public void confirmSeats(Long id, SeatsRequest request)
    {
        AvailabilitySlot slot = this.getAvailabilitySlotById(request.getSlotId());
        if(!id.equals(slot.getRestaurantId()))
        {
            throw new IllegalArgumentException("Restaurant ID provided does not match availability slot.");
        }

        else if(slot.getSeatsAvailable() < request.getPartySize())
        {
            throw new IllegalArgumentException("Not enough seats available");
        }

        slot.setSeatsAvailable(slot.getSeatsAvailable() - request.getPartySize());
    }



    @Transactional
    public Restaurant createRestaurant(RestaurantRequest request)
    {
        Restaurant restaurant = RestaurantMapper.toEntity(request);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public List<Restaurant> getRestaurants()
    {
        return restaurantRepository.findAll();
    }


    @Transactional
    public Restaurant updateRestaurant(Long id, RestaurantRequest details)
    {
        Restaurant r = getRestaurantById(id);

        r.setCountry(details.getCountry());
        r.setCity(details.getCity());
        r.setPhone(details.getPhone());
        r.setEmail(details.getEmail());
        r.setName(details.getName());

        r.setUpdatedAt(Instant.now());
        return r;

    }

    @Transactional
    public void deleteRestaurant(Long id)
    {
        Restaurant restaurant = getRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }


    @Transactional
    public Restaurant getRestaurantById(Long id)
    {
        return restaurantRepository.findById(id).orElseThrow(() ->new RestaurantNotFoundException("Restaurant with id " + id + " not found."));
    }





    @Transactional
    public AvailabilitySlot createAvailabilitySlot(AvailabilitySlotRequest request)
    {
        AvailabilitySlot slot = AvailabilitySlotMapper.toEntity(request);
        return availabilitySlotRepository.save(slot);
    }

    @Transactional
    public List<AvailabilitySlot> getAvailabilitySlots()
    {
        return availabilitySlotRepository.findAll();
    }


    @Transactional
    public AvailabilitySlot updateAvailabilitySlot(Long id, AvailabilitySlotRequest details)
    {
        AvailabilitySlot s = getAvailabilitySlotById(id);

        s.setStartTime(details.getStartTime());
        s.setEndTime(details.getEndTime());
        s.setSeatsAvailable(details.getSeatsAvailable());
        s.setCapacity(details.getCapacity());
        s.setDate(details.getDate());

        s.setUpdatedAt(Instant.now());
        return s;

    }

    @Transactional
    public void deleteAvailabilitySlot(Long id)
    {
        AvailabilitySlot slot = getAvailabilitySlotById(id);
        availabilitySlotRepository.delete(slot);
    }


    @Transactional
    public AvailabilitySlot getAvailabilitySlotById(Long id)
    {
        return availabilitySlotRepository.findById(id).orElseThrow(() ->new AvailabilitySlotNotFoundException("Slot with id " + id + " not found."));
    }


}
