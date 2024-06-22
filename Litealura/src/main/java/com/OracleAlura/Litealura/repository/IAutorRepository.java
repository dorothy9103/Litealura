package com.OracleAlura.Litealura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.OracleAlura.Litealura.model.Autor;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface IAutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :ano AND (a.fechaDeMuerte IS NULL OR a.fechaDeMuerte >= :ano)")
    List<Autor> autoresVivosEnDeterminadoAno(int ano);
}