CREATE TABLE dono_pj
(
    id_proprietario_pf int NOT NULL,
    id_proprietario_pj int NOT NULL,
    CONSTRAINT fk_proprietario_pf_dono_pj FOREIGN KEY (id_proprietario_pf) REFERENCES proprietario (id_proprietario),
    CONSTRAINT fk_proprietario_pj_dono_pj FOREIGN KEY (id_proprietario_pj) REFERENCES proprietario (id_proprietario)
);
