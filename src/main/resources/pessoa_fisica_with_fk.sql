CREATE TABLE pessoa_fisica
(
    id_proprietario_pf      int          NOT NULL,
    cpf_pf                  int          NOT NULL,
    rg_pf                   int          NOT NULL,
    nam_pf                  varchar(100) NOT NULL,
    data_nascimento_pf      date         NOT NULL,
    id_proprietario_conjuge int,
    CONSTRAINT pessoa_fisica_pkey PRIMARY KEY (id_proprietario_pf),
    CONSTRAINT fk_proprietario_pf FOREIGN KEY (id_proprietario_pf) REFERENCES proprietario (id_proprietario),
    CONSTRAINT fk_conjuge_pf FOREIGN KEY (id_proprietario_conjuge) REFERENCES pessoa_fisica (id_proprietario_pf)
);
