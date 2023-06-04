package dev.hayann.model;

public class Proprietario implements Entity {

    public static final String TABLE_NAME = "proprietario";

    public static final String COLLUMN_ID_NAME = "id_proprietario";

    public static final String COLLUMN_NAME_NAME = "nam_proprietario";

    public static final String COLLUMN_TELEFONE1_NAME = "telefone_prorietario1";

    public static final String COLLUMN_TELEFONE2_NAME = "telefone_prorietario2";

    public static final String COLLUMN_TELEFONE3_NAME = "telefone_prorietario3";

    private Integer id;

    private String name;

    private Long telefone1;

    private Long telefone2;

    private Long telefone3;

    public Proprietario(Integer id, String name, Long telefone1, Long telefone2, Long telefone3) {
        this.id = id;
        this.name = name;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.telefone3 = telefone3;
    }

    public Proprietario(String name, Long telefone1, Long telefone2, Long telefone3) {
        this.name = name;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.telefone3 = telefone3;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(Long telefone1) {
        this.telefone1 = telefone1;
    }

    public Long getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(Long telefone2) {
        this.telefone2 = telefone2;
    }

    public Long getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(Long telefone3) {
        this.telefone3 = telefone3;
    }

    @Override
    public String toString() {
        return name;
    }
}
