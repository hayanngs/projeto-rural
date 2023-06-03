CREATE TABLE produto
(
    id_produto   int  NOT NULL DEFAULT NEXTVAL('produto_seq'),
    desc_produto text NOT NULL,
    CONSTRAINT produto_pkey PRIMARY KEY (id_produto)
);
