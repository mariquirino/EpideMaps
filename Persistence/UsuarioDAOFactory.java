package com.example.mariana.projeto.Persistence;

import android.content.Context;


public class UsuarioDAOFactory {
    public static UsuarioDAO getDao(int tipo,Context context){
        UsuarioDAO dao;
        if(tipo==1){
            return dao=new UsuarioDaoSQLite(context);
        }else{
            return null;//outros bancos
        }
    }
}
