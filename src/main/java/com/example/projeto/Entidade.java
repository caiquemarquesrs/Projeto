package com.example.projeto;

public class Entidade {

    private int id;
    private String name;
    private String cnpj;
    private String type;

    public Entidade() {
    }

    public Entidade(String name, String cnpj, String type) {
        this.name = name;
        this.cnpj = cnpj;
        this.type = type;
    }

    public Entidade(int id, String name, String cnpj, String type) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }
}