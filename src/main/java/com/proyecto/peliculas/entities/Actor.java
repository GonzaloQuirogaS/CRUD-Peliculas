package com.proyecto.peliculas.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "actores")
public class Actor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @Column(name = "url_imagen")
    private String urlImagen;

    public Actor() {
    }

    public Actor(Long id, String nombre, String urlImagen) {
        this.id = id;
        this.nombre = nombre;
        this.urlImagen = urlImagen;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
