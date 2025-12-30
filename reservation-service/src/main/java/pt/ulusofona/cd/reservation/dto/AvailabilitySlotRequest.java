package pt.ulusofona.cd.reservation.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class AvailabilitySlotRequest
{
    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @Min(0)
    @NotNull(message = "Capacity is required")
    private int capacity;

    @Min(0)
    @NotNull(message = "Amount of seats available is required")
    private int seatsAvailable;
}