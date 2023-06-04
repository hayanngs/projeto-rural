package dev.hayann.model;

import java.time.LocalDate;

public class PessoaFisica {

    public static final String TABLE_NAME = "pessoa_fisica";

    public static final String COLLUMN_ID_PROPRIETARIO_PF_NAME = "id_proprietario_pf";

    public static final String COLLUMN_CPF_NAME = "cpf_pf";

    public static final String COLLUMN_RG_NAME = "rg_pf";

    public static final String COLLUMN_NAME_NAME = "nam_pf";

    public static final String COLLUMN_ID_CONJUGE_NAME = "id_proprietario_conjuge";

    public static final String COLLUMN_DATA_NASCIMENTO_NAME = "data_nascimento_pf";

    private Integer idProprietarioPessoaFisica;

    private Integer cpf;

    private Integer rg;

    private String name;

    private Integer idConjuge;

    private LocalDate dataNascimento;

    public PessoaFisica(Integer idProprietarioPessoaFisica, Integer cpf, Integer rg, String name, Integer idConjuge, LocalDate dataNascimento) {
        this.idProprietarioPessoaFisica = idProprietarioPessoaFisica;
        this.cpf = cpf;
        this.rg = rg;
        this.name = name;
        this.idConjuge = idConjuge;
        this.dataNascimento = dataNascimento;
    }

    public Integer getIdProprietarioPessoaFisica() {
        return idProprietarioPessoaFisica;
    }

    public void setIdProprietarioPessoaFisica(Integer idProprietarioPessoaFisica) {
        this.idProprietarioPessoaFisica = idProprietarioPessoaFisica;
    }

    public Integer getCpf() {
        return cpf;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public Integer getRg() {
        return rg;
    }

    public void setRg(Integer rg) {
        this.rg = rg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdConjuge() {
        return idConjuge;
    }

    public void setIdConjuge(Integer idConjuge) {
        this.idConjuge = idConjuge;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return name;
    }
}
