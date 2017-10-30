package com.example.mariana.projeto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mariana.projeto.Model.Usuario;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Abrir a barra de navegação
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Key maps
//        AIzaSyCs_9Thc9TmUvtPfJ9eU3jO79PkLu0oCkE
    }

    @Override
    public void onBackPressed() {
        //Quando clico no botao de voltar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //fechar a barra do lado quando eu apertar o botao voltar do celular
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Ou vai voltar uma acivity ou fechar o app
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_formulario) {
            //Abrir a tela de Formulario
            Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_epidemias){
            //Abrir a tela de Epidemias
            Intent intent = new Intent(MainActivity.this, EpidemiasActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_about){
            //Abrir a tela de about
            Intent intent =  new Intent (MainActivity.this,AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}