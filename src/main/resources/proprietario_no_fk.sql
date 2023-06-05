CREATE TABLE proprietario
(
    id_proprietario       int          NOT NULL DEFAULT NEXTVAL('proprietario_seq'),
    nam_proprietario      varchar(100) NOT NULL,
    telefone_prorietario1 Numeric(12)  NOT NULL,
    telefone_prorietario2 Numeric(12),
    telefone_prorietario3 Numeric(12),
    CONSTRAINT proprietario_pkey PRIMARY KEY (id_proprietario)
);
