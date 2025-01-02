package com.literatura.literatura.Principal;

import com.literatura.literatura.model.*;
import com.literatura.literatura.repository.AutorRepository;
import com.literatura.literatura.repository.LibroRepository;
import com.literatura.literatura.service.ConsumoAPI;
import com.literatura.literatura.service.ConversionUtil;
import com.literatura.literatura.service.ConvierteDatos;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado =new Scanner(System.in);
    private String tituloLibro;
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    // Listar libros registrado
    private List<Libro> datosLibro = new ArrayList<>();
    private List<Autor> datosAutor = new ArrayList<>();
    private LibroRepository repositorioLibro;
    private AutorRepository autorRepository;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorioLibro = repository;
        this.autorRepository = autorRepository;
    }




    public void muestraElMenu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    --------------------------------------------------
                    Elija la opcion a traves de su numero:
                    
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listrar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                  
                    0 - Salir
                    ---------------------------------------------------
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibrosWeb();
                    break;
                case 2:
                    mostrarListaLibros();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutoresVivosAnhoDeterminado();
                    break;
                case 5:
                    listarLibrosPorIdiomas();
                    break;
                case 0:
                    System.out.println("Adios :D");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private Datos buscarLibro(){
        System.out.println("Ingrese el nombre del libro, por favor");
        tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));
        //System.out.println(json);
        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        return datosBusqueda;
    }

    private void buscarLibrosWeb() {
        Optional<DatosLibros> libroBuscado = buscarLibro().resultados().stream()
                .filter(libro -> libro.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            Libro libro = ConversionUtil.convertirDesdeDatosLibros(libroBuscado.get());
            System.out.println("Libro encontrado: ");
            System.out.println(libro);

            if (!repositorioLibro.existsByTitulo(libro.getTitulo())) {
                Autor autorDelLibro = libro.getAutor();

                Optional<Autor> autorExistenteOptional = autorRepository.findByNombre(autorDelLibro.getNombre());
                if (autorExistenteOptional.isEmpty()) {
                    autorRepository.save(autorDelLibro); // Guardar si no existe
                } else {
                    // Si el autor existe, puedes establecerlo en el libro antes de guardarlo
                    libro.setAutor(autorExistenteOptional.get());
                }

                repositorioLibro.save(libro); // Guardar el libro después de manejar el autor
                System.out.println("Libro y autor guardados en la base de datos.");
            } else {
                System.out.println("El libro ya existe en la base de datos. No será guardado.");
            }
        } else {
            System.out.println("Libro no encontrado.");
        }
    }





    private void mostrarListaLibros(){
//        if (datosLibro.isEmpty()){
//            System.out.println("No hay libros registrados");
//            return;
//        }
//        datosLibro.forEach(System.out::println);
        List<Libro> libros = repositorioLibro.findAll();
        System.out.println("Libros encontrados en la base de datos: " + libros.size());
        libros.forEach(System.out::println);

    }

    private void mostrarAutoresRegistrados() {
        List<Autor> autor = autorRepository.findAll();
        autor.forEach(System.out::println);
//        if (datosAutor.isEmpty()) {
//            System.out.println("No hay autores registrados.");
//            return;
//        }
//
//        datosAutor.forEach(autor -> {
//            System.out.println("Autor: " + autor.getNombre());
//            System.out.println("Año de nacimiento: " + autor.getFechaDeNacimiento());
//            System.out.println("Año de fallecimiento: " + autor.getFechaDeMuerte());
//            System.out.println("---------------------------------\n");
//        });
    }

    private void buscarAutoresVivosAnhoDeterminado(){
        System.out.println("Escriba el año por favor");
        int anho = teclado.nextInt();
        List<Autor> autoresVivos = autorRepository.findAuthorsByYear(anho);
        System.out.println("***Autores encontrados: ***");
        autoresVivos.forEach(a ->
                System.out.println(a.getNombre() + " fecha de nacimiento: " + a.getFechaDeNacimiento()));
    }

    private void listarLibrosPorIdiomas(){
        var menu2 = """
                Elija el idioma para buscar los libros:
                es - español
                en - ingles
                fr - frances
                pt - portugues
                """;
        System.out.println(menu2);
        String eleccionUsuario = teclado.nextLine();
        List<Libro> librosIdioma =  repositorioLibro.libroPorIdioma(eleccionUsuario);
        if (librosIdioma.isEmpty()){
            System.out.println("Parece que no hay libros en ese idioma");
        }else{
            librosIdioma.forEach(l -> {
                System.out.println("----- Libro -----");
                System.out.println("Título: " + l.getTitulo());
                System.out.println("Idioma: " + l.getIdiomas());
                System.out.println("Autor: " + (l.getAutor() != null ? l.getAutor().getNombre() : "Sin autor"));
                System.out.println("-----------------");
            });;
        }


    }







}