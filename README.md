# coop-vote-api

# Projeto Java para Gerenciamento de Sessões de Votação Cooperativa

# Nome do Projeto: "CoopVoteAPI"

# Descrição do Projeto:
O "CoopVoteAPI" é um projeto de solução back-end que permite o gerenciamento de sessões de votação cooperativa. Ele oferece uma API para que os associados possam participar de assembleias e tomar decisões através de votações.

# Funcionalidades da API:

# Criar uma nova sessão de votação:
Um associado com autorização poderá criar uma nova sessão de votação, definindo a pauta e o período de duração da votação.

# Iniciar uma sessão de votação:
O responsável poderá iniciar a votação para uma pauta específica, permitindo que os associados participem.

# Votar em uma sessão de votação:

Os associados poderão votar em uma sessão de votação em andamento. Cada associado possui um único voto.
# Encerrar uma sessão de votação:

Ao término do período de votação, o responsável encerra a sessão de votação e computa os resultados.

# Verificar resultado da votação:
Após o encerramento, os associados poderão consultar o resultado da votação.

# Gerenciar associados:
Possibilidade de cadastrar novos associados e atribuir autorização para criar sessões de votação.

# Autenticação e autorização:
A API deve ter um sistema de autenticação seguro para garantir que apenas associados autorizados possam criar sessões de votação.

# Tecnologias Utilizadas:
Linguagem: Java
Framework Web: Spring Boot
Banco de Dados: MySQL ou outro banco de dados relacional
Autenticação: JSON Web Tokens (JWT) para autenticação e autorização.

# CoopVoteAPI

A CoopVoteAPI é uma API REST desenvolvida em Java com Spring Boot para gerenciar sessões de votação em pautas de uma cooperativa. Cada associado pode votar em uma pauta e as decisões são tomadas por votação em assembleias.

# Funcionalidades
A API oferece as seguintes funcionalidades:

1. Cadastrar uma nova pauta
2. Abrir uma sessão de votação em uma pauta por um tempo determinado
3. Receber votos dos associados em pautas
4. Contabilizar os votos e dar o resultado da votação na pauta
5. Cadastrar um associado com nome, CPF e e-mail
6. Verificar se um associado pode votar através de integração com um sistema externo

# Endpoints
A API possui os seguintes endpoints:

POST /api/pautas: Cadastra uma nova pauta
GET /api/pautas: Lista todas as pautas cadastradas
GET /api/pautas/{id}: Obtém uma pauta pelo seu ID
POST /api/pautas/{id}/sessao: Abre uma sessão de votação em uma pauta
POST /api/votos: Registra um voto em uma pauta
POST /api/associados: Cadastra um novo associado
GET /api/associados: Lista todos os associados cadastrados
GET /api/associados/{id}: Obtém um associado pelo seu ID
GET /api/associados/cpf/{cpf}: Obtém um associado pelo seu CPF
GET /api/associados/email/{email}: Obtém um associado pelo seu e-mail


# Estruturas JSON

# POST /api/pautas: Cadastra uma nova pauta
Exemplo - localhost:8080/api/pautas

# JSON de saída:
{
    "titulo" : "Pauta 1",
    "sessaoAberta" : false,
    "resultadoVotacao" : null
}

# JSON de entrada:
{
    "id": 23,
    "titulo": "Pauta 1",
    "sessaoAberta": false,
    "duracaoEmSegundos": 60,
    "resultadoVotacao": null
}

# GET /api/pautas: Lista todas as pautas cadastradas
Exemplo - localhost:8080/api/pautas

# JSON de entrada:
[
    {
        "id": 23,
        "titulo": "Pauta 1",
        "sessaoAberta": false,
        "duracaoEmSegundos": 10,
        "resultadoVotacao": null
    },
    {
        "id": 24,
        "titulo": "Pauta 2",
        "sessaoAberta": true,
        "duracaoEmSegundos": 60,
        "resultadoVotacao": null
    }
]

# GET /api/pautas/{id}: Obtém uma pauta pelo seu ID
Exemplo - localhost:8080/api/pautas/23

# JSON de entrada:
{
    "id": 23,
    "titulo": "Pauta 1",
    "sessaoAberta": false,
    "duracaoEmSegundos": 10,
    "resultadoVotacao": "[0]votos = [SIM]. [0]votos = [Não]. [0]totais.   Resultado: Empate"
}

# POST /api/pautas/{id}/abrir-sessao?associadoId={id}: Abre uma sessão de votação em uma pauta
Exemplo - localhost:8080/api/pautas/1/abrir-sessao?associadoId=1

# JSON de saída:
{
    "id" : 23,
    "duracaoEmSegundos" : 10
}

# JSON de entrada:
"Sessão de votação aberta com sucesso!"

POST /api/votos: Registra um voto em uma pauta
Exemplo - localhost:8080/api/votos

# JSON de saída:
{
  "associado": {
    "cpf": "05883022014"
  },
  "pauta": {
   "id": 1
  },
  "voto": "Não"
}

# JSON de entrada:
{
    "id": 43,
    "voto": "Não",
    "pauta": {
        "id": 23,
        "titulo": "Pauta 1",
        "sessaoAberta": true,
        "duracaoEmSegundos": 300,
        "resultadoVotacao": null
    },
    "associado": {
        "id": 8,
        "nome": "Isa Tinner",
        "cpf": "05883022014",
        "email": "isa.t@example.com"
    }
}

# POST /api/associados: Cadastra um novo associado
Exemplo - localhost:8080/api/associados

# JSON de saída:
 {
    "nome": "Marisa Lisboa",
    "cpf": "05883022014",
    "email": "marisa.l@example.com"
}

# JSON de entrada:
{
    "id": 8,
    "nome": "Marisa Lisboa",
    "cpf": "05883022014",
    "email": "marisa.l@example.com"
}

# GET /api/associados: Lista todos os associados cadastrados
Exemplo - localhost:8080/api/associados

# JSON de entrada:
[
    {
        "id": 1,
        "nome": "João",
        "cpf": "12345678901",
        "email": "joao@example.com"
    },
    {
        "id": 2,
        "nome": "Maria",
        "cpf": "98765432109",
        "email": "maria@example.com"
    },
    {
        "id": 3,
        "nome": "Pedro",
        "cpf": "11122233344",
        "email": "pedro@example.com"
    },
    {
        "id": 5,
        "nome": "Maria Mansin",
        "cpf": "85477236094",
        "email": "maria.m@example.com"
    }   
]

# GET /api/associados/{id}: Obtém um associado pelo seu ID
Exemplo - localhost:8080/api/associados/8

# JSON de entrada:
{
    "id": 8,
    "nome": "Marisa Lisboa",
    "cpf": "05883022014",
    "email": "marisa.l@example.com"
}

# GET /api/associados/cpf/{cpf}: Obtém um associado pelo seu CPF
Exemplo - localhost:8080/api/associados/cpf/05883022014

# JSON de entrada:
{
    "id": 8,
    "nome": "Marisa Lisboa",
    "cpf": "05883022014",
    "email": "marisa.l@example.com"
}

# GET /api/associados/email/{email}: Obtém um associado pelo seu e-mail
Exemplo - localhost:8080/api/associados/email/marisa.l@example.com

# JSON de entrada:
{
    "id": 8,
    "nome": "Marisa Lisboa",
    "cpf": "05883022014",
    "email": "marisa.l@example.com"
}


# OBS: 

# Criar o banco de dados "cooperapi" com a seguinte estrutura: 


CREATE TABLE pauta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    sessao_aberta TINYINT(1) DEFAULT 0,
    duracao_em_segundos INT DEFAULT 60,
    resultado_votacao VARCHAR(10),
    PRIMARY KEY (id)
);

CREATE TABLE associado (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (cpf),
    UNIQUE (email)
);

CREATE TABLE voto (
    id BIGINT NOT NULL AUTO_INCREMENT,
    pauta_id BIGINT NOT NULL,
    associado_id BIGINT NOT NULL,
    voto VARCHAR(5) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_voto_pauta FOREIGN KEY (pauta_id) REFERENCES pauta (id),
    CONSTRAINT fk_voto_associado FOREIGN KEY (associado_id) REFERENCES associado (id)
);

No arquivo application.properties conte a estrutura para conectar ao banco

spring.datasource.username=root
spring.datasource.password=root

