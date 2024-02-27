package com.proyecto.peliculas.services;

import com.proyecto.peliculas.dao.IPeliculasRepository;
import com.proyecto.peliculas.entities.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService implements IPeliculaService {

    @Autowired
    private IPeliculasRepository peliculasRepository;

    @Override
    public void save(Pelicula pelicula) {
        peliculasRepository.save(pelicula);
    }

    @Override
    public Pelicula findById(Long id) {
        return peliculasRepository.findById(id).orElse(null);
    }

    @Override
    public List<Pelicula> findAll() {
        return (List<Pelicula>) peliculasRepository.findAll();
    }

    @Override
    public Page<Pelicula> findAll(Pageable paginable) {
        return peliculasRepository.findAll(paginable);
    }


    @Override
    public void delete(Long id) {
        peliculasRepository.deleteById(id);
    }
}
