package com.example.mariana.projeto.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mariana.projeto.Model.Doenca;
import com.example.mariana.projeto.Model.Sintoma;

import java.util.ArrayList;
import java.util.List;



public class DoencaDaoSQLite implements DoencaDAO {

    public static final String NOME_BANCO =  "DoencasBanco";

    public static final String NOME_TABELA = "Doencas";
    public static final String COLUNA_ID = "ID";
    public static final String COLUNA_NOME = "Nome";
    public static final String COLUNA_GRAVIDADE = "Gravidade";
    public static final String COLUNA_SINTOMA = "Sintoma";

    public static final String SCRIPT_CRIACAO_TABELA_DOENCA = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUNA_NOME + " TEXT," + COLUNA_GRAVIDADE + " INTEGER,"
            + COLUNA_SINTOMA + " TEXT" + ")";

    public static final String SCRIPT_DELECAO_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;

    private SQLiteDatabase dataBase = null;


    public DoencaDaoSQLite(Context context) {
        DoencasPersistenceHelper persistenceHelper = DoencasPersistenceHelper.getInstance(context);
        dataBase = persistenceHelper.getWritableDatabase();
        ArrayList<Sintoma> sintomas = new ArrayList<>();
        sintomas.add(Sintoma.Dor_articular);
        sintomas.add(Sintoma.Dor_no_corpo);
        sintomas.add(Sintoma.Febre);
        sintomas.add(Sintoma.Olhos_vermelhos);
        sintomas.add(Sintoma.Dor_de_cabeça);
        String sintomaFinal = "";
        for (Sintoma sintoma : sintomas) {
            if (sintomaFinal.trim().isEmpty()) {
                sintomaFinal = sintoma.toString();
            } else {
                sintomaFinal = sintomaFinal + "," + sintoma.toString();
            }
        }
        insertDoenca(new Doenca("Zica", 0, 9, sintomaFinal));
        sintomas.clear();


        sintomas.add(Sintoma.Cansaço);
        sintomas.add(Sintoma.Inchaço);
        sintomas.add(Sintoma.Manchas);
        sintomaFinal = "";
        for (Sintoma sintoma : sintomas) {
            if (sintomaFinal.trim().isEmpty()) {
                sintomaFinal = sintoma.toString();
            } else {
                sintomaFinal = sintomaFinal + "," + sintoma.toString();
            }
        }
        insertDoenca(new Doenca("Virose", 0, 2, sintomaFinal));
        sintomas.clear();

        sintomas.add(Sintoma.Manchas);
        sintomas.add(Sintoma.Febre);
        sintomas.add(Sintoma.Olhos_vermelhos);
        sintomaFinal = "";
        for (Sintoma sintoma : sintomas) {
            if (sintomaFinal.trim().isEmpty()) {
                sintomaFinal = sintoma.toString();
            } else {
                sintomaFinal = sintomaFinal + "," + sintoma.toString();
            }
        }
        insertDoenca(new Doenca("Catapora", 0, 5, sintomaFinal));
        sintomas.clear();

    }

    public void insertDoenca(Doenca doenca) {
        ContentValues values = gerarContentValeuesDoenca(doenca);
        dataBase.insert(NOME_TABELA, null, values);
    }

    public List<Doenca> recoverDoenca() {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Doenca> doencas = construirDoencaPorCursor(cursor);

        return doencas;
    }

    public void deleteDoenca(Doenca doenca) {
        String[] valoresParaSubstituir = {
                String.valueOf(doenca.getId())
        };

        dataBase.delete(NOME_TABELA, COLUNA_ID + " =  ?", valoresParaSubstituir);
    }

    public void changeDoenca(Doenca doenca) {
        ContentValues valores = gerarContentValeuesDoenca(doenca);

        String[] valoresParaSubstituir = {
                String.valueOf(doenca.getId())
        };

        dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?", valoresParaSubstituir);
    }

    public void closeConectionDoenca() {
        if (dataBase != null && dataBase.isOpen()) {
            dataBase.close();
        }
    }


    private List<Doenca> construirDoencaPorCursor(Cursor cursor) {
        List<Doenca> doencas = new ArrayList<Doenca>();
        if (cursor == null)
            return doencas;
        try {
            if (cursor.moveToFirst()) {
                do {
                    int indexID = cursor.getColumnIndex(COLUNA_ID);
                    int indexNome = cursor.getColumnIndex(COLUNA_NOME);
                    int indexGravidade = cursor.getColumnIndex(COLUNA_GRAVIDADE);
                    int indexSintoma = cursor.getColumnIndex(COLUNA_SINTOMA);


                    int id = cursor.getInt(indexID);
                    String nome = cursor.getString(indexNome);
                    int gravidade = cursor.getInt(indexGravidade);
                    String sintomas = cursor.getString(indexSintoma);

                    Doenca doenca = new Doenca(nome, id, gravidade, sintomas);
                    doencas.add(doenca);

                } while (cursor.moveToNext());
            }

        } finally {
            cursor.close();
        }
        return doencas;
    }

    private ContentValues gerarContentValeuesDoenca(Doenca doenca) {
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, doenca.getNome());
        values.put(COLUNA_GRAVIDADE, doenca.getGravidade());
        values.put(COLUNA_SINTOMA, doenca.getSintomas());
        return values;
    }
}
