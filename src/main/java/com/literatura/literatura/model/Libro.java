package com.literatura.literatura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idiomas;
    private Double numeroDescargas;

    public Libro(){}

    public Libro(String titulo, Autor autor, String idiomas, Double numeroDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idiomas = idiomas;
        this.numeroDescargas = numeroDescargas;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "----- Libros -----" +
                "\nTitulo: " + titulo +
                "\nAutor: " + (autor != null ? autor.getNombre() : "Sin autor") +
                "\nIdioma: " + idiomas +
                "\nCantidad de descargas: " + numeroDescargas +
                "\n------------------";
    }



}

