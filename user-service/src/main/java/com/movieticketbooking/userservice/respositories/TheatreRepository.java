package com.movieticketbooking.userservice.respositories;



import com.movieticketbooking.userservice.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Integer> {

    public Theatre findByTheatreName(String theatreName);
}
