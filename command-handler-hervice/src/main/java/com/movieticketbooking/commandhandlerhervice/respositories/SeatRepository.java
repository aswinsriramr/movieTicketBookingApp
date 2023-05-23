package com.movieticketbooking.commandhandlerhervice.respositories;


import com.movieticketbooking.commandhandlerhervice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Integer> {
}
