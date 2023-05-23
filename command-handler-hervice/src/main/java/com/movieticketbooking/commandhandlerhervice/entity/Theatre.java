package com.movieticketbooking.commandhandlerhervice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@RequiredArgsConstructor
public class Theatre {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID theatreId;
    @Column(unique = true)
    private String theatreName;

    private String address;
}
