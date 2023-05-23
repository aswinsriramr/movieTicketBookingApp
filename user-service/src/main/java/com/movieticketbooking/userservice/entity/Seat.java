package com.movieticketbooking.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID seatId;


    private int seatNumber;

    private SeatStatus seatStatus;

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public int getSeatNumber(){
        return seatNumber;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;



}
