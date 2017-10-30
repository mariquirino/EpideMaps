package com.example.mariana.projeto.Persistence;

import android.content.Context;



public class DoencaDAOFactory {

    public DoencaDAO getDao(int tipo, Context context){
        DoencaDAO dao;
        if(tipo==1){
            return dao=new DoencaDaoSQLite(context);
        }else{
            return null;
        }
    }
}
