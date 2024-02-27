package com.proyecto.peliculas.dao;

import com.proyecto.peliculas.entities.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPeliculasRepository extends JpaRepository<Pelicula, Long> {

}
