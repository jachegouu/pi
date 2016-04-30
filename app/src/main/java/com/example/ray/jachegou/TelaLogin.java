package com.example.ray.jachegou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ray.jachegou.DAOS.UsuarioDAO;
import com.example.ray.jachegou.HELPER.LoginHelper;
import com.example.ray.jachegou.MODELS.UsuarioBean;
import com.example.ray.jachegou.SERVICE.WebServiceLogin;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class TelaLogin extends AppCompatActivity {

    private EditText senhaEdit;
    private AutoCompleteTextView emailEdit;
    private ProgressBar progressBar;
    private TextView textLoga;
    private Button botaoCadastro;
    private Button botaoLogar;
    private Button redinirSenha;
    private UsuarioBean usuarioLogar, ultimoUsuarioLogado;
    private UsuarioDAO usuarioDao;
    private LoginHelper loginHelper;
    private CheckBox verSenha;
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
        verSenha=(CheckBox)findViewById(R.id.chkMostrarSenha);
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
        verSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verSenha.isChecked()) {
                    senhaEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    senhaEdit.setInputType(129);
                }
            }
        });

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogin.this, CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });

        redinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TelaLogin.this,"Em desenvolvimento !",Toast.LENGTH_LONG).show();
            }
        });
        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailEdit.getText().toString().equals("") && emailEdit.getText().toString()!=null
                        && !senhaEdit.getText().toString().equals("") && senhaEdit.getText().toString()!=null) {
                    WebServiceLogin login = new WebServiceLogin(emailEdit.getText().toString(),senhaEdit.getText().toString(),TelaLogin.this);
                    login.logarUsuario();
                }else{
                    Toast.makeText(TelaLogin.this,"Digite usuario e senha !",Toast.LENGTH_SHORT).show();
                }
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



}
