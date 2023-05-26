package dev.hayann.model;

public class Municipio {

    private Integer id;

    private String name;

    private String uf;

    public Municipio(Integer id, String name, String uf) {
        this.id = id;
        this.name = name;
        this.uf = uf;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUf() {
        return uf;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "Municipio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}
