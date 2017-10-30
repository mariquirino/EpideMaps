package com.example.mariana.projeto.Persistence;

import com.example.mariana.projeto.Model.Doenca;

import java.util.List;

public interface DoencaDAO {
    public void insertDoenca(Doenca doenca);
    public List<Doenca> recoverDoenca();
    public void deleteDoenca(Doenca doenca);
    public void changeDoenca(Doenca doenca);
    public void closeConectionDoenca();
}
