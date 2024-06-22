package com.OracleAlura.Litealura.principal;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import com.OracleAlura.Litealura.service.ConsumoAPI;
import com.OracleAlura.Litealura.service.ConvierteDatos;
import com.OracleAlura.Litealura.model.DatosLibro;
import com.OracleAlura.Litealura.repository.ILibroRepository;
import com.OracleAlura.Litealura.repository.IAutorRepository;
import com.OracleAlura.Litealura.model.Libro;
import com.OracleAlura.Litealura.model.Autor;
import com.OracleAlura.Litealura.model.Datos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private final List<DatosLibro> datosLibro = new ArrayList<>();
    @Autowired
    private ILibroRepository repository;
    @Autowired
    private IAutorRepository repositoryAutor;

    private List<Libro> libros;
    private List<Autor> autor;

    public Principal() {
    }

    public void muestraElMenu() {
        var menu = """
                ----------------------------------------
                    MENU:
                                    
                1 - Buscar libros por titulo
                2 - Mostrar libros registrados
                3 - Mostrar autores registrados
                4 - Mostrar autores vivos en determinado año
                5 - Mostrar libros por idiomas
                               
                0 - Salir
                -----------------------------------------
                """;
        System.out.println(menu);
    }

    //filtro de opcion para solo permitir numeros
    private void scannerSoloNumeros() {
        while (!teclado.hasNextInt()) {
            System.out.println("ingrese un numero");
            teclado.next();
        }
    }

    public void opcionesMenu() {
        var opcion = -1;
        while (opcion != 0) {

            muestraElMenu();
            scannerSoloNumeros();
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutorVivoEnAño();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    // Forzar salida de la aplicación
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    public void buscarLibro() {
        System.out.println("Ingrese el titulo del libro: ");
        var buscaLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + buscaLibro.replace(" ", "+"));
        var buscador = convierteDatos.obtenerDatos(json, Datos.class);
        var tituloLibro = teclado.nextLine();
        json = ConsumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = convierteDatos.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado ");
            System.out.println(libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void mostrarLibrosRegistrados() {
        libros = repository.findAll();
        libros.forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados() {
        autor = repositoryAutor.findAll();
        autor.forEach(System.out::println);
    }

    private void buscarLibrosPorIdioma() {
        System.out.println("""
                --------------------------------
                Ingrese la opción del idioma deseado
                        
                1- Ingles
                2- Español
                3- Portugues
                4- Italiano
                -------------------------------
                """);
        scannerSoloNumeros();
        var numero = teclado.nextInt();
        switch (numero) {
            case 1:
                buscarIdioma("en");
                break;
            case 2:
                buscarIdioma("es");
                break;
            case 3:
                buscarIdioma("pt");
                break;
            case 4:
                buscarIdioma("it");
                break;
            default:
                System.out.println("Opción inválida");
        }
    }

    private void buscarIdioma(String idioma) {
        try {
            libros = repository.findByIdiomas(idioma);

            if (libros == null) {
                System.out.println("No hay Libros registrados");
            } else {
                libros.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error en la busqueda");
        }
    }

    private void buscarAutorVivoEnAño() {
        System.out.println("Ingrese año:");
        scannerSoloNumeros();
        var ano = teclado.nextInt();
        autor = repositoryAutor.autoresVivosEnDeterminadoAno(ano);
        if (autor == null) {
            System.out.println("No hay registro de autores en ese año");
        } else {
            autor.forEach(System.out::println);
        }
    }
}


