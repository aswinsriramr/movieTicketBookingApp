package com.movieticketbooking.userservice.controller;

import com.movieticketbooking.userservice.entity.Movie;
import com.movieticketbooking.userservice.entity.Show;
import com.movieticketbooking.userservice.entity.User;
import com.movieticketbooking.userservice.request.BookingRequest;
import com.movieticketbooking.userservice.request.ConfirmationRequest;
import com.movieticketbooking.userservice.request.LoginRequest;
import com.movieticketbooking.userservice.service.TokenService;
import com.movieticketbooking.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        return userService.register(user);
    }


    @GetMapping("/login")
    public String token(@RequestBody LoginRequest user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(),user.password()));
        return tokenService.generateToken(authentication);
    }

    @GetMapping("/movies")
    public List<Movie> getMovies(@RequestParam(defaultValue = "0") Integer pageNo,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "releaseDate") String sortBy){
        return userService.recentMovies(pageNo, pageSize, sortBy);
    }

    @GetMapping("/movie/dates")
    public List<LocalDate> getDates(@RequestParam String movieName){
        return userService.getShowDates(movieName);
    }

    @GetMapping("/shows")
    public List<Show> getShows(@RequestParam String movieName, @RequestParam LocalDate date){
        return userService.getShows(date,movieName);
    }

    @PostMapping("/book/create")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest){
        return userService.createBooking(bookingRequest);
    }

    @PostMapping("/book/confirm")
    public ResponseEntity<?> confirmBooking(@RequestBody ConfirmationRequest confirmationRequest){
        return userService.confirmBooking(confirmationRequest.loginId(),confirmationRequest.bookingID());
    }

}
