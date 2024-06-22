package com.OracleAlura.Litealura.model;

import jakarta.persistence.*;


import java.util.*;
import java.util.stream.Collectors;
import java.util.Set;


@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private int fechaDeNacimiento;
    private int fechaDeMuerte;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Libro> libro = new HashSet<>();

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = Integer.parseInt(datosAutor.fechaDeNacimiento());
        this.fechaDeMuerte = Integer.parseInt(datosAutor.fechaDeMuerte());
    }

    @Override
    public String toString() {
        return
                "AUTOR: " + nombre + '\n' +
                 "Fecha de Nacimiento: " + fechaDeNacimiento + '\n' +
                 "Fecha de Muerte: " + fechaDeMuerte + '\n' +
                 "Libros: " + (libro != null ?libro.stream()
                   .map(Libro::getTitulo)
                   .collect(Collectors.joining(", ")) : "N/A") +'\n' +
                   '\n';
    }

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

    public int getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }
    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = Integer.parseInt(fechaDeNacimiento);
    }

    public int getFechaDeMuerte() {
        return fechaDeMuerte;
    }
    public void setFechaDeMuerte(String fechaDeMuerte) {
        this.fechaDeMuerte = Integer.parseInt(fechaDeMuerte);
    }

    public Set<Libro> getLibro() {
        return libro;
    }
    public void setLibro(Set<Libro> libros) {
        this.libro = libros;
        for (Libro unlibro : libros) {
            unlibro.setAutor(this);
        }
    }
}

