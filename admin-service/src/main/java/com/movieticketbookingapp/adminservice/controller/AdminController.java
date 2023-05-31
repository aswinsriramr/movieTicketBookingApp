package com.movieticketbookingapp.adminservice.controller;

import com.movieticketbookingapp.adminservice.model.Movie;
import com.movieticketbookingapp.adminservice.model.Show;
import com.movieticketbookingapp.adminservice.model.Theatre;
import com.movieticketbookingapp.adminservice.request.LoginRequest;
import com.movieticketbookingapp.adminservice.service.CommandService;
import com.movieticketbookingapp.adminservice.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class AdminController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    private final CommandService commandService;

    @GetMapping("/login")
    public String token(@RequestBody LoginRequest admin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(admin.username(),admin.password()));
        return tokenService.generateToken(authentication);
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @PostMapping("/add/movie")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie){
        log.info("Sending command from controller to service");
        return commandService.addMovie(movie);
    }

    @PostMapping("/add/theatre")
    public ResponseEntity<String> addMovie(@RequestBody Theatre theatre){
        log.info("Sending command from controller");
        return commandService.addTheatre(theatre);
    }

    @PostMapping("/add/show")
    public ResponseEntity<String> addMovie(@RequestBody Show show){
        log.info("Sending command from controller");
        return commandService.addShow(show);
    }
}
