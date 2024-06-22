package com.OracleAlura.Litealura.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        //en el json vemos como esta mapeado ruslts
        @JsonAlias("results") List<DatosLibro> resultados
) {
    public java.util.Optional<Object> resultado() {
        return java.util.Optional.empty();
    }
}