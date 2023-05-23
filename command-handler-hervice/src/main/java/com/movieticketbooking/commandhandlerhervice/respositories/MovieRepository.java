package com.movieticketbooking.commandhandlerhervice.respositories;


import com.movieticketbooking.commandhandlerhervice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {

    public Movie findByMovieName(String movieName);

}
