package com.literatura.literatura.service;

import com.literatura.literatura.model.Autor;
import com.literatura.literatura.model.DatosLibros;
import com.literatura.literatura.model.Libro;

import java.util.List;
import java.util.stream.Collectors;

public class ConversionUtil {
    public static Libro convertirDesdeDatosLibros(DatosLibros datosLibros) {
        // Convertimos el primer DatosAutor a Autor (si hay autores)
        Autor autor = datosLibros.autor().isEmpty() ? null :
                new Autor(
                        datosLibros.autor().get(0).nombre(),
                        datosLibros.autor().get(0).fechaDeNacimiento(),
                        datosLibros.autor().get(0).fechaDemuerte()
                );

        String idioma = datosLibros.idiomas().isEmpty() ? "" : datosLibros.idiomas().get(0);

        return new Libro(
                datosLibros.titulo(),
                autor, // Ahora pasamos un único autor
                idioma,
                datosLibros.numeroDescargas()
        );
    }
}
