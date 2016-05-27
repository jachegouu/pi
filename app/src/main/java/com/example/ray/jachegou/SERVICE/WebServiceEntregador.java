package com.example.ray.jachegou.SERVICE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.ListaPedidoEntregar;
import com.example.ray.jachegou.MODELS.EntregadorBean;
import com.example.ray.jachegou.MODELS.UsuarioBean;
import com.example.ray.jachegou.R;
import com.example.ray.jachegou.TelaPrincipalControler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ray-PC on 15/04/2016.
 */
public class WebServiceEntregador {
        private Activity activity;
        private String jsonString;
        private EntregadorBean bean=new EntregadorBean();
        private static  String url_Servidor = "http://www.ceramicasantaclara.ind.br/jachegou/webservice/loginentregador.php";
        private ProgressBar loading;
        private Button botaoAutenticar,botaoRedefinirSenha,botaoCadastra;
        private CheckBox verSenha;
        private AutoCompleteTextView usuario;
        private EditText senha;

        public WebServiceEntregador(String login, String senha, Activity activity, ProgressBar spiner){
           url_Servidor=url_Servidor+"?usuario="+login.replace("@","")+"&senha="+senha;
            this.activity=activity;
            this.loading=spiner;
            this.botaoAutenticar=(Button)activity.findViewById(R.id.logar);
            this.botaoRedefinirSenha=(Button)activity.findViewById(R.id.btNaoLembraSenha);
            this.botaoCadastra=(Button)activity.findViewById(R.id.cadastro);
            this.verSenha=(CheckBox)activity.findViewById(R.id.chkMostrarSenha);
            this.usuario=(AutoCompleteTextView)activity.findViewById(R.id.email);
            this.senha=(EditText)activity.findViewById(R.id.EditSenha);
        }

        public void logarUsuario() {
            class GetJSON extends AsyncTask<String, Void, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    String uri = params[0];
                    BufferedReader bufferedReader = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();
                        bufferedReader = new BufferedReader((new InputStreamReader(con.getInputStream())));
                        String json;
                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json + "\n");
                        }
                        return sb.toString().trim();
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Log.i("JSON",s);
                    if(s!=null && !s.equals("null") && !s.equals("") && s.length()>0 && !s.equalsIgnoreCase("acesso negado")) {
                        EntregadorBean entregador=getUsuarioJson(s);
                        //UsuarioDAO dao = new UsuarioDAO(activity);
                        //dao.alterar(usuario);
                        ItemStaticos.entregador=entregador;
                        //loading.dismiss();
                        //visualizarBotoes();
                        abriTelaPrincipal();
                        activity.finish();
                    }else{
                        Toast.makeText(activity, "Usuario ou Senha Invalídos !", Toast.LENGTH_SHORT).show();
                        visualizarBotoes();
                    }
                }
            }
            ocultarBotoes();
            Log.i("URl",url_Servidor);
            GetJSON gj = new GetJSON();
            gj.execute(url_Servidor);
        }

    public void abriTelaPrincipal(){
        Intent intent= new Intent(activity, ListaPedidoEntregar.class);
        activity.startActivity(intent);
    }

    private EntregadorBean getUsuarioJson(String jsonString) {

        try {
            JSONArray pessoasJson = new JSONArray(jsonString);


            for (int i = 0; i < pessoasJson.length(); i++) {
                JSONObject usuarioJson =  new JSONObject(pessoasJson.getString(i));
                bean.setId(usuarioJson.getInt("id"));
                bean.setNome(usuarioJson.getString("nome"));
                bean.setId_estabelecimento(Integer.parseInt(usuarioJson.getString("id_estabelecimento")));
                break;
            }
        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return bean;
    }

      public void ocultarBotoes(){
        botaoAutenticar.setVisibility(View.GONE);
        botaoCadastra.setVisibility(View.GONE);
        botaoRedefinirSenha.setVisibility(View.GONE);
        verSenha.setVisibility(View.GONE);
        usuario.setVisibility(View.GONE);
        senha.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }
    public void visualizarBotoes(){
        botaoAutenticar.setVisibility(View.VISIBLE);
        botaoCadastra.setVisibility(View.VISIBLE);
        botaoRedefinirSenha.setVisibility(View.VISIBLE);
        verSenha.setVisibility(View.VISIBLE);
        usuario.setVisibility(View.VISIBLE);
        senha.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }
}

