package com.example.listatelefonica;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Contatos {
    private String telefone;
    private Integer id;
    private String datanasc;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
            return nome + " " + telefone + " " + formatter.format(datanasc);
        } catch (Exception e){
            System.out.println(e);
            return nome + " " + telefone + " " + datanasc;
        }
    }
}
