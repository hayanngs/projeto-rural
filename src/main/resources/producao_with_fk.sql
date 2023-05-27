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
