package com.example.mariana.projeto.Model;

/**
 * Created by Mariana on 10/09/2017.
 */

public enum Sintoma {
    Febre(1), Cansaço(2),Dor_de_cabeça(3), Dor_no_corpo(4), Manchas(5), Dor_articular(6),Olhos_vermelhos(7),Inchaço(8);

    private int id;

    Sintoma(int id){
        this.id = id;
    }

    int getId(){
        return id;
    }

    Sintoma mapping(int id){
        switch(id){
            case 1: return Febre;
            case 2: return Cansaço;
            case 3: return Dor_de_cabeça;
            case 4: return Dor_no_corpo;
            case 5: return Manchas;
            case 6: return Dor_articular;
            case 7: return Olhos_vermelhos;
            case 8: return Inchaço;
            default: return null;
        }

    }
}
