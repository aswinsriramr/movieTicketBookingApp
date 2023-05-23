package com.movieticketbooking.userservice.service;

import com.movieticketbooking.userservice.entity.*;
import com.movieticketbooking.userservice.request.BookingRequest;
import com.movieticketbooking.userservice.respositories.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final BookingRespository bookingRespository;

    public ResponseEntity<String> register(User user){
        try {
            userRepository.save(user);
            return ResponseEntity.ok("User Registered");
        }catch (Exception e){
           return ResponseEntity.badRequest().body("Unable to register user");
       }
    }



    public List<Movie> recentMovies(Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Movie> page = movieRepository.findAll(paging);
        if(page.hasContent())
            return page.getContent();
        else
            return new ArrayList<>();
    }

    public List<LocalDate> getShowDates(String movieName){
        return showRepository.findByShowTimeGreaterThanEqualAndMovieIn(LocalDateTime.now(),movieRepository.findByMovieName(movieName))
                .stream().map( a -> a.getShowTime().toLocalDate()).collect(Collectors.toList());
    }

    public List<Show> getShows(LocalDate date, String movieName){
        return showRepository.findByShowTimeEqualsAndMovieEquals(date,movieRepository.findByMovieName(movieName));
    }

    public ResponseEntity<?> createBooking(BookingRequest bookingRequest){
       try {
            Show show = showRepository.findByShowIdEquals(bookingRequest.showId());
            User user = userRepository.findByLoginId(bookingRequest.userId());
            Booking booking = Booking.builder().id(UUID.randomUUID().toString()).movieName(show.getMovie().getMovieName())
                    .showTime(show.getShowTime())
                    .bookingStatus(BookingStatus.PENDING)
                    .theatreName(show.getTheatre().getTheatreName()).build();
            for (Integer i : bookingRequest.seatNumber()) {
                Seat seat = seatRepository.findByShowEqualsAndSeatNumberEquals(show, i);
                if(seat.getSeatStatus()!=SeatStatus.AVAILABLE)
                    throw new Exception("Seats Already Booked or Temporarily unavailable");
                seat.setSeatStatus(SeatStatus.LOCKED);
                seatRepository.save(seat);
            }
            booking.getSeats().addAll(bookingRequest.seatNumber());
            user.getBookingHistory().add(booking);
            userRepository.save(user);
            return ResponseEntity.ok(booking);
        }catch(Exception e){
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Booking cannot be initiated , please check details : "+ e.getMessage());
        }
    }

    public ResponseEntity<?> confirmBooking(String loginId,String bookingID){
        try {
            User user = userRepository.findByLoginId(loginId);
            Booking booking = user.getBookingHistory()
                    .stream().filter(b -> b.getId().equals(bookingID)).findAny().get();
            System.out.println(booking.toString());
            Movie movie = movieRepository.findByMovieName(booking.getMovieName());
            Show show = showRepository.findByShowTimeAndMovie(booking.getShowTime(), movie);
            for (Integer i : booking.getSeats()) {
                Seat seat = seatRepository.findByShowEqualsAndSeatNumberEquals(show, i);
                seat.setSeatStatus(SeatStatus.BOOKED);
                seatRepository.save(seat);
            }
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            bookingRespository.save(booking);
            return ResponseEntity.ok(booking);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Booking Confirmation Failed");
       }
    }

    public ResponseEntity<?> cancelBooking(String loginId,String bookingID){
        try {
            User user = userRepository.findByLoginId(loginId);
            Booking booking = user.getBookingHistory()
                    .stream().filter(b -> b.getId().equals(bookingID)).findAny().get();
            System.out.println(booking.toString());
            Movie movie = movieRepository.findByMovieName(booking.getMovieName());
            Show show = showRepository.findByShowTimeAndMovie(booking.getShowTime(), movie);
            for (Integer i : booking.getSeats()) {
                Seat seat = seatRepository.findByShowEqualsAndSeatNumberEquals(show, i);
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seatRepository.save(seat);
            }
            booking.setBookingStatus(BookingStatus.CANCELLED);
            bookingRespository.save(booking);
            return ResponseEntity.ok(booking);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Booking Cancellation Failed");
        }
    }


}
