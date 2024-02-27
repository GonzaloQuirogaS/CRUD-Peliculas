package com.proyecto.peliculas.controller;

import com.proyecto.peliculas.entities.Actor;
import com.proyecto.peliculas.entities.Pelicula;
import com.proyecto.peliculas.services.IActorService;
import com.proyecto.peliculas.services.IArchivoService;
import com.proyecto.peliculas.services.IGeneroService;
import com.proyecto.peliculas.services.IPeliculaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        Pelicula pelicula = peliculaService.findById(id);
        String ids = "";

        for (Actor actor : pelicula.getProtagonistas()) {
            if ("".equals(ids)) {
                ids = actor.getId().toString();
            } else {
                ids = ids + "," + actor.getId().toString();
            }
        }

        model.addAttribute("pelicula", pelicula);
        model.addAttribute("ids", ids);
        model.addAttribute("generos", this.generoService.findAll());
        model.addAttribute("actores", this.actorService.findAll());
        model.addAttribute("titulo", "Editar pelicula");
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

        if (ids != null && !"".equals(ids)) {
            List<Long> idsProtagonistas = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
            List<Actor> protagonistas = actorService.findAllById(idsProtagonistas);
            pelicula.setProtagonistas(protagonistas);
        }

        peliculaService.save(pelicula);
        return "redirect:home";
    }

    private String getExtension(String archivo) {
        return archivo.substring(archivo.lastIndexOf("."));
    }

    @GetMapping({"/", "/home", "/index"})
    public String home(Model model, @RequestParam(name = "pagina", required = false, defaultValue = "0") Integer pagina) {

        PageRequest pr = PageRequest.of(pagina, 12);
        Page<Pelicula> page = peliculaService.findAll(pr);

        if (page.getTotalPages() > 0) {
            List<Integer> paginas = IntStream.rangeClosed(1, page.getTotalPages()).boxed().toList();
            model.addAttribute("paginas", paginas);
        }

        model.addAttribute("actual", pagina + 1);

        model.addAttribute("peliculas", page.getContent());
        model.addAttribute("titulo", "Catalogo de peliculas");


        //model.addAttribute("msj", "Catalogo actualizado");
        //model.addAttribute("tipoMsj", "success");

        return "home";
    }

    @GetMapping({"/listado"})
    public String listado(Model model, @RequestParam(required = false) String msj, @RequestParam(required = false) String tipoMsj) {
        model.addAttribute("titulo", "Listado de peliculas");
        model.addAttribute("peliculas", peliculaService.findAll());

        if (!"".equals(tipoMsj) && !"".equals(msj)) {
            model.addAttribute("msj", msj);
            model.addAttribute("tipoMsj", tipoMsj);
        }

        return "listado";
    }

    @GetMapping("/pelicula/{id}/delete")
    public String eliminar(@PathVariable(name = "id") Long id, Model model, RedirectAttributes redirectAttributes) {

        peliculaService.delete(id);

        redirectAttributes.addAttribute("msj", "La pelicula fue elmininada con exito");
        redirectAttributes.addAttribute("tipoMsj", "success");


        return "redirect:/listado";
    }


}
