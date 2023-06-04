package dev.hayann.model;

public class Propriedade implements Entity {

    public static final String TABLE_NAME = "propriedade";

    public static final String COLLUMN_ID_NAME = "id_propriedade";

    public static final String COLLUMN_ID_MUNICIPIO_NAME = "id_mun";

    public static final String COLLUMN_NAME_NAME = "nam_propriedade";

    public static final String COLLUMN_AREA_PROPRIEDADE_NAME = "area_propriedade";

    public static final String COLLUMN_DISTANCIA_MUNICIPIO_NAME = "dist_mun_propriedade";

    public static final String COLLUMN_VALOR_AQUISICAO_NAME = "valor_aquisicao";

    private Integer id;

    private Municipio municipio;

    private String name;

    private Double areaPropriedade;

    private Double distanciaMunicipio;

    private Double valorAquisicao;

    public Propriedade(Integer id, String name, Double areaPropriedade, Double distanciaMunicipio, Double valorAquisicao, Municipio municipio) {
        this.id = id;
        this.name = name;
        this.areaPropriedade = areaPropriedade;
        this.distanciaMunicipio = distanciaMunicipio;
        this.valorAquisicao = valorAquisicao;
        this.municipio = municipio;
    }

    public Propriedade(Integer id, String name, Double areaPropriedade, Double distanciaMunicipio, Double valorAquisicao) {
        this.id = id;
        this.name = name;
        this.areaPropriedade = areaPropriedade;
        this.distanciaMunicipio = distanciaMunicipio;
        this.valorAquisicao = valorAquisicao;
    }

    public Propriedade(String name, Double areaPropriedade, Double distanciaMunicipio, Double valorAquisicao, Municipio municipio) {
        this.name = name;
        this.areaPropriedade = areaPropriedade;
        this.distanciaMunicipio = distanciaMunicipio;
        this.valorAquisicao = valorAquisicao;
        this.municipio = municipio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAreaPropriedade() {
        return areaPropriedade;
    }

    public void setAreaPropriedade(Double areaPropriedade) {
        this.areaPropriedade = areaPropriedade;
    }

    public Double getDistanciaMunicipio() {
        return distanciaMunicipio;
    }

    public void setDistanciaMunicipio(Double distanciaMunicipio) {
        this.distanciaMunicipio = distanciaMunicipio;
    }

    public Double getValorAquisicao() {
        return valorAquisicao;
    }

    public void setValorAquisicao(Double valorAquisicao) {
        this.valorAquisicao = valorAquisicao;
    }

    @Override
    public String toString() {
        return name;
    }
}
