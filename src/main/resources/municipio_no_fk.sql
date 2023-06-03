CREATE TABLE municipio
(
    id_mun  int         NOT NULL DEFAULT NEXTVAL('municipio_seq'),
    nam_mun varchar(50) NOT NULL,
    uf_mun  varchar(2)  NOT NULL,
    CONSTRAINT municipio_pkey PRIMARY KEY (id_mun)
);
