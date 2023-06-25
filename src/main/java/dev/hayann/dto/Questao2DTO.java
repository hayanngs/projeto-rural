package dev.hayann.dto;

public class Questao2DTO {
    
    public String nomePf;
    
    public String razaoSocial;
    
    public String nomePropriedade;
    
    public Double areaPropriedade;
    
    public Double valorAquisicao;

    public Questao2DTO(String nomePf, String razaoSocial, String nomePropriedade, Double areaPropriedade, Double valorAquisicao) {
        this.nomePf = nomePf;
        this.razaoSocial = razaoSocial;
        this.nomePropriedade = nomePropriedade;
        this.areaPropriedade = areaPropriedade;
        this.valorAquisicao = valorAquisicao;
    }
}
