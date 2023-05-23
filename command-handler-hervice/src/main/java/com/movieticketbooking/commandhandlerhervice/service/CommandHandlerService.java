package com.movieticketbooking.commandhandlerhervice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.movieticketbooking.commandhandlerhervice.entity.*;
import com.movieticketbooking.commandhandlerhervice.request.ShowRequest;
import com.movieticketbooking.commandhandlerhervice.respositories.MovieRepository;
import com.movieticketbooking.commandhandlerhervice.respositories.SeatRepository;
import com.movieticketbooking.commandhandlerhervice.respositories.ShowRepository;
import com.movieticketbooking.commandhandlerhervice.respositories.TheatreRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandHandlerService {


    ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();


    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    @KafkaListener(topics = "create-movie", groupId = "command-handler")
    public void addMovie(String message){
        try {
            Movie movie = objectMapper.readValue(message,Movie.class);
            movieRepository.save(movie);
        }catch (Exception e){
            System.out.println("Movie not inserted "+e.getMessage());
        }
    }

    @KafkaListener(topics = "create-theatre", groupId = "command-handler")
    public void saveTheatre(String message){
        try {
            Theatre theatre = objectMapper.readValue(message,Theatre.class);
            theatreRepository.save(theatre);
        }catch (Exception e){
            System.out.println("Theatre not inserted "+e.getMessage());
        }
    }

    @KafkaListener(topics = "create-show", groupId = "command-handler")
    public void saveShow(String message){
        try {
            ShowRequest showRequest = objectMapper.readValue(message, ShowRequest.class);
            Movie movie = movieRepository.findByMovieName(showRequest.getMovieName());
            Theatre theatre = theatreRepository.findByTheatreName(showRequest.getTheatreName());
            Show show = Show.builder().movie(movie).theatre(theatre).showTime(showRequest.getShowTime()).build();
            List<Seat> seatList = show.getSeats();
            for (int i = 1; i <= 100; i++) {
                seatList.add(Seat.builder().seatNumber(i).seatStatus(SeatStatus.AVAILABLE).show(show).build());
            }
            showRepository.save(show);
        }catch (Exception e){
            System.out.println("Show not inserted "+e.getMessage());
        }
    }
}
