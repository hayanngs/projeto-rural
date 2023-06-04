package dev.hayann.model;

import java.time.LocalDate;

public class Producao implements Entity {

    public static final String TABLE_NAME = "producao";

    public static final String COLLUMN_ID_NAME = "id_producao";

    public static final String COLLUMN_ID_PROPRIEDADE_NAME = "id_propriedade";

    public static final String COLLUMN_ID_PRODUTO_NAME = "id_produto";

    public static final String COLLUMN_DATA_INICIO_PROV_COLHEITA_NAME = "data_ini_colheita_prov";

    public static final String COLLUMN_DATA_FIM_PROV_COLHEITA_NAME = "data_fim_colheita_prov";

    public static final String COLLUMN_QTD_PROV_COLHIDA_NAME = "qtd_prov_colhida";

    public static final String COLLUMN_DATA_INICIO_REAL_COLHEITA_NAME = "data_ini_colheita_real";

    public static final String COLLUMN_DATA_FIM_REAL_COLHEITA_NAME = "data_fim_colheita_real";

    public static final String COLLUMN_QTD_REAL_COLHIDA_NAME = "qtd_real_colhida";

    private Integer id;

    private Propriedade propriedade;

    private Produto produto;

    private LocalDate dataInicioColheitaProv;

    private LocalDate dataFimColheitaProv;

    private Double qtdProvColhida;

    private LocalDate dataInicioColheitaReal;

    private LocalDate dataFimColheitaReal;

    private Double qtdRealColhida;

    public Producao(
            Integer id,
            LocalDate dataInicioColheitaProv,
            LocalDate dataFimColheitaProv,
            Double qtdProvColhida,
            LocalDate dataInicioColheitaReal,
            LocalDate dataFimColheitaReal,
            Double qtdRealColhida,
            Propriedade propriedade,
            Produto produto
        ) {
        this.id = id;
        this.dataInicioColheitaProv = dataInicioColheitaProv;
        this.dataFimColheitaProv = dataFimColheitaProv;
        this.qtdProvColhida = qtdProvColhida;
        this.dataInicioColheitaReal = dataInicioColheitaReal;
        this.dataFimColheitaReal = dataFimColheitaReal;
        this.qtdRealColhida = qtdRealColhida;
        this.propriedade = propriedade;
        this.produto = produto;
    }

    public Producao(
            LocalDate dataInicioColheitaProv,
            LocalDate dataFimColheitaProv,
            Double qtdProvColhida,
            LocalDate dataInicioColheitaReal,
            LocalDate dataFimColheitaReal,
            Double qtdRealColhida,
            Propriedade propriedade,
            Produto produto
    ) {
        this.dataInicioColheitaProv = dataInicioColheitaProv;
        this.dataFimColheitaProv = dataFimColheitaProv;
        this.qtdProvColhida = qtdProvColhida;
        this.dataInicioColheitaReal = dataInicioColheitaReal;
        this.dataFimColheitaReal = dataFimColheitaReal;
        this.qtdRealColhida = qtdRealColhida;
        this.propriedade = propriedade;
        this.produto = produto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataInicioColheitaProv() {
        return dataInicioColheitaProv;
    }

    public void setDataInicioColheitaProv(LocalDate dataInicioColheitaProv) {
        this.dataInicioColheitaProv = dataInicioColheitaProv;
    }

    public LocalDate getDataFimColheitaProv() {
        return dataFimColheitaProv;
    }

    public void setDataFimColheitaProv(LocalDate dataFimColheitaProv) {
        this.dataFimColheitaProv = dataFimColheitaProv;
    }

    public Double getQtdProvColhida() {
        return qtdProvColhida;
    }

    public void setQtdProvColhida(Double qtdProvColhida) {
        this.qtdProvColhida = qtdProvColhida;
    }

    public LocalDate getDataInicioColheitaReal() {
        return dataInicioColheitaReal;
    }

    public void setDataInicioColheitaReal(LocalDate dataInicioColheitaReal) {
        this.dataInicioColheitaReal = dataInicioColheitaReal;
    }

    public LocalDate getDataFimColheitaReal() {
        return dataFimColheitaReal;
    }

    public void setDataFimColheitaReal(LocalDate dataFimColheitaReal) {
        this.dataFimColheitaReal = dataFimColheitaReal;
    }

    public Double getQtdRealColhida() {
        return qtdRealColhida;
    }

    public void setQtdRealColhida(Double qtdRealColhida) {
        this.qtdRealColhida = qtdRealColhida;
    }

    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
