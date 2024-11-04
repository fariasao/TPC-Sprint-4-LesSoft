# LesSoft

## Sumário
- [Integrantes;](#integrantes)
- [Documentação completa;](#documentação-completa)
- [Instruções para uso da aplicação;](#instruções-para-uso-da-aplicação)
- [Link para vídeo com a proposta tecnológica;](#link-para-vídeo-com-o-passo-a-passo-do-deploy-e-utilização)
- [Listagem de Endpoints.](#listagem-de-endpoints)
- [Link para o Json Collection(Postman)](#json-collection)

## Integrantes
- Beatriz Lucas - RM99104
- Enzo Farias - RM98792
- Ewerton Gonçalves - RM98571
- Guilherme Tantulli - RM97890
- Thiago Zupelli - RM99085

## Documentação completa
[DEVOPS - 4SPRINT.pdf](https://github.com/user-attachments/files/17612689/DEVOPS.-.4SPRINT.pdf)

## Link para vídeo com o passo a passo do deploy e utilização
[![Vídeo TPC](http://img.youtube.com/vi/3V5XxM7scrQ/0.jpg)](https://youtu.be/bhwy2N7SPlg).

## Instruções para Deploy
1. **Vamos criar o Serviço de Aplicativo no Portal da Azure. Na aba Básico deixe as propriedades dessa forma**:
    - Grupo de recursos Novo: SpringApi-webapp
    - Nome: LesSoft-API
    - Codigo
    - pilha de runtime: Java 21
    - Pilha de serviço: java SE
    - Sistema Operacional: Windowns
    - Região: Brasil South
    - Plano do windowns (novo):API-LesSoft
    - Plano de preços: Gratuito F1
2. **Implantação** 
    - Na aba Implantação deixe o opção de Habilitar a Implantação Contínua e autorize o acesso do Azure em sua conta no GitHub 
    - Ainda na aba Implantação informe sua conta no GitHub, faça o fork deste repositorio e o selecione junto com a branch para Build e Deploy
3. **Rede**
    - Na aba Rede deixe o Acesso Público Ativado
4. **Monitoramento**
    - Na aba Monitoramento deixe o Application Insights desabilitado para esse projeto
5. **Revisar e Criar**
    - vá até a aba Revisar e Criar, analise as propriedades e, estando tudo OK, clique em Criar
6. **Build e Deploy**
    - Aguarde o Build e o Deploy
    - no navegador insira o sufixo https://lessoft-api.azurewebsites.net/
7. **Testes**
    - Utilizando nossa Json Collection, execute testes no postman
    - Verifique sua persistencia no banco oracle configurado no App

## Json Collection
[Postman](Less%20Soft%20-%20Challenge.postman_collection.json)

## Desenho Da arquitetura da solução
[Arquitetura](arquitetura.png)

## Instruções para uso da aplicação
### Iniciando a Aplicação

1. **Execução da Aplicação**:
    - Para iniciar a aplicação, você deve executar o arquivo **`TpcApplication.java`**. Isso pode ser feito diretamente dentro de sua IDE (Ambiente de Desenvolvimento Integrado) de preferência.
    - Localize o arquivo **`TpcApplication.java`** na pasta de projetos.
    - Clique com o botão direito no arquivo e selecione **`Run 'TpcApplication'`** ou use o atalho para executar o arquivo diretamente, geralmente encontrado na barra de ferramentas ou menu de contexto.
2. **Verificação do Servidor**:
    - Após executar a aplicação, verifique se o servidor foi iniciado corretamente observando a saída no console da IDE, que deve indicar que o Spring Boot está rodando na porta **`8080`**.

### Acesso ao Banco de Dados

1. **Acessando o Oracle**:
    - Com a aplicação em execução, abra o SQL Developer e insira as credenciais necessarias, e rode o script sql no seu banco
    - volte no arquivo application.properties do projeto e atualize com as suas credenciais
2. **Interagindo com o Banco de Dados**:
    - Dentro do oracle, você pode executar comandos SQL para consultar ou modificar dados. Por exemplo, após fazer mudanças via sua aplicação, você pode verificar os efeitos dessas mudanças no banco de dados executando um comando **`SELECT`** apropriado.
    - Use a área de consulta no Oracle para inserir, atualizar, ou deletar dados manualmente, o que pode ser útil para testes rápidos ou manipulação de dados durante o desenvolvimento.

**Utilização da API**

### Exemplos de Requisições para a API
Lembrando que como esse app está builadado para ser rodado em nuvem, onde está localhost:8080 / trocar pelo link da nuvem - (link utilizado em video https://lessoft-sprint4-rm99104.azurewebsites.net)

Aqui estão exemplos de como interagir com cada tipo de recurso:

**1. Users**

- **Listar todos os usuários**
    
    ```bash
    GET http://localhost:8080/users
    
    ```
    
- **Buscar um usuário pelo ID**
    
    ```bash
    GET http://localhost:8080/users/{id}
    
    ```
    
- **Criar um novo usuário**
    
    ```bash
    POST http://localhost:8080/users
    -Header 'Content-Type: application/json'
    -data '{
        "nome": "Thiago",
        "sobrenome": "Zupelli",
        "email": "rm99085@fiap.com.br",
        "password": "123silva4",
        "telefone": "12345678910",
        "endereco": "Av. Paulista",
        "numero": "1100",
        "complemento": "",
        "ativo": true
    }'
    
    ```
    
- **Atualizar um usuário**
    
    ```bash
    PUT http://localhost:8080/users/{id}
    -Header 'Content-Type: application/json'
    -data '{
      "nome": "Thiago",
        "sobrenome": "Zupelli",
        "email": "rm99085@fiap.com.br",
        "password": "123silva4",
        "telefone": "12345678910",
        "endereco": "Av. Paulista",
        "numero": "1100",
        "complemento": "",
        "ativo": true
    }'
    
    ```
    
- **Deletar um usuário**
    
    ```bash
    DELETE http://localhost:8080/users/{id}
    
    ```
    

**2. Produtos**

- **Listar todos os produtos**
    
    ```bash
    GET http://localhost:8080/produtos
    
    ```
    
- **Buscar um produto pelo ID**
    
    ```bash
    GET http://localhost:8080/produtos/{pdvId}
    
    ```
    
- **Criar um novo produto**
    
    ```bash
    POST http://localhost:8080/produtos
    -Header 'Content-Type: application/json'
    -data '{
      "categoriaId": 1,
      "nome": "Cafeteira Elétrica",
      "descricao": "Cafeteira com capacidade de 10 xícaras",
      "valor": 120.50,
      "ativo": 1,
      "compraPontosPedidoId": 2
    }'
    
    ```
    
- **Atualizar um produto**
    
    ```bash
    PUT http://localhost:8080/produtos/{pdvId}
    -Header: 'Content-Type: application/json'
    -data '{
      "categoriaId": 1,
      "nome": "Cafeteira Elétrica - Nova Geração",
      "descricao": "Nova cafeteira atualizada com capacidade para 15 xícaras",
      "valor": 150.75,
      "ativo": 1,
      "compraPontosPedidoId": 3
    }'
    
    ```
    
- **Deletar um produto**
    
    ```bash
    DELETE http://localhost:8080/produtos/{pdvId}
    
    ```
    

**3. Lojas, UserMaster, UserPDV, Notificações, Pontos, Compras, CompraPontos, e Campanhas**

- O formato para estes será similar ao descrito para Users e Produtos. Substitua o caminho do endpoint e os detalhes do corpo da requisição conforme apropriado para cada tipo de entidade. As operações incluirão listagem, busca por ID, criação, atualização e deleção.

### Respostas Esperadas

- **Sucesso**: As respostas para requisições bem-sucedidas incluirão os dados solicitados ou uma confirmação de sucesso.
    
    ```json
    
    {
      "status": "success",
      "data": {
        "id": 1,
        "nome": "Thiago",
        "sobrenome": "Zupelli",
        "email": "rm99085@fiap.com.br",
        "password": "123silva4",
        "telefone": "12345678910",
        "endereco": "Av. Paulista",
        "numero": "1100",
        "complemento": "",
        "ativo": true
      }
    }
    
    ```
    
- **Erro**: Respostas de erro fornecerão detalhes sobre o que deu errado.
    
    ```json
    
    {
      "status": "error",
      "message": "Usuário não encontrado.",
      "code": 404
    }
    
    ```
    

### Considerações Adicionais

- **Ambiente de Desenvolvimento**: Certifique-se de que sua IDE está configurada com o JDK apropriado para o projeto e com todas as dependências, definidas geralmente no **`pom.xml`** (Maven).

## Listagem de Endpoints
### Endpoints para Users:
GET /users - Buscar todos os usuários.\
GET /users/{usersID} - Buscar um usuário pelo ID.\
POST /users - Criar um novo usuário.\
PUT /users/{usersID} - Atualizar um usuário existente.\
DELETE /users/{usersID} - Deletar um usuário pelo ID.

### Endpoints para UserMaster:
GET /usermasters - Buscar todos os usuários mestres.\
GET /usermasters/{masterID} - Buscar um usuário mestre pelo ID.\
POST /usermasters - Criar um novo usuário mestre.\
PUT /usermasters/{masterID} - Atualizar um usuário mestre existente.\
DELETE /usermasters/{masterID} - Deletar um usuário mestre pelo ID.

### Endpoints para UserPDV:
GET /userpdv - Buscar todos os usuários PDV.\
GET /userpdv/{userPdvID} - Buscar um usuário PDV pelo ID.\
POST /userpdv - Criar um novo usuário PDV.\
PUT /userpdv/{userPdvID} - Atualizar um usuário PDV existente.\
DELETE /userpdv/{userPdvID} - Deletar um usuário PDV pelo ID.

### Endpoints para Loja:
GET /lojas - Buscar todas as lojas.\
GET /lojas/{pdvID} - Buscar uma loja pelo ID.\
POST /lojas - Criar uma nova loja.\
PUT /lojas/{pdvID} - Atualizar uma loja existente.\
DELETE /lojas/{pdvID} - Deletar uma loja pelo ID.

### Endpoints para Produtos:
GET /produtos - Buscar todos os produtos.\
GET /produtos/{produtoID} - Buscar um produto pelo ID.\
POST /produtos - Criar um novo produto.\
PUT /produtos/{produtoID} - Atualizar um produto existente.\
DELETE /produtos/{produtoID} - Deletar um produto pelo ID.

### Endpoints para Credit:
GET /credits - Buscar todos os créditos.\
GET /credits/{creditID} - Buscar um crédito pelo ID.\
POST /credits - Criar um novo crédito.\
PUT /credits/{creditID} - Atualizar um crédito existente.\
DELETE /credits/{creditID} - Deletar um crédito pelo ID.

### Endpoints para Pontos:
GET /pontos - Buscar todos os pontos.\
GET /pontos/{pointId} - Buscar um ponto pelo ID.\
POST /pontos - Criar novos pontos.\
PUT /pontos/{pointId} - Atualizar um ponto existente.\
DELETE /pontos/{pointId} - Deletar um ponto pelo ID.

### Endpoints para UserCluster:
GET /usercluster - Buscar todas as associações UserCluster.\
GET /usercluster/{userClusterID} - Buscar uma associação UserCluster pelo ID.\
POST /usercluster - Criar uma nova associação UserCluster.
PUT /usercluster/{userClusterID} - Atualizar uma associação UserCluster existente.\
DELETE /usercluster/{userClusterID} - Deletar uma associação UserCluster pelo ID.

### Endpoints para Notificacoes:
GET /notificacoes - Buscar todas as notificações.\
GET /notificacoes/{notificacoesID} - Buscar uma notificação pelo ID.\
POST /notificacoes - Criar uma nova notificação.\
PUT /notificacoes/{notificacoesID} - Atualizar uma notificação existente.\
DELETE /notificacoes/{notificacoesID} - Deletar uma notificação pelo ID.

### Endpoints para Compras:
GET /compras - Buscar todas as compras.\
GET /compras/{compraID} - Buscar uma compra pelo ID.\
POST /compras - Criar uma nova compra.\
PUT /compras/{compraID} - Atualizar uma compra existente.\
DELETE /compras/{compraID} - Deletar uma compra pelo ID.

### Endpoints para Campanhas:
GET /campanhas - Buscar todas as campanhas.\
GET /campanhas/{campanhaID} - Buscar uma campanha pelo ID.\
POST /campanhas - Criar uma nova campanha.\
PUT /campanhas/{campanhaID} - Atualizar uma campanha existente.\
DELETE /campanhas/{campanhaID} - Deletar uma campanha pelo ID.

### Endpoints para Categorias:
GET /categorias - Buscar todas as categorias.\
GET /categorias/{categoriaID} - Buscar uma categoria pelo ID.\
POST /categorias - Criar uma nova categoria.\
PUT /categorias/{categoriaID} - Atualizar uma categoria existente.\
DELETE /categorias/{categoriaID} - Deletar uma categoria pelo ID.

### Endpoints para Cluster:
GET /clusters - Buscar todos os clusters.\
GET /clusters/{clusterID} - Buscar um cluster pelo ID.\
POST /clusters - Criar um novo cluster.\
PUT /clusters/{clusterID} - Atualizar um cluster existente.\
DELETE /clusters/{clusterID} - Deletar um cluster pelo ID.

### Endpoints para CreditCompras:
GET /creditcompras - Buscar todas as compras de pontos.\
GET /creditcompras/{creditCompraID} - Buscar uma compra de pontos pelo ID.\
POST /creditcompras - Criar uma nova compra de pontos.\
PUT /creditcompras/{creditCompraID} - Atualizar uma compra de pontos existente.\
DELETE /creditcompras/{creditCompraID} - Deletar uma compra de pontos pelo ID.

### Endpoints para PontosCompra:
GET /pontoscompras - Buscar todos os pontos.\
GET /pontoscompras/{pontosCompraID} - Buscar um ponto pelo ID.\
POST /pontoscompras - Criar novos pontos.\
PUT /pontoscompras/{pontosCompraID} - Atualizar um ponto existente.\
DELETE /pontoscompras/{pontosCompraID} - Deletar um ponto pelo ID.
