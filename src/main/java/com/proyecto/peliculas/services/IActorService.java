package com.proyecto.peliculas.services;

import com.proyecto.peliculas.entities.Actor;

import java.util.List;

public interface IActorService {
    public List<Actor> findAll();

    public List<Actor> findAllById(List<Long> id);

}
