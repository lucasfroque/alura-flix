<h1 style="text-align: center;"> Aluraflix</h1>
<img src="img/130516084-f199b740-4daf-4d67-a995-9bfdb2bd4560.png" alt="Aluraflix" style="display: block; margin-left: auto; margin-right: auto; width: 50%;" />
<h2 style="text-align: center;">Sobre o projeto</h2>
<p style="text-align: center;">O Aluraflix é um projeto desenvolvido durante o Alura Challenge Back-End, que consiste em criar uma API Restful para um sistema de vídeos.</p>

<h2 style="text-align: center;">Tecnologias utilizadas</h2>
<ul>
<li>Java</li>
<li>Spring Boot</li>
<li>Spring Data JPA</li>
<li>Spring Security</li>
<li>JWT</li>
<li>MySQL</li>
<li>H2 Database</li>
<li>JUnit</li>
<li>Mockito</li>
</ul>

<h2 style="text-align: center;">Como executar o projeto</h2>
<p>Pré-requisitos: Java 17</p>
    
    ```bash
    # clonar repositório
    git clone

    # entrar na pasta do projeto back end
    cd backend

    # executar o projeto
      mvn spring-boot:run
    ```

<h2 style="text-align: center;">Regras de serviço</h2>
<h3>Autenticação:</h3>
<p>Para acessar o serviço, é necessário estar autenticado. Para isso, é necessário fazer uma requisição POST para o endpoint /login como no exemplo abaixo:</p>

    {
      "email": "admin@mail.com",
      "password": "admin"
    }

<p>Após isso, será retornado um token JWT que deverá ser utilizado no header da requisição para acessar os endpoints do serviço.</p>

<h3>Categoria:</h3>
<p>A categoria com ID = 1, se chama LIVRE e caso ela não seja especificada na criação do vídeo, será atribuido o ID = 1 automaticamente.</p>


<h3>Free videos:</h3>
<p>Existem videos que podem ser assistidos sem estar autenticado.</p>

    ```bash
    GET /videos/free
    ```

<h3>Paginação e ordenação:</h3>
<p>Os endpoints de listagem de vídeos e categorias aceitam os parâmetros de paginação e ordenação.</p>

    ```bash
    GET /videos?page=0&size=10&sort=id,desc
    GET /categories?page=0&size=10&sort=id,desc
    ```

<h3>Endpoints:</h3>
<p>Os endpoints da API são:</p>

    ```bash
    POST /login
    GET /videos
    GET /videos/{id}
    POST /videos
    PUT /videos/{id}
    DELETE /videos/{id}
    GET /categories
    GET /categories/{id}
    POST /categories
    PUT /categories/{id}
    DELETE /categories/{id}
    POST /users
    GET /users/{id}
    PUT /users/{id}
    DELETE /users/{id}
    ```










