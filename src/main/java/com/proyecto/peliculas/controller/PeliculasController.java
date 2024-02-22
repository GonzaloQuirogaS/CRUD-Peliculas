package com.proyecto.peliculas.controller;

import com.proyecto.peliculas.entities.Actor;
import com.proyecto.peliculas.entities.Pelicula;
import com.proyecto.peliculas.services.IActorService;
import com.proyecto.peliculas.services.IArchivoService;
import com.proyecto.peliculas.services.IGeneroService;
import com.proyecto.peliculas.services.IPeliculaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PeliculasController {

    private IPeliculaService peliculaService;

    private IGeneroService generoService;

    private IActorService actorService;

    private IArchivoService archivoService;

    public PeliculasController(IPeliculaService peliculaService, IGeneroService generoService, IActorService actorService, IArchivoService archivoService) {
        this.peliculaService = peliculaService;
        this.generoService = generoService;
        this.actorService = actorService;
        this.archivoService = archivoService;
    }

    @GetMapping("/pelicula")
    public String crear(Model model) {

        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("actores", actorService.findAll());
        model.addAttribute("titulo", "Nueva Pelicula");
        return "pelicula";
    }

    @GetMapping("/pelicula/{id}")
    public String editar(@PathVariable(name = "id") Long id, Model model) {
        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("generos", generoService.findAll());
        model.addAttribute("titulo", "Editar Pelicula");
        return "pelicula";
    }

    @PostMapping("/pelicula")
    public String guardar(@Valid Pelicula pelicula, BindingResult br, @ModelAttribute(name = "ids") String ids, Model model, @RequestParam("archivo") MultipartFile imagen) {

        if (br.hasErrors()) {
            model.addAttribute("generos", generoService.findAll());
            model.addAttribute("actores", actorService.findAll());
            return "pelicula";
        }

        if (!imagen.isEmpty()) {
            String archivo = pelicula.getNombre() + this.getExtension(imagen.getOriginalFilename());
            pelicula.setImagen(archivo);
            try {
                archivoService.guardar(archivo, imagen.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
         pelicula.setImagen("default.jpg");
        }



        List<Long> idsProtagonistas = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
        List<Actor> protagonistas = actorService.findAllById(idsProtagonistas);
        pelicula.setProtagonistas(protagonistas);

        peliculaService.save(pelicula);
        return "redirect:home";
    }

    private String getExtension(String archivo){
        return archivo.substring(archivo.lastIndexOf("."));
    }

    @GetMapping({"/", "/home", "/index"})
    public String home(Model model) {


        model.addAttribute("peliculas", peliculaService.findAll());
        //model.addAttribute("msj", "Catalogo actualizado");
        //model.addAttribute("tipoMsj", "success");

        return "home";
    }


}
