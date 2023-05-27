CREATE SEQUENCE municipio_seq INCREMENT 1 START 1;
CREATE SEQUENCE propriedade_seq INCREMENT 1 START 1;
CREATE SEQUENCE produto_seq INCREMENT 1 START 1;
CREATE SEQUENCE proprietario_seq INCREMENT 1 START 1;

CREATE TABLE municipio
(
    id_mun  int         NOT NULL DEFAULT NEXTVAL('municipio_seq'),
    nam_mun varchar(50) NOT NULL,
    uf_mun  varchar(2)  NOT NULL,
    CONSTRAINT municipio_pkey PRIMARY KEY (id_mun)
);

CREATE TABLE propriedade
(
    id_propriedade       int          NOT NULL DEFAULT NEXTVAL('propriedade_seq'),
    nam_propriedade      varchar(100) NOT NULL,
    area_propriedade     float        NOT NULL,
    dist_mun_propriedade float        NOT NULL,
    valor_aquisicao      float,
    CONSTRAINT propriedade_pkey PRIMARY KEY (id_propriedade)
);

CREATE TABLE produto
(
    id_produto   int  NOT NULL DEFAULT NEXTVAL('produto_seq'),
    desc_produto text NOT NULL,
    CONSTRAINT produto_pkey PRIMARY KEY (id_produto)
);

CREATE TABLE producao
(
    id_propriedade         int            NOT NULL,
    id_produto             int            NOT NULL,
    data_ini_colheita_prov date           NOT NULL,
    data_fim_colheita_prov date           NOT NULL,
    qtd_prov_colhida       numeric(10, 2) NOT NULL,
    data_ini_colheita_real date,
    data_fim_colheita_real date,
    qtd_real_colhida       numeric(10, 2),
    CONSTRAINT producao_pkey PRIMARY KEY (id_propriedade, id_produto),
    CONSTRAINT fk_propriedade_producao FOREIGN KEY (id_propriedade) REFERENCES propriedade (id_propriedade),
    CONSTRAINT fk_produto_producao FOREIGN KEY (id_produto) REFERENCES produto (id_produto)
);

CREATE TABLE proprietario
(
    id_proprietario       int          NOT NULL DEFAULT NEXTVAL('proprietario_seq'),
    nam_proprietario      varchar(100) NOT NULL,
    telefone_prorietario1 int          NOT NULL,
    telefone_prorietario2 int,
    telefone_prorietario3 int,
    CONSTRAINT proprietario_pkey PRIMARY KEY (id_proprietario)
);

CREATE TABLE pessoa_fisica
(
    id_proprietario_pf      int          NOT NULL,
    cpf_pf                  int          NOT NULL,
    rg_pf                   int          NOT NULL,
    nam_pf                  varchar(100) NOT NULL,
    data_nascimento_pf      date         NOT NULL,
    id_proprietario_conjuge int          NOT NULL,
    CONSTRAINT pessoa_fisica_pkey PRIMARY KEY (id_proprietario_pf),
    CONSTRAINT fk_proprietario_pf FOREIGN KEY (id_proprietario_pf) REFERENCES proprietario (id_proprietario),
    CONSTRAINT fk_conjuge_pf FOREIGN KEY (id_proprietario_conjuge) REFERENCES pessoa_fisica (id_proprietario_pf)
);

CREATE TABLE pessoa_juridica
(
    id_proprietario_pj int          NOT NULL,
    cnpj_pj            int UNIQUE   NOT NULL,
    razao_social_pj    varchar(100) NOT NULL,
    data_criacao_pj    date         NOT NULL,
    CONSTRAINT pessoa_juridica_pkey PRIMARY KEY (id_proprietario_pj),
    CONSTRAINT fk_proprietario_pj FOREIGN KEY (id_proprietario_pj) REFERENCES proprietario (id_proprietario)
);

CREATE TABLE proprietario_propriedade
(
    id_propriedade  int  NOT NULL,
    id_proprietario int  NOT NULL,
    data_aquisicao  date NOT NULL,
    CONSTRAINT fk_propriedade_proprietario_propriedade FOREIGN KEY (id_propriedade) REFERENCES propriedade (id_propriedade),
    CONSTRAINT fk_proprietario_proprietario_propriedade FOREIGN KEY (id_proprietario) REFERENCES proprietario (id_proprietario)
);

CREATE TABLE dono_pj
(
    id_proprietario_pf int NOT NULL,
    id_proprietario_pj int NOT NULL,
    CONSTRAINT fk_proprietario_pf_dono_pj FOREIGN KEY (id_proprietario_pf) REFERENCES proprietario (id_proprietario),
    CONSTRAINT fk_proprietario_pj_dono_pj FOREIGN KEY (id_proprietario_pj) REFERENCES proprietario (id_proprietario)
);
