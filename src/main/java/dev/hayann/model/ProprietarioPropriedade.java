package dev.hayann.model;

import java.time.LocalDate;

public class ProprietarioPropriedade {

    public static final String TABLE_NAME = "proprietario_propriedade";

    public static final String COLLUMN_ID_PROPRIEDADE_NAME = "id_propriedade";

    public static final String COLLUMN_ID_PROPRIETARIO_NAME = "id_proprietario";

    public static final String COLUMN_DATA_AQUISICAO_NAME = "data_aquisicao";

    private Integer idPropriedade;

    private Integer idProprietario;

    private LocalDate dateAcquisition;

    public ProprietarioPropriedade(Integer idPropriedade, Integer idProprietario, LocalDate dateAcquisition) {
        this.idPropriedade = idPropriedade;
        this.idProprietario = idProprietario;
        this.dateAcquisition = dateAcquisition;
    }

    public Integer getIdPropriedade() {
        return idPropriedade;
    }

    public void setIdPropriedade(Integer idPropriedade) {
        this.idPropriedade = idPropriedade;
    }

    public Integer getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(Integer idProprietario) {
        this.idProprietario = idProprietario;
    }

    public LocalDate getDateAcquisition() {
        return dateAcquisition;
    }

    public void setDateAcquisition(LocalDate dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }
}
