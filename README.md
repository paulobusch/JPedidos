# JPedidos
Sistema fictício para controle de pedidos.

## Requisitos técnicos obrigatórios
- Linguagem de programação Java
- Banco de dados MySQL
- GUI Desktop usando Java Swing
- Testes de unidade com JUnit 5 e Mockito
    - 90% de line coverage
- No frameworks policy: o projeto não deve incluir muitas dependências, salvo as autorizadas.

## Tipos de usuário

- Admin: função única de adicionar novos usuários.

- Gerente: cadastrar/alterar produtos, listar os pedidos.

- Funcionário: cadastrar um novo pedido. 

## Estórias de usuário

- E1 - realizar login

Como um usuário, eu quero logar no sistema para acessar minhas funcionalidades no sistema de pedidos.

- E2 - fazer pedido

Como um funcionário, eu quero registrar um pedido com produtos para passar a equipe, informar o valor total e atender ao cliente.

- E3 - adicionar usuário

Como um admin, eu quero inserir, atualizar e remover usuários de diferentes tipos. 

- E4 - Cadastrar produto

Como um gerente, eu quero inserir, atualizar e remover produtos para que possam ser vendidos aos clientes.

- E5 - listar pedidos

Como um gerente e funcionário, eu quero listar os pedidos para acompanhar as vendas. 

## Regras de negócio

- Usuário: possui nome completo, login, senha, e-mail e é de um único tipo (ver tipos de usuários). 

- Produto: possui nome curto, descrição e preço unitário. 

- Pedido: possui o nome e telefone do cliente, e contém um ou mais produtos associados. Cada produto tem uma quantidade de no mínimo 1 (um). O valor total do pedido deve ser calculado. 

## Estrutura básica do projeto

- Utilize a estrutura do maven aqui disponibilizada.
- Instale o Maven
- Use '>> mvn clean compile assembly:single' para gerar um jar com todas as dependências.
- Use '>> mvn test' para executar os testes de unidade.
- Use '>> mvn clean verify' para cobertura de código. Então acesse o relatório em 'firefox target/site/jacoco/index.html'

## Dúvidas

Para dúvidas sobre os requisitos, abra uma issue com a sua questão. 