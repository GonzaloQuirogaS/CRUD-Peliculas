package com.proyecto.peliculas.dao;

import com.proyecto.peliculas.entities.Genero;
import org.springframework.data.repository.CrudRepository;

public interface IGeneroRepository extends CrudRepository<Genero,Long> {

}
