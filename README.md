<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/JDK-21+-informational?style=for-the-badge" alt="JDK 21+">
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot">
<img src="https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
</p>
<h1 align="center">🐶 Sistema de Cadastro de Animais 🐱</h1>

## ℹ️ Sobre o Projeto
Aplicação desenvolvida com Spring Boot para gerenciar os cadastro de animais de um abrigo. Para criar um cadastro é preciso preencher o formulário (nome, tipo, sexo, endereço onde foi encontrado, idade, peso e raça).

O sistema utiliza o Spring Data JPA para implementar as funcionalidades CRUD no banco de dados MySQL, armazenando os dados dos animais. A interface foi projetada para funcionar via  CLI (Interface de Linha de Comando) através de um menu interativo.

O Docker cria um container da aplicação, permitindo a sua execução mesmo em máquinas que não possuam Java e/ou MySQL instalados.

### 🎬 Demonstração
![WIP](https://img.shields.io/badge/status-em%20constru%C3%A7%C3%A3o-lightgrey?style=flat-square)

(Essa seção será atualizada com uma demonstração do menu assim que a versão 2.0 estiver pronta)

### ✨ Funcionalidades do Sistema

- Cadastrar novo animal
- Listar todos os animais ou filtrar por critérios
- Atualizar um cadastro
- Excluir um cadastro

### 📄 Formulário de cadastro
| Campo                |          Descriçao           | Exemplo                 | 
|:---------------------|:----------------------------:|:------------------------|
| **Nome**             |        Nome do animal        | Caramelo                |
| **Tipo**             |       Gato ou Cachorro       | Cachorro                |
| **Sexo**             |        Sexo do animal        | M (Macho)               |
| **Endereço**         | Onde o animal foi encontrado | Rua abc, 123, São Paulo |
| **Idade**            |   Valor da idade estimada    | 4                       |
| **Unidade da Idade** |    Idade em meses ou anos    | anos                    |
| **Peso**             |          Peso em kg          | 9.8                     |
| **Raça**             |      Raça predominante       | SRD                     |

### 🗃️ Arquitetura do banco de dados
![WIP](https://img.shields.io/badge/status-em%20constru%C3%A7%C3%A3o-lightgrey?style=flat-square)

(Essa seção será atualizada com um exemplo da estrutura do banco de dados assim que a versão 2.0 estiver pronta)

### 📂 Estrutura do Projeto
![WIP](https://img.shields.io/badge/status-em%20constru%C3%A7%C3%A3o-lightgrey?style=flat-square)

(Essa seção será atualizada com a estrutura do projeto assim que a versão 2.0 estiver pronta)

### 🛠️ Tecnologias e ferramentas
**Linguagem:** Java 21

**Framework:** Spring Boot 3

**Persistência:** Spring Data JPA / Hibernate

**Banco de dados:** MySQL 9

**Infraestrutura:** Docker & Docker Compose

**Padrão de Camadas:** Controller, Service, Repository e Entity

## 🚀 Executando a Aplicação
### 💻️ Pré-requisitos
- **Docker** para containerizar a aplicação.
- **Git** para clonar o repositório.

---
1. **Clone o repositório**
```
git clone https://github.com/alineaos/sistema-cadastro-animais.git
```

2. **Navegue até a pasta do repositório**
```
cd sistema-cadastro-animais
```

3. **Construa a imagem da aplicação**
```
docker-compose build
```

4. **Execute a aplicação**
```
docker-compose run --rm app
```
```--rm``` permite o uso do CLI da aplicação

---

### Projeto proposto por Lucas Carrilho - [@devmagro](https://www.linkedin.com/in/karilho/)

[Link original](https://github.com/karilho/desafioCadastro)
