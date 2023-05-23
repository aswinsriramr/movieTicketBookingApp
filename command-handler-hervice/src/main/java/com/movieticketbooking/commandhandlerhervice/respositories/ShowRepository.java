package com.movieticketbooking.commandhandlerhervice.respositories;


import com.movieticketbooking.commandhandlerhervice.entity.Movie;
import com.movieticketbooking.commandhandlerhervice.entity.Show;
import com.movieticketbooking.commandhandlerhervice.projection.ShowInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show,Integer> {
    @Query("select distinct s from Show s where s.showTime >= ?1 and s.movie = ?2")
    List<ShowInfo> findByShowTimeGreaterThanEqualAndMovieIn(LocalDateTime showTime, Movie movie);

    @Query("select s from Show s where date(s.showTime) = ?1 and s.movie = ?2 order by s.showTime")
    List<Show> findByShowTimeEqualsAndMovieEquals(LocalDate date, Movie movie);







}
