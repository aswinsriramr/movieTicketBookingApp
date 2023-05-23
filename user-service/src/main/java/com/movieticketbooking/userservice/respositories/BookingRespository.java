package com.movieticketbooking.userservice.respositories;

import com.movieticketbooking.userservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingRespository extends MongoRepository<Booking, UUID> {
}
