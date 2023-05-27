CREATE TABLE pessoa_juridica
(
    id_proprietario_pj int          NOT NULL,
    cnpj_pj            int UNIQUE   NOT NULL,
    razao_social_pj    varchar(100) NOT NULL,
    data_criacao_pj    date         NOT NULL,
    CONSTRAINT pessoa_juridica_pkey PRIMARY KEY (id_proprietario_pj),
    CONSTRAINT fk_proprietario_pj FOREIGN KEY (id_proprietario_pj) REFERENCES proprietario (id_proprietario)
);
