CREATE TABLE proprietario
(
    id_proprietario       int          NOT NULL DEFAULT NEXTVAL('proprietario_seq'),
    nam_proprietario      varchar(100) NOT NULL,
    telefone_prorietario1 int          NOT NULL,
    telefone_prorietario2 int,
    telefone_prorietario3 int,
    CONSTRAINT proprietario_pkey PRIMARY KEY (id_proprietario)
);
