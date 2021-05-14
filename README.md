# Sistema de reembolso

## Objetivo

O objetivo do sistema foi criar uma API para simplificar o processo de reembolso dos Zuppers. Para isso, criamos uma API para cadastrar e gerenciar as despesas.

## Requisitos para rodar o projeto

As seguintes variáveis de ambiente são necessárias para rodar o projeto:

* `URL_BANCO` especifica a url do banco de dados. É assumido o prefixo `jdbc:mysql:` antes do nome da variável.

Exemplos de valores para essa variável: `//localhost:3306/reembolso`

* `USUARIO_BANCO` especifica em qual usuário do banco de dados o sistema irá rodar.

* `SENHA_BANCO` especifica a senha do usuário no banco de dados.

* `JWT_SEGREDO` especifica o segredo que será usado na geração do token JWT.

* `TEMPO_EXPIRAR_TOKEN` especifica em quanto tempo (em mili-segundos) que um token JWT permanece válido.

## Endpoints

Quando a API for executada, os endpoints disponíveis estarão disponíveis no Swagger, no link http://localhost:4200/swagger-ui.html .