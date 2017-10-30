package com.example.mariana.projeto.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mariana.projeto.Model.Endereco;
import com.example.mariana.projeto.Model.Usuario;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;



public class UsuarioDaoSQLite implements UsuarioDAO {

    public static final String NOME_TABELA = "Usuario";
    public static final String COLUNA_ID = "ID";
    public static final String COLUNA_NOME = "Nome";
    public static final String COLUNA_IDADE = "Idade";
    public static final String COLUNA_DATA = "Data";
    public static final String COLUNA_LATITUDE = "Latitude";
    public static final String COLUNA_LONGITUDE = "Longitude";
    public static final String COLUNA_DOENCA = "Doenca";


    public static final String SCRIPT_CRIACAO_TABELA_USUARIO = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUNA_NOME + " TEXT," + COLUNA_IDADE + " INTEGER,"
            + COLUNA_DATA + " DATE," + COLUNA_LATITUDE + " DOUBLE," + COLUNA_LONGITUDE + " DOUBLE," + COLUNA_DOENCA + " TEXT" + ")";

    public static final String SCRIPT_DELECAO_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;


    private SQLiteDatabase dataBase = null;


    public UsuarioDaoSQLite(Context context) {
        PersistenceHelper persistenceHelper = PersistenceHelper.getInstance(context);
        dataBase = persistenceHelper.getWritableDatabase();
    }

    public void insertUsuario(Usuario usuario) {
        ContentValues values = gerarContentValuesUsuario(usuario);
        dataBase.insert(NOME_TABELA, null, values);
    }

    public List<Usuario> recoverUsuario() {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Usuario> usuarios = construirUsuarioPorCursor(cursor);

        return usuarios;
    }

    public void deleteUsuario(Usuario usuario) {
        String[] valoresParaSubstituir = {
                String.valueOf(usuario.getID())
        };

        dataBase.delete(NOME_TABELA, COLUNA_ID + " =  ?", valoresParaSubstituir);
    }

    public void changeUsuario(Usuario usuario) {
        ContentValues valores = gerarContentValuesUsuario(usuario);

        String[] valoresParaSubstituir = {
                String.valueOf(usuario.getID())
        };

        dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?", valoresParaSubstituir);
    }

    public void closeConectionUsuario() {
        if (dataBase != null && dataBase.isOpen())
            dataBase.close();
    }


    private List<Usuario> construirUsuarioPorCursor(Cursor cursor) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        if (cursor == null)
            return usuarios;
        try {
            if (cursor.moveToFirst()) {
                do {
                    int indexID = cursor.getColumnIndex(COLUNA_ID);
                    int indexNome = cursor.getColumnIndex(COLUNA_NOME);
                    int indexIdade = cursor.getColumnIndex(COLUNA_IDADE);
                    int indexData = cursor.getColumnIndex(COLUNA_DATA);
                    int indexLatitude = cursor.getColumnIndex(COLUNA_LATITUDE);
                    int indexLongitude = cursor.getColumnIndex(COLUNA_LONGITUDE);
                    int indexDoenca = cursor.getColumnIndex(COLUNA_DOENCA);


                    int id = cursor.getInt(indexID);
                    String nome = cursor.getString(indexNome);
                    int idade = cursor.getInt(indexIdade);
                    Date data =  new Date(cursor.getLong(indexData));
                    double latitude = cursor.getDouble(indexLatitude);
                    double longitude = cursor.getDouble(indexLongitude);
                    String doenca = cursor.getString(indexDoenca);

                    Usuario usuario = new Usuario(id, nome, idade, new Endereco(latitude, longitude));
                    usuario.setData_incersao(data);
                    usuario.setNome_doenca(doenca);
                    usuarios.add(usuario);

                } while (cursor.moveToNext());
            }

        } finally {
            cursor.close();
        }
        return usuarios;
    }

    private ContentValues gerarContentValuesUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_IDADE, usuario.getIdade());
        values.put(COLUNA_DATA, usuario.getData_incersao().getTime());
        values.put(COLUNA_LATITUDE, usuario.getEndereco().getLatitude());
        values.put(COLUNA_LONGITUDE, usuario.getEndereco().getLongitude());
        values.put(COLUNA_DOENCA, usuario.getNome_doenca());
        return values;
    }
}
