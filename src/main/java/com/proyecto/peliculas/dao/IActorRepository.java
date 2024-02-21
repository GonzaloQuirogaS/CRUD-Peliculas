package com.proyecto.peliculas.dao;

import com.proyecto.peliculas.entities.Actor;
import org.springframework.data.repository.CrudRepository;

public interface IActorRepository extends CrudRepository<Actor, Long> {
}
