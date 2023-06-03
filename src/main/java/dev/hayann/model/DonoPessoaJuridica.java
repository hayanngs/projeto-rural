package dev.hayann.model;

import java.time.LocalDate;

public class DonoPessoaJuridica {

    public static final String TABLE_NAME = "dono_pj";

    public static final String COLLUMN_ID_PROPRIETARIO_PF_NAME = "id_proprietario_pf";

    public static final String COLLUMN_ID_PROPRIETARIO_PJ_NAME = "id_proprietario_pj";

    private Integer idProprietarioPessoaFisica;

    private Integer idProprietarioPessoaJuridica;

    public DonoPessoaJuridica(Integer idProprietarioPessoaFisica, Integer idProprietarioPessoaJuridica) {
        this.idProprietarioPessoaFisica = idProprietarioPessoaFisica;
        this.idProprietarioPessoaJuridica = idProprietarioPessoaJuridica;
    }

    public Integer getIdProprietarioPessoaFisica() {
        return idProprietarioPessoaFisica;
    }

    public void setIdProprietarioPessoaFisica(Integer idProprietarioPessoaFisica) {
        this.idProprietarioPessoaFisica = idProprietarioPessoaFisica;
    }

    public Integer getIdProprietarioPessoaJuridica() {
        return idProprietarioPessoaJuridica;
    }

    public void setIdProprietarioPessoaJuridica(Integer idProprietarioPessoaJuridica) {
        this.idProprietarioPessoaJuridica = idProprietarioPessoaJuridica;
    }

    @Override
    public String toString() {
        return "DonoPessoaJuridica{" +
                "idProprietarioPessoaFisica=" + idProprietarioPessoaFisica +
                ", idProprietarioPessoaJuridica=" + idProprietarioPessoaJuridica +
                '}';
    }
}
