package com.projects.bookmyshow.Controllers;

import com.projects.bookmyshow.DTO.BookTicketRequestDTO;
import com.projects.bookmyshow.DTO.BookTicketResponseDTO;
import com.projects.bookmyshow.DTO.ResponseStatus;
import com.projects.bookmyshow.Models.Booking;
import com.projects.bookmyshow.Services.BookTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    BookTicketService bookTicketService;
    @Autowired
    public  BookingController(BookTicketService bookTicketService){
        this.bookTicketService = bookTicketService;
    }
    public BookTicketResponseDTO bookTicket(BookTicketRequestDTO bookTicketRequestDTO){
        BookTicketResponseDTO bookTicketResponseDTO = new BookTicketResponseDTO();
        try{
            Booking booking = bookTicketService.bookTicket(
                    bookTicketRequestDTO.getUserId(),
                    bookTicketRequestDTO.getShowSeatIds(),
                    bookTicketRequestDTO.getShowId());
            bookTicketResponseDTO.setAmount(booking.getAmount());
            bookTicketResponseDTO.setBookingId(booking.getId());
            bookTicketResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);

        }
        catch(Exception e){
            bookTicketResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return bookTicketResponseDTO;
    }
}
