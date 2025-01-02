package com.literatura.literatura.repository;

import com.literatura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByTitulo(String titulo);
    @Query("SELECT l FROM Libro l WHERE LOWER(l.idiomas) = LOWER(:idioma)")
    List<Libro> libroPorIdioma(@Param("idioma") String idioma);;

}
