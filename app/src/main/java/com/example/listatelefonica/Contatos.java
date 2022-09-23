package com.example.listatelefonica;

public class Contatos {
    String telefone;
    Integer id;
    String datanasc;

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatanasc() {
        return datanasc;
    }


    public void setDatanasc(String datanasc) {
        this.datanasc = datanasc;

    }
    @Override
    public String toString() {
        return "Contatos{" +
                "telefone='" + telefone + '\'' +
                ", id=" + id +
                ", datanasc='" + datanasc + '\'' +
                '}';
    }
}
