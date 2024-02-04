package com.projects.bookmyshow.DTO;

import com.projects.bookmyshow.Models.ShowSeat;
import com.projects.bookmyshow.Models.User;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookTicketRequestDTO {
    private List<Long> showSeatIds;
    private long userId;
    private long showId;
}
