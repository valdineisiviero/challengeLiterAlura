Catálogo de Livros Interativo - Alura Challenge
Este projeto é um catálogo de livros interativo que opera via terminal (console). Desenvolvido como parte do Challenge de Back-End da Alura, a aplicação consome a API pública Gutendex para buscar e exibir informações sobre livros e autores, além de persistir os dados consultados em um banco de dados PostgreSQL.

📖 Visão Geral
O objetivo principal é oferecer uma ferramenta simples e eficiente para que os usuários possam explorar o vasto acervo da API Gutendex. A aplicação permite buscar livros por título, listar todos os livros e autores já registrados no banco de dados local, encontrar autores que estavam vivos em um determinado ano e filtrar livros por idioma.

✨ Funcionalidades Principais
O menu interativo oferece as seguintes opções:

Buscar livros por título:

Pesquisa na API Gutendex por um título específico.

Exibe os detalhes do(s) livro(s) encontrado(s): título, autor(es), idioma e número de downloads.

Salva o livro e o autor no banco de dados para consultas futuras.

Listar livros registrados:

Mostra todos os livros que já foram buscados e estão armazenados no banco de dados local.

Listar autores registrados:

Exibe todos os autores que foram salvos no banco de dados, com seus respectivos anos de nascimento e falecimento.

Listar autores vivos em um determinado ano:

Solicita um ano e retorna uma lista de autores registrados que estavam vivos naquele período.

Listar livros em um determinado idioma:

Filtra e exibe os livros registrados no banco de dados com base em um código de idioma (ex: pt para português, en para inglês).

Sair:

Encerra a aplicação.

🚀 Tecnologias Utilizadas
Java 17: Linguagem principal do projeto.

Spring Boot: Framework para criação de aplicações Java robustas e independentes.

Spring Data JPA: Para facilitar a persistência e o acesso a dados.

PostgreSQL: Banco de dados relacional utilizado para armazenar as informações.

RestTemplate: Para realizar as chamadas HTTP à API Gutendex.

Jackson: Para desserializar as respostas JSON da API.

Maven: Gerenciador de dependências do projeto.

⚙️ Como Executar o Projeto
Pré-requisitos
Java 17 ou superior instalado.

Maven instalado.

PostgreSQL instalado e um banco de dados criado para a aplicação.

Passos
Clone o repositório:

Bash

git clone 
cd SEU-REPOSITORIO https://github.com/valdineisiviero/challengeLiterAlura
Configure o Banco de Dados:

Abra o arquivo src/main/resources/application.properties.

Altere as seguintes propriedades com as suas credenciais do PostgreSQL:

Properties

spring.datasource.url=jdbc:postgresql://localhost:5432/SEU_BANCO_DE_DADOS
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
Compile e Execute a Aplicação:

Utilizando o Maven, execute o comando abaixo na raiz do projeto:

Bash

mvn spring-boot:run
Interaja com a Aplicação:

O menu interativo será exibido no seu terminal. Basta digitar o número da opção desejada e pressionar Enter para começar a usar.

🏛️ Estrutura do Código
O código fonte está organizado da seguinte forma:

Principal.java: A classe principal (@SpringBootApplication) que inicializa a aplicação e contém toda a lógica da interface de linha de comando (menu, entrada do usuário e exibição de dados).

/model: Contém as entidades JPA (Livro e Autor) que mapeiam as tabelas do banco de dados.

/service: Inclui a classe LivroService, responsável pela lógica de negócio, como buscar dados na API, processá-los e interagir com o repositório.

/repository: Contém as interfaces Spring Data JPA (LivroRepository, AutorRepository) para as operações de banco de dados.

/dto: (Se aplicável) Data Transfer Objects para desserializar a resposta da API.

