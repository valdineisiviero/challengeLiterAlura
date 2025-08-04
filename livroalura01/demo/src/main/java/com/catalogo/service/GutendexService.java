

// GutendexService.java - Versão corrigida
package com.catalogo.service;

import com.catalogo.dto.GutendexResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class GutendexService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "https://gutendex.com/books/";

    public GutendexService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GutendexResponse buscarLivrosPorTitulo(String titulo) {
        try {
            String url = baseUrl + "?search=" + titulo.replace(" ", "%20");
            return restTemplate.getForObject(url, GutendexResponse.class);
        } catch (RestClientException e) {
            System.err.println("Erro ao buscar livros na API: " + e.getMessage());
            return new GutendexResponse();
        }
    }
}