package com.projects.bookmyshow.Services;

import com.projects.bookmyshow.Repositories.ShowSeatRepository;
import com.projects.bookmyshow.Repositories.ShowSeatTypeRepository;
import com.projects.bookmyshow.Models.Show;
import com.projects.bookmyshow.Models.ShowSeat;
import com.projects.bookmyshow.Models.ShowSeatType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Getter
@Setter
@Service
public class PriceCalculatorService {
    private ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    public PriceCalculatorService(ShowSeatRepository showSeatRepository){
        this.showSeatTypeRepository =  showSeatTypeRepository;
    }

    int amount = 0;
    public int calculatePrice(List<ShowSeat> showSeats, Show show){
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
        for(ShowSeat showSeat : showSeats){
            for(ShowSeatType showSeatType : showSeatTypes){
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    this.amount += showSeatType.getPrice();
                }
            }
        }
        return amount;
    }
}
