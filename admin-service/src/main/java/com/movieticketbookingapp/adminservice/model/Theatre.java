package com.movieticketbookingapp.adminservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@Getter
@RequiredArgsConstructor
public class Theatre {
    private String theatreName;

    private String address;
}
