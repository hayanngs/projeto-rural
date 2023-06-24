package dev.hayann.dto;

public class ProducaoAnualDTO {

    public String nam_propriedade;

    public Double area_propriedade;

    public String desc_produto;

    public Double qtd_real_colhida;

    public ProducaoAnualDTO(String nam_propriedade, Double area_propriedade, String desc_produto, Double qtd_real_colhida) {
        this.nam_propriedade = nam_propriedade;
        this.area_propriedade = area_propriedade;
        this.desc_produto = desc_produto;
        this.qtd_real_colhida = qtd_real_colhida;
    }
}
