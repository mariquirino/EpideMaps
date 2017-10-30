package com.example.mariana.projeto.Persistence;

import com.example.mariana.projeto.Model.Usuario;

import java.util.List;


public interface UsuarioDAO {

    public void insertUsuario(Usuario usuario);
    public List<Usuario> recoverUsuario();
    public void deleteUsuario(Usuario usuario);
    public void changeUsuario(Usuario usuario);

}
