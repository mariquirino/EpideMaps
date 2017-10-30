package com.example.mariana.projeto;

import android.os.Bundle;
import android.util.Log;

import com.example.mariana.projeto.Model.Endereco;
import com.example.mariana.projeto.Model.Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    public void setLocation(LatLng location){
        this.location = location;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Esse metodo vai ser executado quando o mapa tiver pronto
        mMap = googleMap;
        //Adicionando o botao de zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //Botando o zoom
        mMap.setMinZoomPreference(15);

        //Pegando ususarios do banco

        ControllerUser controllerUser = new ControllerUser(getContext(),1);//1 é o tipo do banco(apenas uma flag)


        List<Usuario> usuarios;
        usuarios = controllerUser.getUsuario();

        //Adicionando tds os usuarios no mapa
        Date atual=new Date();
        GregorianCalendar gc = new GregorianCalendar();
        atual = gc.getTime();
        Calendar futuro = Calendar.getInstance();
        for (Usuario usuario: usuarios){

            futuro.setTime(usuario.getData_incersao()); //pega a data do usuario
            futuro.add(Calendar.YEAR,1); //aumenta a data em um ano


            //igual retorna 0
            //se a data do  parametro for maior, retorna -1
            //se for menor retorna maior que 1
            if(atual.compareTo(futuro.getTime()) == -1){  // agora ta certo
                LatLng latLng = new LatLng(usuario.getEndereco().getLatitude(), usuario.getEndereco().getLongitude());
                int prioridade=usuario.getPrioridade();
                if(prioridade<3){
                    mMap.addMarker(new MarkerOptions().position(latLng).title(usuario.getNome_doenca()).snippet(usuario.getNome()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                }else if(prioridade<7){
                    mMap.addMarker(new MarkerOptions().position(latLng).title(usuario.getNome_doenca()).snippet(usuario.getNome()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }else {
                    mMap.addMarker(new MarkerOptions().position(latLng).title(usuario.getNome_doenca()).snippet(usuario.getNome()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
            }
        }
        Log.i("Foi", "FOI2");

        //Aqui move a tela para o pin
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
