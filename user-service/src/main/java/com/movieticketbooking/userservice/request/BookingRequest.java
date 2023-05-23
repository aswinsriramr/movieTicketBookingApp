package com.movieticketbooking.userservice.request;

import com.movieticketbooking.userservice.entity.Seat;

import java.util.List;
import java.util.UUID;

public record BookingRequest(String userId,UUID showId, List<Integer> seatNumber) {
}
