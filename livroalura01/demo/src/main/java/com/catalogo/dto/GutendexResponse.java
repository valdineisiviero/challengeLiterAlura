
// GutendexResponse.java
package com.catalogo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexResponse {
    private List<LivroDTO> results;

    @JsonProperty("next")
    private String proximaPagina;

    @JsonProperty("previous")
    private String paginaAnterior;

    private Integer count;

    // Getters e Setters
    public List<LivroDTO> getResults() { return results; }
    public void setResults(List<LivroDTO> results) { this.results = results; }

    public String getProximaPagina() { return proximaPagina; }
    public void setProximaPagina(String proximaPagina) { this.proximaPagina = proximaPagina; }

    public String getPaginaAnterior() { return paginaAnterior; }
    public void setPaginaAnterior(String paginaAnterior) { this.paginaAnterior = paginaAnterior; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
}