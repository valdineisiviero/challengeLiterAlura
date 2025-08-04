// LivroDTO.java
package com.catalogo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroDTO {
    private Long id;
    private String title;

    @JsonProperty("authors")
    private List<AutorDTO> autores;

    @JsonProperty("languages")
    private List<String> idiomas;

    @JsonProperty("download_count")
    private Long numeroDownloads;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<AutorDTO> getAutores() { return autores; }
    public void setAutores(List<AutorDTO> autores) { this.autores = autores; }

    public List<String> getIdiomas() { return idiomas; }
    public void setIdiomas(List<String> idiomas) { this.idiomas = idiomas; }

    public Long getNumeroDownloads() { return numeroDownloads; }
    public void setNumeroDownloads(Long numeroDownloads) { this.numeroDownloads = numeroDownloads; }
}
