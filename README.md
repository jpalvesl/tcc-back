<p align="center">
  <img alt="Icone do APP" src="https://upload.wikimedia.org/wikipedia/en/5/5a/Black_question_mark.png" width="100"/>
</p>
<h1 align="center">
  Code2Know - Backend
</h1>

<!-- Badges -->
<p align="center">

  <!-- if your app is a website -->
  <!-- <a href="https://plantinhas-backend.herokuapp.com/" alt="Url do site">
    <img src="https://img.shields.io/website-up-down-1EAE72-red/http/shields.io.svg" />
  </a> -->

  <!-- License -->
  <!-- <a href="./LICENSE" alt="License: MIT">
    <img src="https://img.shields.io/badge/License-MIT-1EAE72.svg" />
  </a> -->

  <img alt="GitHub language count" src="https://img.shields.io/github/languages/count/jpalvesl/tcc-back?color=blue">

  <!-- GitHub repo size -->
  <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/jpalvesl/tcc-back">

  <!-- Social -->  
  <a href="https://github.com/jpalvesl/tcc-back/stargazers">
    <img alt="GitHub stars" src="https://img.shields.io/github/stars/jpalvesl/tcc-back?style=social">
  </a>

</p>

<!-- summary -->
<p align="center">
  <a href="#clipboard-descrição">Descrição</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#rocket-iniciando-o-projeto">Iniciando o projeto</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-o-que-tem-dentro">O que tem dentro?</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#requisitos">Requisitos</a>
</p>


## :clipboard: Descrição
Sistema criado com foco primariamente em ensino e melhorar a interação do aluno iniciante de programação, de forma que ele consiga progredir de forma mais autônoma.

## :rocket: Iniciando o projeto

1. Baixando o repositório

  - Usando Git
```shell
  git clone https://github.com/jpalvesl/tcc-back.git
```
  - Usando Github CLI
```shell
  gh repo clone jpalvesl/tcc-back
```
  <!-- - Rodando com o gitpod  
[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/https://github.com/jpalvesl/tcc-back) -->

2. Criando container Docker responsável pelo banco de dados e criando a **Database** que será usada.

```shell
docker run --detach --name mariadb --env MARIADB_ROOT_PASSWORD=12345 -p 3306:3306  mariadb:latest
```

```shell
docker exec -it mariadb bash
```
> O bash dentro do container será a aberto e a partir dele será possível criar a **Database**

```shell
mariadb -u root -p
```

> Colocar a senha 12345


```shell
CREATE DATABASE CODEKNOW;
```


3. Executando
  - Usando maven
```shell
  mvn spring-boot:run
```
  - Usando wrapper do maven presente no projeto
```shell
  ./mvn spring-boot:run
```
> Lembrando que para usar o wrapper o terminal tem que estar aberto dentro da pasta do backend

4. Obtendo documentação das rotas no insomnia   
[![Run in Insomnia}](https://insomnia.rest/images/run.svg)](https://insomnia.rest/run/?label=code2know&uri=https%3A%2F%2Fgist.githubusercontent.com%2Fjpalvesl%2Fd376e49d396abffe4135b36e2793e34c%2Fraw%2Fa1c1aaaca16fe77021e93cc00351f6c259eed1ff%2Fcode2know.json)

## 🧐 O que tem dentro?

### :building_construction: Tecnologias
- [Java](https://www.java.com/pt-BR/)
- [Spring Boot](https://spring.io/projects/spring-boot)


### :package: Pacotes
- [Spring MVC]()
- [Spring Validation](https://www.baeldung.com/spring-boot-bean-validation)
- [Lombok](https://projectlombok.org/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [MariaDB](https://mariadb.com/kb/en/about-mariadb-connector-j/)
- [Gson](https://github.com/google/gson)

4. Lembrando que para a funcionalidade de submissão funcionar corretamente o [subsistema de Juiz-Online](https://github.com/jpalvesl/tcc-back) deve estar rodando na porta 5000


## Requisitos
| Tecnologia | Versão | 
| --- | --- |
| Java | 17 |
| Maven | 3.6.3 |
| Docker | 20.10.12 |
