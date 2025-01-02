package com.literatura.literatura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {}

    public Autor(String nombre, String fechaDeNacimiento, String fechaDeMuerte) {
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.fechaDeMuerte = fechaDeMuerte;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(String fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        StringBuilder librosInfo = new StringBuilder();
        for (Libro libro : libros) {
            librosInfo.append("\n - ").append(libro.getTitulo());
        }

        return "***Autor***" +
                "\nNombre: " + nombre +
                "\nFecha de nacimiento: " + fechaDeNacimiento +
                "\nFecha de muerte: " + fechaDeMuerte +
                "\nLibros:" + (libros.isEmpty() ? "Sin libros" : librosInfo.toString()) +
                "\n***********";
    }

}
