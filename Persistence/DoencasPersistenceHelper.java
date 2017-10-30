package com.example.mariana.projeto.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DoencasPersistenceHelper extends SQLiteOpenHelper {
    public static final String NOME_BANCO =  "DoencasBanco";
    public static final int VERSAO =  1;

    private static DoencasPersistenceHelper instance;

    private DoencasPersistenceHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    public static synchronized DoencasPersistenceHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DoencasPersistenceHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DoencaDaoSQLite.SCRIPT_CRIACAO_TABELA_DOENCA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DoencaDaoSQLite.SCRIPT_DELECAO_TABELA);
        onCreate(db);
    }
}
