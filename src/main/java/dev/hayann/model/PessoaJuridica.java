package dev.hayann.model;

import java.time.LocalDate;

public class PessoaJuridica {

    public static final String TABLE_NAME = "pessoa_juridica";

    public static final String COLLUMN_ID_PROPRIETARIO_NAME = "id_proprietario_pj";

    public static final String COLLUMN_CNPJ_NAME = "cnpj_pj";

    public static final String COLLUMN_RAZAO_SOCIAL_NAME = "razao_social_pj";

    public static final String COLUMN_DATA_CRIACAO_NAME = "data_criacao_pj";

    private Integer id;

    private Integer cnpj;

    private String razaoSocial;

    private LocalDate dateCreation;

    public PessoaJuridica(Integer id, Integer cnpj, String razaoSocial, LocalDate dateCreation) {
        this.id = id;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.dateCreation = dateCreation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCnpj() {
        return cnpj;
    }

    public void setCnpj(Integer cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return razaoSocial;
    }
}
