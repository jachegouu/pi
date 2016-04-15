package com.example.ray.jachegou.SERVICE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.ray.jachegou.TelaPrincipal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ray-PC on 15/04/2016.
 */
public class WebServiceLogin {
        private Button buttonGet;
        private Button buttonParse;
        private Activity activity;
        private String jsonString;

        private static  String MY_JSON = "MY_JSON";
        private static  String url_Servidor = "http://www.ceramicasantaclara.ind.br/jachegou/webservice/login.php";

        public WebServiceLogin(String email, String senha,Activity activity){
           url_Servidor=url_Servidor+"?email_usuario="+email+"&senha_usuario="+senha;
            this.activity=activity;
        }

        public void logarUsuario() {
            class GetJSON extends AsyncTask<String, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(activity, "Verificando credenciais ...", null);
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
                    loading.dismiss();
                    setJsonString(s);
                    Log.i("JSON",s);
                    if(s!=null) {
                        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                        abirTelaPrincipal();
                    }else{
                        Toast.makeText(activity, "Error autentar logar", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            GetJSON gj = new GetJSON();
            gj.execute(url_Servidor);
        }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public void abirTelaPrincipal(){
        Intent intent= new Intent(activity, TelaPrincipal.class);
        activity.startActivity(intent);
    }
}

