package com.literatura.literatura.repository;

import com.literatura.literatura.model.Autor;
import com.literatura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a " +
            "WHERE a.fechaDeMuerte > :year " +
            "AND a.fechaDeNacimiento < :year")
    List<Autor> findAuthorsByYear(@Param("year") int year);
    Optional<Autor> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
