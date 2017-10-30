package com.example.mariana.projeto;

import android.content.Context;

import com.example.mariana.projeto.Model.Doenca;
import com.example.mariana.projeto.Persistence.DoencaDAO;
import com.example.mariana.projeto.Persistence.DoencaDAOFactory;

import java.util.List;


public class ControllerDoenca {
    private DoencaDAO doencaDao;
    private DoencaDAOFactory doencaDAOFactory;
    private Context context;

    public ControllerDoenca(Context context, int tipo_banco){
        this.context=context;
        doencaDAOFactory = new DoencaDAOFactory();
        doencaDao=doencaDAOFactory.getDao(tipo_banco, context);
    }


    public void inserir(Doenca doenca){doencaDao.insertDoenca(doenca);}

    public List<Doenca> getDoenca(){return doencaDao.recoverDoenca();}



}
