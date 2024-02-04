package com.projects.bookmyshow.DTO;

import com.projects.bookmyshow.Models.BookingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTicketResponseDTO {
    private ResponseStatus responseStatus;
    private int amount;
    private long bookingId;
}
