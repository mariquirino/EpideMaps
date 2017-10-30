package com.example.mariana.projeto.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mariana on 10/09/2017.
 */

public class Usuario{
    private int id;
    private int idade;

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    private int prioridade;
    private Date data_incersao;
    private String nome;
    private Endereco endereco;
    private String nome_doenca;

    public Usuario(int id, String nome, int idade, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
    }

    public String getNome_doenca() {
        return nome_doenca;
    }

    public void setNome_doenca(String nome_doenca) {
        this.nome_doenca = nome_doenca;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public int getIdade() {
        return idade;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public Date getData_incersao() {
        return data_incersao;
    }

    public void setData_incersao(Date data_incersao) {
        this.data_incersao = data_incersao;
    }

    public String getNome() {
        return nome;
    }

    public int getID(){
        return id;
    }
}
