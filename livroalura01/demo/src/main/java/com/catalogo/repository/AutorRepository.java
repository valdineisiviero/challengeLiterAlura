




package com.catalogo.repository;

import com.catalogo.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional; // Importar Optional

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // ALTERAÇÃO: Mudou de "List<Autor> findByNomeContainingIgnoreCase"
    // para "Optional<Autor> findByNomeIgnoreCase".
    // Isso busca o nome exato do autor.
    Optional<Autor> findByNomeIgnoreCase(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> findAutoresVivosNoAno(@Param("ano") int ano);
}