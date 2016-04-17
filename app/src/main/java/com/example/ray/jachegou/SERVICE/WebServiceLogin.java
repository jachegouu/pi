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
import android.widget.Button;
import android.widget.Toast;

import com.example.ray.jachegou.HELPER.UsuarioHelper;
import com.example.ray.jachegou.MODELS.ProdutoBean;
import com.example.ray.jachegou.MODELS.UsuarioBean;
import com.example.ray.jachegou.TelaPrincipal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
                        //Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                        UsuarioBean usuario=getUsuarioJson(s);
                        UsuarioHelper.usuarioLogado=usuario;
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

    private UsuarioBean getUsuarioJson(String jsonString) {
        UsuarioBean bean=new UsuarioBean();
        try {

            JSONObject usuarioJson= new JSONObject(jsonString);
            bean.setId(Integer.parseInt(usuarioJson.getString("id")));
            bean.setNome(usuarioJson.getString("nome"));
            bean.setTelefone(usuarioJson.getString("telefone"));
            bean.setBairro(usuarioJson.getString("bairro"));
            bean.setRua(usuarioJson.getString("rua"));
            bean.setNumero(Integer.parseInt(usuarioJson.getString("numero")));
            bean.setCep(usuarioJson.getString("cep"));
            bean.setEmail(usuarioJson.getString("email"));
            bean.setSenha(usuarioJson.getString("senha"));

            bean.setImagem(carregarImagem("http://www.ceramicasantaclara.ind.br/jachegou/webservice/imagem_cliente/"+usuarioJson.getString("path_imagen")));

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return bean;
    }

    public Drawable carregarImagem(String url) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL myfileurl =null;
        Drawable imagem=null;
        try{
            myfileurl= new URL(url);

        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        try
        {
            HttpURLConnection conn= (HttpURLConnection)myfileurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            int length = conn.getContentLength();
            int[] bitmapData =new int[length];
            byte[] bitmapData2 =new byte[length];
            InputStream is = conn.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();

            Bitmap bmImg = BitmapFactory.decodeStream(is, null, options);
            imagem= new BitmapDrawable(bmImg);;
            return imagem;
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
//          Toast.makeText(PhotoRating.this, "Connection Problem. Try Again.", Toast.LENGTH_SHORT).show();
            return imagem;
        }
    }
}

