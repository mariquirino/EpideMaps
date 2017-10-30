package com.example.mariana.projeto.Model;

import java.util.ArrayList;

/**
 * Created by Mariana on 10/09/2017.
 */

public class Doenca {
    private String sintomas;
    private String nome;
    private int id, gravidade;


    public Doenca(String nome, int id, int gravidade, String sintomas){
        this.nome = nome;
        this.id=id;
        this.gravidade=gravidade;
        this.sintomas=sintomas;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public int getGravidade() {
        return gravidade;
    }

    public String getSintomas() {
        return sintomas;
    }
}
