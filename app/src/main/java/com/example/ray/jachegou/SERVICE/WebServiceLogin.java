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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ray.jachegou.DAOS.UsuarioDAO;
import com.example.ray.jachegou.HELPER.ItemStaticos;
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
public class WebServiceLogin {
        private Activity activity;
        private String jsonString;
        private UsuarioBean bean=new UsuarioBean();
        private static  String url_Servidor = "http://www.ceramicasantaclara.ind.br/jachegou/webservice/login.php";
        private ProgressBar loading;
        private Button botaoAutenticar,botaoRedefinirSenha,botaoCadastra;
        private CheckBox verSenha;
        private AutoCompleteTextView usuario;
        private EditText senha;

        public WebServiceLogin(String email, String senha,Activity activity,ProgressBar spiner){
           url_Servidor=url_Servidor+"?email_usuario="+email+"&senha_usuario="+senha;
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
                //ProgressDialog loading = ProgressDialog.show(activity, "Verificando credenciais ...", null);
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
                    setJsonString(s);
                    Log.i("JSON",s);
                    if(s!=null && !s.equals("null") && !s.equals("") && s.length()>0) {
                        UsuarioBean usuario=getUsuarioJson(s);
                        //UsuarioDAO dao = new UsuarioDAO(activity);
                        //dao.alterar(usuario);
                        //ItemStaticos.usuarioLogado=usuario;
                        //loading.dismiss();
                        //visualizarBotoes();
                        DonwnloadImagenAsync(usuario);

                    }else{
                        Toast.makeText(activity, "Usuario ou Senha Invalídos !", Toast.LENGTH_SHORT).show();
                        //loading.dismiss();
                        visualizarBotoes();
                    }
                }
            }
            ocultarBotoes();
            Log.i("URl",url_Servidor);
            GetJSON gj = new GetJSON();
            gj.execute(url_Servidor);
        }

    public void redefinirSenha(String email) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading = ProgressDialog.show(activity, "Verificando credenciais ...", null);
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
                if(s.equalsIgnoreCase("ok")) {
                    Toast.makeText(activity, "Verifique seu e-mail !", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    abriTelaPrincipal();
                }else{
                    Toast.makeText(activity, "Erro interno !", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute(""+email);
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public void abriTelaPrincipal(){
        Intent intent= new Intent(activity, TelaPrincipalControler.class);
        activity.startActivity(intent);
    }

    private UsuarioBean getUsuarioJson(String jsonString) {

        try {
            JSONArray pessoasJson = new JSONArray(jsonString);


            for (int i = 0; i < pessoasJson.length(); i++) {
                JSONObject usuarioJson =  new JSONObject(pessoasJson.getString(i));
                bean.setId(usuarioJson.getInt("id"));
                bean.setNome(usuarioJson.getString("nome"));
                bean.setTelefone(usuarioJson.getString("telefone"));
                bean.setBairro(usuarioJson.getString("bairro"));
                bean.setRua(usuarioJson.getString("rua"));
                bean.setNumero(Integer.parseInt(usuarioJson.getString("numero")));
                bean.setCep(usuarioJson.getString("cep"));
                bean.setEmail(usuarioJson.getString("email"));
                bean.setSenha(usuarioJson.getString("senha"));
                bean.setPathImagem("http://www.ceramicasantaclara.ind.br/jachegou/webservice/" + usuarioJson.getString("path_imagen"));
                //donwloadImagem(bean);
                //bean.setImagem(carregarImagem(bean.getPathImagem()));
                bean.setPathImagemAntiga(usuarioJson.getString("path_imagen"));
                bean.setTipoUsuario(usuarioJson.getInt("tipo"));
                Log.i("USUARIO", usuarioJson.getString("nome"));
                break;
            }
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
            bean.setBitmap(bmImg);
            imagem= new BitmapDrawable(bmImg);
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
    public void DonwnloadImagenAsync(final UsuarioBean usuario) {
        class Down extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... urls) {
                return download_Image(urls[0]);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                Drawable img= new BitmapDrawable(result);
                usuario.setImagem(img);
                ItemStaticos.usuarioLogado=usuario;
                abriTelaPrincipal();
            }


            private Bitmap download_Image(String url) {
                //---------------------------------------------------
                Bitmap bm = null;
                try {
                    URL aURL = new URL(url);
                    URLConnection conn = aURL.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    Log.e("Hub", "Error getting the image from server : " + e.getMessage().toString());
                }
                return bm;
                //---------------------------------------------------
            }


        }
        Down d = new Down();
        d.execute(usuario.getPathImagem());
    }
}

