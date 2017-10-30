package com.example.mariana.projeto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mariana.projeto.Model.Doenca;
import com.example.mariana.projeto.Model.Endereco;
import com.example.mariana.projeto.Model.Sintoma;
import com.example.mariana.projeto.Model.Usuario;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class FormularioActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Usuario usuario;
    private ArrayList<Sintoma> sintomasUsuario;
    private String nome;
    private int idade;
    private Endereco endereco;
    private Location location;
    private GoogleApiClient mGoogleApiClient;

    private EditText editTextnome;
    private EditText editTextidade;
    private CheckBox febre;
    private CheckBox mancha;
    private CheckBox dorDeCabeca;
    private CheckBox cansaco;
    private CheckBox dorNoCorpo;
    private CheckBox dorArticular;
    private CheckBox olhosVermelhos;
    private CheckBox inchaco;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        callConnection();
    }

    public void sintomas() {
        //Pegando as chechBox
        febre = (CheckBox) findViewById(R.id.checkBoxFebre);
        mancha = (CheckBox) findViewById(R.id.checkBoxManchas);
        dorDeCabeca = (CheckBox) findViewById(R.id.checkBoxDorDeCabeca);
        cansaco = (CheckBox) findViewById(R.id.checkBoxcansaco);
        dorNoCorpo = (CheckBox) findViewById(R.id.checkBoxDorNoCorpo);
        dorArticular = (CheckBox) findViewById(R.id.checkBoxDorArticular);
        olhosVermelhos = (CheckBox) findViewById(R.id.checkBoxOlhosVermelhos);
        inchaco = (CheckBox) findViewById(R.id.checkBoxInchaco);



        sintomasUsuario = new ArrayList<>();

        //Olhando se os CheckBox foram selecionados
        if (febre.isChecked()) {
          sintomasUsuario.add(Sintoma.Febre);
        }
        if (mancha.isChecked()) {
            sintomasUsuario.add(Sintoma.Manchas);
        }
        if (dorDeCabeca.isChecked()) {
            sintomasUsuario.add(Sintoma.Dor_de_cabeça);
        }
        if (cansaco.isChecked()) {
            sintomasUsuario.add(Sintoma.Cansaço);
        }
        if (dorNoCorpo.isChecked()) {
            sintomasUsuario.add(Sintoma.Dor_no_corpo);
        }
        if (dorArticular.isChecked()) {
            sintomasUsuario.add(Sintoma.Dor_articular);
        }
        if (olhosVermelhos.isChecked()) {
            sintomasUsuario.add(Sintoma.Olhos_vermelhos);
        }
        if (inchaco.isChecked()) {
            sintomasUsuario.add(Sintoma.Inchaço);
        }




    }




    private synchronized void callConnection() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        final boolean[] bandeira = new boolean[1];
        editTextnome = (EditText) findViewById(R.id.editTextNome);
        editTextidade = (EditText) findViewById(R.id.editTextIdade);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           Log.i("foi", "foi");
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null){
            //Aqui to criando o botton e quando clicar ele pega o usuario e finaliza essa pag
            Button buttonEnviar = (Button) findViewById(R.id.buttonEnviar);
            buttonEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Pegando o nome, idade e endereço
                    nome = editTextnome.getText().toString();
                    if (!editTextidade.getText().toString().isEmpty()) {
                        idade = Integer.parseInt(editTextidade.getText().toString());
                    }else{
                        idade = 0;
                    }
                    endereco = new Endereco(location.getLatitude(), location.getLongitude());
                    if(nome.isEmpty() || idade== 0){
                        bandeira[0] =false;
                    }else{
                        usuario = new Usuario(0, nome, idade, endereco);
                        //Pegando os sintomas
                        sintomas();
                        Calendar a = Calendar.getInstance();
                        a.setTime(new Date());
                        Date date = new Date();
                        GregorianCalendar gc = new GregorianCalendar();
                        date = gc.getTime();
                        usuario.setData_incersao(date);
                        bandeira[0] = diagnostico();
                    }
                   if(bandeira[0] ==true) {

                       ControllerUser controllerUser = new ControllerUser(FormularioActivity.this,1);//1 é o tipo do banco(apenas uma flag)

                       controllerUser.inserir(usuario);

                       Toast.makeText(FormularioActivity.this, "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();
                       Intent it = new Intent(FormularioActivity.this, EpidemiasActivity.class);
                       startActivity(it);
                       finish();
                   }else{
                       Toast.makeText(FormularioActivity.this, "Usuario invalido, logo, nao cadastrado",Toast.LENGTH_SHORT).show();
                       finish();
                   }
                }
            });
        }
    }
    //temporario
    public ArrayList<Sintoma> ToSintoma(String sintomas){
        String test[] = sintomas.split(",");
        ArrayList<Sintoma> novo= new ArrayList();
        for (int i = 0; i < test.length; i++) {
            novo.add(Sintoma.valueOf(test[i]));
        }
        return novo;
    }

    public boolean diagnostico(){
        ControllerDoenca controllerDoenca = new ControllerDoenca(FormularioActivity.this,1);//1 é o tipo do banco(apenas uma flag)
        int qtd = 0, maior = 0;
        Doenca doencaUsuario = null;

        List<Doenca> lista_doencas = controllerDoenca.getDoenca();

        for(Doenca doenca: lista_doencas){

            ArrayList<Sintoma> sintomas = ToSintoma(doenca.getSintomas());
            qtd = 0;
            for (Sintoma sintoma: sintomas){
                for (Sintoma sintomaUsuario: sintomasUsuario) {
                    if(sintomaUsuario.compareTo(sintoma)==0){
                        qtd++;
                    }
                }
            }
            if (qtd > maior){
                maior = qtd;
                doencaUsuario = doenca;
            }
        }
        sintomasUsuario.clear();
        if(doencaUsuario==null) {
            return false;
        }else {
            usuario.setNome_doenca(doencaUsuario.getNome());
            usuario.setPrioridade(doencaUsuario.getGravidade());
            return true;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOG", "onConnectionSuspended(" + i + ")");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("LOG", "onConnectionFailed("+connectionResult+")");
    }

}
