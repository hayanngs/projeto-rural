CREATE TABLE propriedade
(
    id_propriedade       int          NOT NULL DEFAULT NEXTVAL('propriedade_seq'),
    nam_propriedade      varchar(100) NOT NULL,
    area_propriedade     float        NOT NULL,
    dist_mun_propriedade float        NOT NULL,
    valor_aquisicao      float,
    CONSTRAINT propriedade_pkey PRIMARY KEY (id_propriedade)
);
