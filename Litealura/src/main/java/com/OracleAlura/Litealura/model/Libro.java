package com.OracleAlura.Litealura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;
    private Double totalDescargas;
    private String nombreAutor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    
    public Libro(DatosLibro datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo();
        this.nombreAutor = datosLibro.autor().stream().map(DatosAutor::nombre).collect(Collectors.toList()).toString();
        this.idiomas = datosLibro.idiomas();
        this.totalDescargas = datosLibro.totalDescargas();
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "------------LIBRO-----------\n"+
                "Titulo: "+ titulo + '\n' +
                "Autor: " + nombreAutor + '\n' +
                "Idiomas: " + idiomas + '\n' +
                "Descargas: " + totalDescargas +'\n' +
                "----------------------------" +'\n';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
        if (autor != null && !autor.getLibro().contains(this)) {
            autor.getLibro().add(this);
        }
    }

    public Double getDescargas() {
        return totalDescargas;
    }

    public void setDescargas(Double descargas) {
        this.totalDescargas = descargas;
    }
}