package com.catalogo.repository;

import com.catalogo.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional; // Importar Optional

public interface LivroRepository extends JpaRepository<Livro, Long> {

    // ALTERAÇÃO: Mudou de "List<Livro> findByTituloContainingIgnoreCase"
    // para "Optional<Livro> findByTituloIgnoreCase".
    // Isso busca o título exato e lida melhor com o caso de não encontrar.
    Optional<Livro> findByTituloIgnoreCase(String titulo);

    List<Livro> findByIdioma(String idioma);

    // Este método não é usado na lógica atual, mas pode ser mantido para uso futuro.
    // @Query("SELECT DISTINCT l FROM Livro l JOIN l.autores a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    // List<Livro> findLivrosPorAutoresVivosNoAno(@Param("ano") int ano);
}