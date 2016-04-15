package com.example.ray.jachegou;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ray.jachegou.DAOS.Teste;
import com.example.ray.jachegou.DAOS.UsuarioDAO;
import com.example.ray.jachegou.HELPER.LoginHelper;
import com.example.ray.jachegou.MODELS.UsuarioBean;
import com.example.ray.jachegou.SERVICE.WebService;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    private EditText senhaEdit;
    private AutoCompleteTextView emailEdit;
    private ProgressBar progressBar;
    private TextView textLoga;
    private Button botaoCadastro;
    private Button botaoLogar;
    private Button redinirSenha;
    private UsuarioBean usuarioLogar, ultimoUsuarioLogado;
    private RequestQueue requestQueue;
    private UsuarioDAO usuarioDao;
    private ProgressDialog progress;
    String caminhoServidor = "http://www.ceramicasantaclara.ind.br/jachegou/webservice/";
    String loginUrl = caminhoServidor + "login.php";
    private LoginHelper loginHelper;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        botaoCadastro = (Button) findViewById(R.id.cadastro);
        redinirSenha = (Button) findViewById(R.id.btNaoLembraSenha);
        usuarioDao = new UsuarioDAO(this);
        //ultimoUsuarioLogado = usuarioDao.pegarUltimoUsuarioLogado();
        botaoLogar=(Button)findViewById(R.id.logar);
        loginHelper=new LoginHelper(this);
        emailEdit=(AutoCompleteTextView)findViewById(R.id.email);
        senhaEdit=(EditText)findViewById(R.id.EditSenha);
        
        if (ultimoUsuarioLogado != null) {
            emailEdit.setText(ultimoUsuarioLogado.getEmail());
            senhaEdit.setText(ultimoUsuarioLogado.getSenha());
        }


        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });

        redinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Teste teste= new Teste();
                String retorno=teste.teste(requestQueue, emailEdit.getText().toString(), senhaEdit.getText().toString());
                Toast.makeText(LoginActivity.this,retorno,Toast.LENGTH_LONG);*/
                WebService teste= new WebService(LoginActivity.this);
                teste.getJSON("http://widehaus.com/android/android.json");
                //Toast.makeText(LoginActivity.this,teste.getJsonString(),Toast.LENGTH_LONG).show();
                //Log.e("TESTE JSON",teste.getJsonString());
            }
        });
        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailEdit.getText().toString().equals("") && emailEdit.getText().toString()!=null
                        && !senhaEdit.getText().toString().equals("") && senhaEdit.getText().toString()!=null) {
                    loginHelper.autenticarUsuario2();
                }else{
                    Toast.makeText(LoginActivity.this,"Digite usuario e senha !",Toast.LENGTH_SHORT).show();
                }
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



}
