package com.projects.bookmyshow.Repositories;

import com.projects.bookmyshow.Models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    @Override
    List<ShowSeat> findAllById(Iterable<Long> longs);

    @Override
    ShowSeat save(ShowSeat showSeat);

//    List<ShowSeatType> findAll(Show show);
}
