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

## Conta de administrador

O sistema, quando é executado pela primeira vez, cria uma conta de administrador para que possam ser realizadas as operações de criação de categoria, localidade e projeto. Ele também cria uma categoria, localidade e projeto de teste para facilitar os testes.

Para logar nesta conta, utilize o endpoint `/login` com as seguintes informações:

cpf: 796.373.610-49
Senha: teste123

O token será enviado pelo header Authorization, e este deve ser enviado em todas as requisições subsequentes que necessitem do usuário logado no sistema.

## Endpoints

Quando a API for executada, os endpoints estarão disponíveis no Swagger, no link http://localhost:4200/swagger-ui.html .

## Endpoints principais

Post em `/despesas/` cria uma despesa.

Parâmetros:

{
  "categoriaId": inteiro,
  "descricao": "string",
  "notaFiscal": {
    "dataDeEmissao": "string",
    "id": inteiro (opcional, se não for enviado usa o dados da nota fiscal),
    "linkDaImagem": "string"
  },
  "projetoId": inteiro,
  "valor": inteiro
}

Patch em /despesas/{id}/aprovar/ ou /despesas/{id}/desaprovar/

Aprova ou desaprova uma despesa. Só o diretor (a conta de administrador) pode aprovar ou desaprovar uma despesa, ou um colaborador que tenha o cargo de diretor ou gerente. Quando uma despesa é aprovada, a verba respectiva ao valor da despesa é descontada do projeto em que ela foi cadastrada.

Get em /despesas/projetos/{idProjeto}/paraAprovacao

Lista as despesas que foram enviadas para aprovação.

Get em /notasfiscais/{id}/ lista uma nota fiscal. Se várias despesas forem inseridas numa mesma nota fiscal, o valor da nota será devidamente somado.