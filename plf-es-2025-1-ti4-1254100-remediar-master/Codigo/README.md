# Código do Projeto

## back-remediar

O backend do projeto está disponível em [/Codigo/back-remediar](/Codigo/back-remediar), onde é possível também observar a arquitetura empregada pela Remediar.

A API Remediar foi desenvolvida com a abordagem de microsserviços em Java com Spring Boot e Mensageria com RabbitMQ, além de docker-compose.yml.

Para rodar o backend localmente, siga os passos abaixo.

#### 🛠️ Configuração do Ambiente
1. Assegure-se que tenha o [Java JDK 21](https://www.oracle.com/java/technologies/downloads/#java21) instalado e configurado em sua máquina.

2. Instale o [Docker](https://www.docker.com/products/docker-desktop/).

3. Clone o repositório do GitHub.
_Abra o terminal git bash em um diretório de preferência e cole o seguinte comando._
``` bash
git clone https://github.com/ICEI-PUC-Minas-PPLES-TI/plf-es-2025-1-ti4-1254100-remediar.git
```

4. Suba os containers via docker.
_Com o aplicativo do Docker aberto em sua máquina, abra o terminal no diretório raiz do projeto e siga os passos:_
``` bash
1. cd Codigo/back-remediar/
2. docker-compose up -d
```

5. Com os containers ativos, a aplicação Spring Boot estará pronta para ser executada. Você pode iniciar a aplicação de duas formas:

#### 🗄️ Acesso ao Banco de Dados (PgAdmin)
A aplicação utiliza um banco de dados PostgreSQL com interface web fornecida pelo PgAdmin, acessível após subir os containers.

URL de acesso: http://localhost:15433

Credenciais padrão:

Email: admin@remediar.com

Senha: admin

Após login, você poderá visualizar e manipular os bancos de dados criados pela aplicação, incluindo tabelas, dados, índices e execuções de queries.

##### Crie um novo Database dentro do PGAdmin!
Botão direito em Servers > Register > Server

Preencha o campo de nome com o de sua preferência.

![Imagem Registro de Servidor DB](/Documentacao/Imagens/image-1.png)

Em **Connection**, preencha os campos:
* Host name/address: db_remediar
* Port 5432
* Maintenance database postgres: postgres
* Username: 'Seu username configurado'
* Password: 'Sua senha configurada'

![alt text](/Documentacao/Imagens/image-4.png)

#### ✅ Executar pelo terminal:

Ainda no diretório `Codigo/back-remediar/`, use o seguinte comando abaixo para compilar e rodar a aplicação:

```bash
.\remediar-restart.ps1 true update
```
> [!WARNING] 
> **_Obs:_** O (primeiro) parâmetro booleano é para popular > o banco de dados com a base de medicamentos; e o (segundo) parâmetro é para definir o comportamento do banco (create, create-drop, update)

#### 🐇 Interface do RabbitMQ
Após iniciar os containers, o painel do RabbitMQ estará disponível em:
```bash
http://localhost:15672
```
Usuário: rabbitmq
Senha: rabbitmq

#### 📬 Testando a API
A API principal será inicializada por padrão em:
```bash
http://localhost:8080
```

Você pode testar os endpoints utilizando:
* [Postman](https://www.postman.com/);
* [Insomnia](https://insomnia.rest/download);
* Ou diretamente via curl ou navegador (para endpoints GET públicos)

#### 📚 Documentação com Swagger

A API da Remediar possui documentação interativa gerada automaticamente com o **Swagger**, permitindo explorar os endpoints disponíveis, seus parâmetros, respostas esperadas e realizar testes diretamente pela interface.

Após iniciar a aplicação, acesse a documentação Swagger através do seguinte link:

```bash
http://localhost:8080/swagger-ui/index.html
```