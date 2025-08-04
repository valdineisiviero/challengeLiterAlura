// livroService.java - Versão corrigida
package com.catalogo.service;

import com.catalogo.dto.GutendexResponse;
import com.catalogo.dto.LivroDTO;
import com.catalogo.model.Autor;
import com.catalogo.model.Livro;
import com.catalogo.repository.AutorRepository;
import com.catalogo.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final GutendexService gutendexService;

    public LivroService(LivroRepository livroRepository,
                        AutorRepository autorRepository,
                        GutendexService gutendexService) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.gutendexService = gutendexService;
    }

    @Transactional
    public List<Livro> buscarESalvarLivrosPorTitulo(String titulo) {
        // 1. Busca os dados na API externa (Gutendex)
        GutendexResponse response = gutendexService.buscarLivrosPorTitulo(titulo);

        if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
            return new ArrayList<>(); // Retorna lista vazia se não encontrar nada na API
        }

        // 2. Mapeia os DTOs da API para Entidades do nosso banco
        List<Livro> livrosParaSalvar = response.getResults().stream()
                .map(this::converterDtoParaEntidade)
                .collect(Collectors.toList());

        // 3. Salva cada livro e seus autores no banco de dados
        List<Livro> livrosSalvos = new ArrayList<>();
        for (Livro livro : livrosParaSalvar) {
            livrosSalvos.add(salvarOuAtualizarLivroComAutores(livro));
        }

        return livrosSalvos;
    }

    @Transactional
    private Livro salvarOuAtualizarLivroComAutores(Livro livro) {
        // Verifica se o livro já existe no banco pelo título exato
        Optional<Livro> livroExistenteOpt = livroRepository.findByTituloIgnoreCase(livro.getTitulo());

        if (livroExistenteOpt.isPresent()) {
            return livroExistenteOpt.get(); // Se já existe, apenas retorna sem salvar de novo
        }

        // Se o livro não existe, vamos salvar seus autores primeiro
        List<Autor> autoresPersistidos = new ArrayList<>();
        if (livro.getAutores() != null) {
            for (Autor autor : livro.getAutores()) {
                // Verifica se o autor já existe pelo nome
                Optional<Autor> autorExistenteOpt = autorRepository.findByNomeIgnoreCase(autor.getNome());

                // Se o autor já existe, usa ele. Se não, salva o novo autor.
                Autor autorParaAssociar = autorExistenteOpt.orElseGet(() -> autorRepository.save(autor));
                autoresPersistidos.add(autorParaAssociar);
            }
        }

        // Associa os autores já persistidos ao novo livro
        livro.setAutores(autoresPersistidos);

        // **PASSO CRÍTICO**: Estabelece a relação nos dois lados (bidirecional)
        // Para cada autor, informa a qual livro ele pertence.
        for(Autor autor : autoresPersistidos) {
            autor.getLivros().add(livro);
        }

        // Finalmente, salva o novo livro com as associações corretas
        return livroRepository.save(livro);
    }

    private Livro converterDtoParaEntidade(LivroDTO livroDTO) {
        if (livroDTO == null || livroDTO.getTitle() == null) {
            return null;
        }
        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitle());
        livro.setNumeroDownloads(livroDTO.getNumeroDownloads());

        if (livroDTO.getIdiomas() != null && !livroDTO.getIdiomas().isEmpty()) {
            livro.setIdioma(livroDTO.getIdiomas().get(0));
        } else {
            livro.setIdioma("desconhecido");
        }

        if (livroDTO.getAutores() != null) {
            livro.setAutores(
                    livroDTO.getAutores().stream()
                            .map(this::converterAutorDtoParaEntidade)
                            .collect(Collectors.toList())
            );
        }
        return livro;
    }

    private Autor converterAutorDtoParaEntidade(com.catalogo.dto.AutorDTO autorDTO) {
        Autor autor = new Autor();
        autor.setNome(autorDTO.getName());
        autor.setAnoNascimento(autorDTO.getAnoNascimento());
        autor.setAnoFalecimento(autorDTO.getAnoFalecimento());
        return autor;
    }

    // MÉTODOS DE CONSULTA (não precisam de mudança)
    @Transactional(readOnly = true)
    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Autor> listarTodosAutores() {
        return autorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutoresVivosNoAno(int ano) {
        // Certifique-se que sua query no repositório está correta
        return autorRepository.findAutoresVivosNoAno(ano);
    }

    @Transactional(readOnly = true)
    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdioma(idioma);
    }
}