package com.proyecto.peliculas.controller;

import com.proyecto.peliculas.dao.IGeneroRepository;
import com.proyecto.peliculas.entities.Genero;
import org.springframework.web.bind.annotation.*;

@RestController
public class GeneroController {

    private IGeneroRepository iGeneroRepository;

    public GeneroController(IGeneroRepository iGeneroRepository) {
        this.iGeneroRepository = iGeneroRepository;
    }

    @PostMapping("genero")
    public Long guardar(@RequestParam String nombre){

        Genero genero = new Genero();
        genero.setNombre(nombre);

        iGeneroRepository.save(genero);

        return genero.getId();
    }

    @GetMapping("genero/{id}")
    public String buscarPorId(@PathVariable(name = "id") Long id){
        return iGeneroRepository.findById(id).getNombre();
    }

}
