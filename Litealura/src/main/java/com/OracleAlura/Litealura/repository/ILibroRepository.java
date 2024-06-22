package com.OracleAlura.Litealura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.OracleAlura.Litealura.model.Libro;
import java.util.List;


@Repository
public interface ILibroRepository extends JpaRepository<Libro,Long> {

    Libro findByTituloContainsIgnoreCase(String nombreLibro);
    List<Libro> findByIdiomas(String idiomas);
}