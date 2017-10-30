package com.example.mariana.projeto;

import android.content.Context;

import com.example.mariana.projeto.Model.Usuario;
import com.example.mariana.projeto.Persistence.UsuarioDAO;
import com.example.mariana.projeto.Persistence.UsuarioDAOFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class ControllerUser{
    private UsuarioDAO usuarioDAO;
    private UsuarioDAOFactory usuarioDAOFactory;
    private Context context;

    public ControllerUser(Context context, int tipo) {
        this.context = context;
        usuarioDAOFactory = new UsuarioDAOFactory();
        usuarioDAO=  usuarioDAOFactory.getDao(tipo, context);
        delete();
    }

    public void inserir(Usuario usuario){
        usuarioDAO.insertUsuario(usuario);
    }

    public List<Usuario> getUsuario(){
        return usuarioDAO.recoverUsuario();
    }

    public void delete(){
        List<Usuario> usuarios = usuarioDAO.recoverUsuario();
        Date atual=new Date();
        GregorianCalendar gc = new GregorianCalendar();
        atual = gc.getTime();
        Calendar futuro = Calendar.getInstance();
        for (Usuario usuario: usuarios){
            futuro.setTime(usuario.getData_incersao()); //pega a data do usuario
            futuro.add(Calendar.YEAR,1); //aumenta a data em um ano
            if(atual.compareTo(futuro.getTime()) == 1){
               usuarioDAO.deleteUsuario(usuario);
            }
        }
    }
}
