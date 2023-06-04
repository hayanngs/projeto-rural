package dev.hayann.model;

public class Produto implements Entity {
    public static final String TABLE_NAME = "produto";

    public static final String COLLUMN_ID_NAME = "id_produto";

    public static final String COLLUMN_DESCRIPTION_NAME = "desc_produto";

    private Integer id;

    private String descricao;

    public Produto(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Produto(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
