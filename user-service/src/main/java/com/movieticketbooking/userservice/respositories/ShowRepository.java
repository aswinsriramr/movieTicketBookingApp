package com.movieticketbooking.userservice.respositories;



import com.movieticketbooking.userservice.entity.Movie;
import com.movieticketbooking.userservice.entity.Show;
import com.movieticketbooking.userservice.projection.ShowInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ShowRepository extends JpaRepository<Show,Integer> {
    @Query("select distinct s from Show s where s.showTime >= ?1 and s.movie = ?2")
    List<ShowInfo> findByShowTimeGreaterThanEqualAndMovieIn(LocalDateTime showTime, Movie movie);

    Show findByShowIdEquals(UUID showId);

    Show findByShowTimeAndMovie(LocalDateTime showTime, Movie movie);

    @Query("select s from Show s where date(s.showTime) = ?1 and s.movie = ?2 order by s.showTime")
    List<Show> findByShowTimeEqualsAndMovieEquals(LocalDate date, Movie movie);







}
