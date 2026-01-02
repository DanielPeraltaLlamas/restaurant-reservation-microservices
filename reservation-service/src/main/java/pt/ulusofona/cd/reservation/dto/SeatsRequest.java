package pt.ulusofona.cd.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeatsRequest
{
    private Long slotId;
    private int partySize;
}

