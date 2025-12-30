package pt.ulusofona.cd.reservation.dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class AvailabilitySlotResponse
{
    private Long id;
    private Long restaurantId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private int seatsAvailable;
}