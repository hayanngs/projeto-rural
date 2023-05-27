CREATE SEQUENCE municipio_seq INCREMENT 1 START 1;
CREATE SEQUENCE propriedade_seq INCREMENT 1 START 1;
CREATE SEQUENCE produto_seq INCREMENT 1 START 1;
CREATE SEQUENCE proprietario_seq INCREMENT 1 START 1;

CREATE TABLE municipio (
	id_mun int NOT NULL DEFAULT NEXT VALUE FOR municipio_seq,
	nam_mun varchar(50) NOT NULL,
	uf_mun varchar(2) NOT NULL,
	CONSTRAINT municipio_pkey PRIMARY KEY (id_mun)
);

CREATE TABLE propriedade (
	id_propriedade int NOT NULL DEFAULT NEXT VALUE FOR propriedade_seq,
	nam_propriedade varchar(100) NOT NULL,
	area_propriedade float NOT NULL,
	dist_mun_propriedade float NOT NULL,
	valor_aquisicao float,
	CONSTRAINT propriedade_pkey PRIMARY KEY (id_propriedade)
);

CREATE TABLE produto (
	id_produto int NOT NULL DEFAULT NEXT VALUE FOR produto_seq,
	desc_produto text NOT NULL,
	CONSTRAINT produto_pkey PRIMARY KEY (id_produto)
);

CREATE TABLE producao (
	id_propriedade int NOT NULL,
	id_produto int NOT NULL,
	data_ini_colheita_prov timestamp NOT NULL,
	data_ini_colheita_prov timestamp NOT NULL,
	qtd_prov_colhida numeric(10,2) NOT NULL,
	data_ini_colheita_real timestamp,
	data_ini_colheita_real timestamp,
	qtd_real_colhida numeric(10,2),
	CONSTRAINT produto_pkey PRIMARY KEY (id_propriedade, id_produto),
	CONSTRAINT fk_propriedade_producao FOREIGN KEY (id_propriedade) REFERENCES propriedade(id_propriedade),
	CONSTRAINT fk_produto_producao FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

CREATE TABLE proprietario (
	id_proprietario int NOT NULL DEFAULT NEXT VALUE FOR proprietario_seq,
	nam_proprietario varchar(100) NOT NULL,
	telefone_prorietario1 int NOT NULL,
	telefone_prorietario2 int,
	telefone_prorietario3 int,
	CONSTRAINT proprietario_pkey PRIMARY KEY (id_proprietario)
);
