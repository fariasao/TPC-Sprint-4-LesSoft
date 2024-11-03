SET SERVEROUTPUT ON;
-------------------------------------------------------------
-- DROPS DAS TABELAS E RELACIONAMENTOS 
-------------------------------------------------------------

-- Remover relacionamentos 
ALTER TABLE tb_pontos_compra DROP CONSTRAINT tb_pontos_c_id_comprasid_fk;
ALTER TABLE tb_pontos_compra DROP CONSTRAINT tb_pontos_c_id_pontosid_fk;

ALTER TABLE tb_credit_compras DROP CONSTRAINT tb_credit_c_id_comprasid_fk;
ALTER TABLE tb_credit_compras DROP CONSTRAINT tb_credit_c_id_creditid_fk;

ALTER TABLE tb_compras DROP CONSTRAINT tb_compras_id_pdvid_fk;
ALTER TABLE tb_compras DROP CONSTRAINT tb_compras_id_usersid_fk;

ALTER TABLE tb_campanhas DROP CONSTRAINT tb_campanhas_id_clusterid_fk;
ALTER TABLE tb_campanhas DROP CONSTRAINT tb_campanhas_id_masterid_fk;

ALTER TABLE tb_produtos DROP CONSTRAINT tb_produtos_id_categoriaid_fk;
ALTER TABLE tb_produtos DROP CONSTRAINT tb_produtos_id_pdvid_fk;

ALTER TABLE tb_user_cluster DROP CONSTRAINT tb_user_cluster_id_cluster_fk;
ALTER TABLE tb_user_cluster DROP CONSTRAINT tb_user_cluster_id_users_fk;

ALTER TABLE tb_user_pdv DROP CONSTRAINT tb_user_pdv_id_pdvid_fk;

ALTER TABLE tb_notificacoes DROP CONSTRAINT tb_notificacoes_id_pdvid_fk;

-- remover as tabelas
DROP TABLE tb_pontos_compra;
DROP TABLE tb_credit_compras;
DROP TABLE tb_compras;
DROP TABLE tb_campanhas;
DROP TABLE tb_produtos;
DROP TABLE tb_user_cluster;
DROP TABLE tb_user_pdv;
DROP TABLE tb_notificacoes;
DROP TABLE tb_pontos;
DROP TABLE tb_credit;
DROP TABLE tb_loja;
DROP TABLE tb_users;
DROP TABLE tb_usermaster;
DROP TABLE tb_cluster;
DROP TABLE tb_categorias;


-- Criar tabelas e relacionamentos.

CREATE TABLE tb_campanhas (
    campanhaid INTEGER NOT NULL,
    masterid   INTEGER NOT NULL,
    clusterid  INTEGER NOT NULL,
    titulo     VARCHAR2(255 BYTE) NOT NULL,
    conteudo   VARCHAR2(255 BYTE) NOT NULL,
    descricao  VARCHAR2(255 BYTE) NOT NULL,
    canaltipo  INTEGER NOT NULL
);

ALTER TABLE tb_campanhas ADD CONSTRAINT tb_campanhas_pk PRIMARY KEY ( campanhaid );

CREATE TABLE tb_categorias (
    categoriaid INTEGER NOT NULL,
    nome        VARCHAR2(255 BYTE) NOT NULL,
    descricao   VARCHAR2(255 BYTE) NOT NULL,
    ativo       CHAR(1) NOT NULL
);

ALTER TABLE tb_categorias ADD CONSTRAINT tb_categorias_pk PRIMARY KEY ( categoriaid );

CREATE TABLE tb_cluster (
    clusterid INTEGER NOT NULL,
    name      VARCHAR2(255 BYTE) NOT NULL,
    descricao VARCHAR2(255 BYTE) NOT NULL
);

ALTER TABLE tb_cluster ADD CONSTRAINT tb_cluster_pk PRIMARY KEY ( clusterid );

CREATE TABLE tb_compras (
    compraid   INTEGER NOT NULL,
    usersid    INTEGER NOT NULL,
    pdvid      INTEGER NOT NULL,
    valor      NUMBER(15, 2) NOT NULL,
    datacompra DATE NOT NULL
);

ALTER TABLE tb_compras ADD CONSTRAINT tb_compras_pk PRIMARY KEY ( compraid );

CREATE TABLE tb_credit (
    creditid      INTEGER NOT NULL,
    valor         NUMBER(15, 2) NOT NULL,
    datacredito   DATE NOT NULL,
    dataexpiracao DATE NOT NULL,
    utilizado     CHAR(1) NOT NULL
);

ALTER TABLE tb_credit ADD CONSTRAINT tb_credit_pk PRIMARY KEY ( creditid );

CREATE TABLE tb_credit_compras (
    creditid INTEGER NOT NULL,
    compraid INTEGER NOT NULL
);

CREATE TABLE tb_loja (
    pdvid       INTEGER NOT NULL,
    nomeloja    VARCHAR2(255 BYTE) NOT NULL,
    endereco    VARCHAR2(255 BYTE) NOT NULL,
    numero      INTEGER NOT NULL,
    complemento VARCHAR2(255 BYTE),
    cep         VARCHAR2(9 BYTE) NOT NULL,
    ativo       CHAR(1) NOT NULL
);

ALTER TABLE tb_loja ADD CONSTRAINT tb_loja_pk PRIMARY KEY ( pdvid );

CREATE TABLE tb_notificacoes (
    notificacoesid INTEGER NOT NULL,
    pdvid          INTEGER NOT NULL,
    titulo         VARCHAR2(255 BYTE) NOT NULL,
    mensagem       VARCHAR2(255 BYTE) NOT NULL,
    dataenvio      DATE NOT NULL
);

ALTER TABLE tb_notificacoes ADD CONSTRAINT tb_notificacoes_pk PRIMARY KEY ( notificacoesid );

CREATE TABLE tb_pontos (
    pointid       INTEGER NOT NULL,
    valor         NUMBER(15, 2) NOT NULL,
    datacreditado DATE NOT NULL,
    dataexpirado  DATE NOT NULL,
    utilizado     CHAR(1) NOT NULL
);

ALTER TABLE tb_pontos ADD CONSTRAINT tb_pontos_pk PRIMARY KEY ( pointid );

CREATE TABLE tb_pontos_compra (
    compraid INTEGER NOT NULL,
    pointid  INTEGER NOT NULL
);

CREATE TABLE tb_produtos (
    produtoid   INTEGER NOT NULL,
    pdvid       INTEGER NOT NULL,
    categoriaid INTEGER NOT NULL,
    nome        VARCHAR2(255 BYTE) NOT NULL,
    descricao   VARCHAR2(255 BYTE),
    valor       NUMBER(15, 2) NOT NULL,
    ativo       CHAR(1) NOT NULL
);

ALTER TABLE tb_produtos ADD CONSTRAINT tb_produtosv1_pk PRIMARY KEY ( produtoid );

CREATE TABLE tb_user_cluster (
    userclusterid INTEGER NOT NULL,
    clusterid     INTEGER NOT NULL,
    usersid        INTEGER NOT NULL
);

ALTER TABLE tb_user_cluster ADD CONSTRAINT tb_user_cluster_pk PRIMARY KEY ( userclusterid );

CREATE TABLE tb_user_pdv (
    userpdvid    INTEGER NOT NULL,
    pdvid        INTEGER NOT NULL,
    nome         VARCHAR2(255 BYTE) NOT NULL,
    sobrenome    VARCHAR2(255 BYTE) NOT NULL,
    email        VARCHAR2(255 BYTE) NOT NULL,
    password     VARCHAR2(255 BYTE) NOT NULL,
    dataregistro DATE NOT NULL,
    ativo        CHAR(1) NOT NULL
);

ALTER TABLE tb_user_pdv ADD CONSTRAINT tb_userpdv_pk PRIMARY KEY ( userpdvid );

CREATE TABLE tb_usermaster (
    masterid     INTEGER NOT NULL,
    nome         VARCHAR2(255 BYTE) NOT NULL,
    sobrenome    VARCHAR2(255 BYTE) NOT NULL,
    email        VARCHAR2(255 BYTE) NOT NULL,
    password     VARCHAR2(255 BYTE) NOT NULL,
    dataregistro DATE NOT NULL,
    ativo        CHAR(1) NOT NULL
);

ALTER TABLE tb_usermaster ADD CONSTRAINT tb_usermaster_pk PRIMARY KEY ( masterid );

CREATE TABLE tb_users (
    usersid     INTEGER NOT NULL,
    nome        VARCHAR2(255 BYTE) NOT NULL,
    sobrenome   VARCHAR2(255 BYTE) NOT NULL,
    email       VARCHAR2(255 BYTE) NOT NULL,
    password    VARCHAR2(255 BYTE) NOT NULL,
    telefone    NUMBER(11) NOT NULL,
    endereco    VARCHAR2(255 BYTE) NOT NULL,
    numero      INTEGER NOT NULL,
    complemento VARCHAR2(255 BYTE),
    ativo       CHAR(1) NOT NULL
);

ALTER TABLE tb_users ADD CONSTRAINT tb_users_pk PRIMARY KEY ( usersid );

ALTER TABLE tb_campanhas
    ADD CONSTRAINT tb_campanhas_id_clusterid_fk FOREIGN KEY ( clusterid )
        REFERENCES tb_cluster ( clusterid );

ALTER TABLE tb_campanhas
    ADD CONSTRAINT tb_campanhas_id_masterid_fk FOREIGN KEY ( masterid )
        REFERENCES tb_usermaster ( masterid );

ALTER TABLE tb_compras
    ADD CONSTRAINT tb_compras_id_pdvid_fk FOREIGN KEY ( pdvid )
        REFERENCES tb_loja ( pdvid );

ALTER TABLE tb_compras
    ADD CONSTRAINT tb_compras_id_usersid_fk FOREIGN KEY ( usersid )
        REFERENCES tb_users ( usersid );

ALTER TABLE tb_credit_compras
    ADD CONSTRAINT tb_credit_c_id_comprasid_fk FOREIGN KEY ( compraid )
        REFERENCES tb_compras ( compraid );

ALTER TABLE tb_credit_compras
    ADD CONSTRAINT tb_credit_c_id_creditid_fk FOREIGN KEY ( creditid )
        REFERENCES tb_credit ( creditid );

ALTER TABLE tb_notificacoes
    ADD CONSTRAINT tb_notificacoes_id_pdvid_fk FOREIGN KEY ( pdvid )
        REFERENCES tb_loja ( pdvid );

ALTER TABLE tb_pontos_compra
    ADD CONSTRAINT tb_pontos_c_id_comprasid_fk FOREIGN KEY ( compraid )
        REFERENCES tb_compras ( compraid );

ALTER TABLE tb_pontos_compra
    ADD CONSTRAINT tb_pontos_c_id_pontosid_fk FOREIGN KEY ( pointid )
        REFERENCES tb_pontos ( pointid );

ALTER TABLE tb_produtos
    ADD CONSTRAINT tb_produtos_id_categoriaid_fk FOREIGN KEY ( categoriaid )
        REFERENCES tb_categorias ( categoriaid );

ALTER TABLE tb_produtos
    ADD CONSTRAINT tb_produtos_id_pdvid_fk FOREIGN KEY ( pdvid )
        REFERENCES tb_loja ( pdvid );

ALTER TABLE tb_user_cluster
    ADD CONSTRAINT tb_user_cluster_id_cluster_fk FOREIGN KEY ( clusterid )
        REFERENCES tb_cluster ( clusterid );

ALTER TABLE tb_user_cluster
    ADD CONSTRAINT tb_user_cluster_id_users_fk FOREIGN KEY ( usersid )
        REFERENCES tb_users ( usersid );

ALTER TABLE tb_user_pdv
    ADD CONSTRAINT tb_user_pdv_id_pdvid_fk FOREIGN KEY ( pdvid )
        REFERENCES tb_loja ( pdvid );