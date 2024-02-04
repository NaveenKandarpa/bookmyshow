package com.projects.bookmyshow.Services;

import com.projects.bookmyshow.Models.*;
import com.projects.bookmyshow.Repositories.BookingRepository;
import com.projects.bookmyshow.Repositories.ShowRepository;
import com.projects.bookmyshow.Repositories.ShowSeatRepository;
import com.projects.bookmyshow.Repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Service
public class BookTicketService {
    private UserRepository userRepository;
    private ShowSeatRepository showSeatRepository;
    private ShowRepository showRepository;
    private BookingRepository bookingRepository;
    private PriceCalculatorService priceCalculatorService;
    @Autowired
    public BookTicketService(
            UserRepository userRepository,
            ShowSeatRepository showSeatRepository,
            ShowRepository showRepository,
            BookingRepository bookingRepository){
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookTicket(Long userId, List<Long> showSeatIds, Long showId){

        // 1. Get user by user ID
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Sorry, User not found!!");
        }
        User user = userOptional.get();

        // 2. Get show by show ID
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new RuntimeException("Sorry, Show not present!!");
        }
        Show show = showOptional.get();

        // 3. Get ShowSeats by ID
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        // 4. Check for the  availability of seats of all the seats
        for(ShowSeat showSeat : showSeats){
            // 5. If no, throw exception
            if(showSeat.getShowSeatStatus().equals(ShowSeatStatus.BOOKED) ||
                    showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED)  &&
                            Duration.between(showSeat.getLockedAt().toInstant(), new Date().toInstant()).toMinutes() < 15){
                throw new RuntimeException("Seats not available!!");
            }
        }


        for(ShowSeat showSeat : showSeats){
            // 6. If yes, mark all the seats as BLOCKED
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setLockedAt(new Date());
            // 7. Save it in the database
            showSeatRepository.save(showSeat);
        }

        // 8. Create the booking object
        Booking booking = new Booking();
        booking.setShow(show);
        booking.setShowseats(showSeats);
        booking.setUser(user);
        booking.setBookedAt(new Date());
        booking.setPayments(new ArrayList<>());
        booking.setBookingStatus(BookingStatus.IN_PROGRESS);
        booking.setAmount(priceCalculatorService.calculatePrice(showSeats, show));
        // 9. Save the booking object
        booking = bookingRepository.save(booking);
        // 10. return the object
        return booking;
    }
}