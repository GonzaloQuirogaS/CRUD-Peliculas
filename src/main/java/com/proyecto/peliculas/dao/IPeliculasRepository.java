package com.proyecto.peliculas.dao;

import com.proyecto.peliculas.entities.Pelicula;
import org.springframework.data.repository.CrudRepository;

public interface IPeliculasRepository extends CrudRepository<Pelicula, Long> {
}
