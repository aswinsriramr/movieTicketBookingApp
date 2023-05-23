package com.movieticketbooking.userservice.respositories;



import com.movieticketbooking.userservice.entity.Seat;
import com.movieticketbooking.userservice.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Integer> {
    Seat findByShowEqualsAndSeatNumberEquals(Show show, int seatNumber);
}
