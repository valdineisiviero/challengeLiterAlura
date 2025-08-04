
// AutorDTO.java
package com.catalogo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorDTO {
    private String name;

    @JsonProperty("birth_year")
    private Integer anoNascimento;

    @JsonProperty("death_year")
    private Integer anoFalecimento;

    // Getters e Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAnoNascimento() { return anoNascimento; }
    public void setAnoNascimento(Integer anoNascimento) { this.anoNascimento = anoNascimento; }

    public Integer getAnoFalecimento() { return anoFalecimento; }
    public void setAnoFalecimento(Integer anoFalecimento) { this.anoFalecimento = anoFalecimento; }
}