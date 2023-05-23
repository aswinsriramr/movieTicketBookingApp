package com.movieticketbooking.userservice.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Document
@Getter
public class Booking {

    @Id
    private String id;

    private String movieName;
    private String theatreName;
    private LocalDateTime showTime;

    @Setter
    private BookingStatus bookingStatus;
    @Builder.Default
    private List<Integer> seats = new ArrayList<>();
}
