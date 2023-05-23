package com.movieticketbookingapp.adminservice.service;

import com.movieticketbookingapp.adminservice.model.Movie;
import com.movieticketbookingapp.adminservice.model.Show;
import com.movieticketbookingapp.adminservice.model.Theatre;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CommandService {

    private final KafkaTemplate<String, Object> kafkaTemplate;


    public ResponseEntity<String> addMovie(Movie movie){
        try {
            log.info("Sending create movie command via kafka in command service");
            kafkaTemplate.send("create-movie", movie);
            return ResponseEntity.ok("Create Movie command sent successfully");
        }catch (Exception e){
            log.error("Sending create movie command failed");
            return ResponseEntity.badRequest().body("Command failed, check movie details");
        }
    }


    public ResponseEntity<String> addTheatre(Theatre theatre){
        try {
            log.info("Sending create theatre  command via kafka in command service");
            kafkaTemplate.send("create-theatre", theatre);
            return ResponseEntity.ok("Create Theatre command sent successfully");
        }catch (Exception e){
            log.error("Sending create theatre command failed");
            return ResponseEntity.badRequest().body("Command failed, check  details");
        }
    }


    public ResponseEntity<String> addShow(Show show){
        try {
            log.info("Sending create show command via kafka in command service");
            kafkaTemplate.send("create-show",show);
            return ResponseEntity.ok("Create Movie command sent successfully");
        }catch (Exception e){
            log.error("Sending create  command failed");
            return ResponseEntity.badRequest().body("Command failed, check  details");
        }
    }
}
