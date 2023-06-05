CREATE TABLE proprietario_propriedade
(
    id_propriedade  int  NOT NULL,
    id_proprietario int  NOT NULL,
    data_aquisicao  date NOT NULL,
    PRIMARY KEY (id_proprietario, id_propriedade),
    CONSTRAINT fk_propriedade_proprietario_propriedade FOREIGN KEY (id_propriedade) REFERENCES propriedade (id_propriedade),
    CONSTRAINT fk_proprietario_proprietario_propriedade FOREIGN KEY (id_proprietario) REFERENCES proprietario (id_proprietario)
);
