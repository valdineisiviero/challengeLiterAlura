package com.catalogo.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer anoDownload;
    private Long numeroDownloads;

    // =========== MUDANÇA PRINCIPAL AQUI ===========
    // Mude de FetchType.LAZY para FetchType.EAGER.
    // Isso força o Hibernate a carregar a lista de autores junto com o livro.
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();

    // Construtores
    public Livro() {}

    public Livro(String titulo, String idioma, Integer anoDownload, Long numeroDownloads) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.anoDownload = anoDownload;
        this.numeroDownloads = numeroDownloads;
    }

    // Getters e Setters (sem alterações)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Integer getAnoDownload() { return anoDownload; }
    public void setAnoDownload(Integer anoDownload) { this.anoDownload = anoDownload; }
    public Long getNumeroDownloads() { return numeroDownloads; }
    public void setNumeroDownloads(Long numeroDownloads) { this.numeroDownloads = numeroDownloads; }
    public List<Autor> getAutores() { return autores; }
    public void setAutores(List<Autor> autores) { this.autores = autores; }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", anoDownload=" + anoDownload +
                ", numeroDownloads=" + numeroDownloads +
                '}';
    }
}