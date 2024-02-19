package com.proyecto.peliculas.dao;

import com.proyecto.peliculas.entities.Genero;

public interface IGeneroRepository {
    public void save(Genero genero);
    public  Genero findById(Long id);
}
