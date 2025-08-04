
// Application.java
package com.catalogo;

import com.catalogo.model.Autor;
import com.catalogo.model.Livro;
import com.catalogo.service.LivroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors; // <-- MUDANÇA: Import necessário

@SpringBootApplication
public class Principal {

    public static void main(String[] args) {
        SpringApplication.run(Principal.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner commandLineRunner(LivroService livroService) {
        return args -> {
            // O Scanner não será fechado para evitar fechar o System.in
            Scanner scanner = new Scanner(System.in);
            boolean continuar = true;

            System.out.println("=== CATÁLOGO DE LIVROS GUTENDEX ===");

            while (continuar) {
                exibirMenu();
                int opcao = lerOpcao(scanner);

                switch (opcao) {
                    case 1 -> buscarLivrosPorTitulo(scanner, livroService);
                    case 2 -> listarLivrosRegistrados(livroService);
                    case 3 -> listarAutoresRegistrados(livroService);
                    case 4 -> listarAutoresVivosEmAno(scanner, livroService);
                    case 5 -> listarLivrosPorIdioma(scanner, livroService);
                    case 6 -> continuar = false;
                    default -> System.out.println("Opção inválida!");
                }

                if (continuar) {
                    System.out.println("\nPressione Enter para continuar...");
                    scanner.nextLine();
                }
            }

            System.out.println("Obrigado por usar o Catálogo de Livros!");
            // scanner.close(); // <-- MUDANÇA: Removido para não fechar System.in
        };
    }

    // <-- MUDANÇA: Métodos agora são estáticos para melhor prática
    private static void exibirMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1 - Buscar livros por título");
        System.out.println("2 - Listar livros registrados");
        System.out.println("3 - Listar autores registrados");
        System.out.println("4 - Listar autores vivos em um determinado ano");
        System.out.println("5 - Listar livros em um determinado idioma");
        System.out.println("6 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Retorna um valor que será tratado como inválido
        }
    }

    private static void buscarLivrosPorTitulo(Scanner scanner, LivroService livroService) {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();

        if (titulo.trim().isEmpty()) {
            System.out.println("Título não pode ser vazio!");
            return;
        }

        // <-- MUDANÇA: Tratamento de exceção para falhas na API
        try {
            System.out.println("Buscando livros...");
            List<Livro> livros = livroService.buscarESalvarLivrosPorTitulo(titulo);

            if (livros.isEmpty()) {
                System.out.println("Nenhum livro encontrado para o título: " + titulo);
            } else {
                System.out.println("\nLivros encontrados:");
                livros.forEach(livro -> {
                    System.out.println("- Título: " + livro.getTitulo());
                    String nomesAutores = livro.getAutores().stream()
                            .map(Autor::getNome)
                            .collect(Collectors.joining(", ")); // <-- MUDANÇA: Uso do Collectors.joining
                    System.out.println("  Autores: " + (nomesAutores.isEmpty() ? "Nenhum" : nomesAutores));
                    System.out.println("  Idioma: " + livro.getIdioma());
                    System.out.println("  Downloads: " + livro.getNumeroDownloads());
                    System.out.println();
                });
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao buscar os livros: " + e.getMessage());
        }
    }

    private static void listarLivrosRegistrados(LivroService livroService) {
        List<Livro> livros = livroService.listarTodosLivros();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado no catálogo.");
        } else {
            System.out.println("\n=== LIVROS REGISTRADOS ===");
            livros.forEach(livro -> {
                System.out.println("Título: " + livro.getTitulo());
                String nomesAutores = livro.getAutores().stream()
                        .map(Autor::getNome)
                        .collect(Collectors.joining(", ")); // <-- MUDANÇA
                System.out.println("Autores: " + (nomesAutores.isEmpty() ? "Nenhum" : nomesAutores));
                System.out.println("Idioma: " + livro.getIdioma());
                System.out.println("Downloads: " + livro.getNumeroDownloads());
                System.out.println("---");
            });
        }
    }

    private static void listarAutoresRegistrados(LivroService livroService) {
        List<Autor> autores = livroService.listarTodosAutores();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado no catálogo.");
        } else {
            System.out.println("\n=== AUTORES REGISTRADOS ===");
            autores.forEach(autor -> {
                System.out.println("Nome: " + autor.getNome());
                System.out.println("Nascimento: " + (autor.getAnoNascimento() != null ? autor.getAnoNascimento() : "Desconhecido"));
                System.out.println("Falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Vivo"));
                System.out.println("---");
            });
        }
    }

    private static void listarAutoresVivosEmAno(Scanner scanner, LivroService livroService) {
        System.out.print("Digite o ano: ");
        try {
            int ano = Integer.parseInt(scanner.nextLine());
            List<Autor> autores = livroService.listarAutoresVivosNoAno(ano);

            if (autores.isEmpty()) {
                System.out.println("Nenhum autor vivo encontrado no ano " + ano);
            } else {
                System.out.println("\n=== AUTORES VIVOS EM " + ano + " ===");
                autores.forEach(autor -> {
                    System.out.println("Nome: " + autor.getNome());
                    System.out.println("Período: " +
                            (autor.getAnoNascimento() != null ? autor.getAnoNascimento() : "?") + " - " +
                            (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Presente"));
                    System.out.println("---");
                });
            }
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido! Por favor, digite um número.");
        }
    }

    private static void listarLivrosPorIdioma(Scanner scanner, LivroService livroService) {
        System.out.print("Digite o código do idioma (ex: pt, en, es, fr): ");
        String idioma = scanner.nextLine().trim().toLowerCase();

        if (idioma.isEmpty()) {
            System.out.println("Idioma não pode ser vazio!");
            return;
        }

        List<Livro> livros = livroService.listarLivrosPorIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma: " + idioma);
        } else {
            System.out.println("\n=== LIVROS NO IDIOMA " + idioma.toUpperCase() + " ===");
            livros.forEach(livro -> {
                System.out.println("Título: " + livro.getTitulo());
                String nomesAutores = livro.getAutores().stream()
                        .map(Autor::getNome)
                        .collect(Collectors.joining(", ")); // <-- MUDANÇA
                System.out.println("Autores: " + (nomesAutores.isEmpty() ? "Nenhum" : nomesAutores));
                System.out.println("Downloads: " + livro.getNumeroDownloads());
                System.out.println("---");
            });
        }
    }
}