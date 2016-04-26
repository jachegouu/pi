package com.example.ray.jachegou.HELPER;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ray.jachegou.DAOS.UsuarioDAO;
import com.example.ray.jachegou.LoginActivity;
import com.example.ray.jachegou.MODELS.UsuarioBean;
import com.example.ray.jachegou.R;
import com.example.ray.jachegou.TelaPrincipalControler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ray on 31/03/2016.
 */
public class LoginHelper {
    private Button btnLogin;
    private AutoCompleteTextView email;
    private TextView senha;
    private LoginActivity activity;
    private String caminhoServidor = "http://www.ceramicasantaclara.ind.br/jachegou/webservice/";
    private String loginUrl = caminhoServidor + "login.php";
    private UsuarioDAO dao;
    private ProgressDialog progress;
    private RequestQueue requestQueue;
    public LoginHelper(LoginActivity activity){
        email=(AutoCompleteTextView)activity.findViewById(R.id.email);
        senha=(TextView)activity.findViewById(R.id.EditSenha);
        btnLogin=(Button)activity.findViewById(R.id.logar);
        this.activity=activity;
        dao = new UsuarioDAO(activity);
        requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
    }

    public void autenticarUsuario2() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, loginUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progress.dismiss();
                            UsuarioBean usuario = new UsuarioBean();
                            usuario.setEmail(email.getText().toString());
                            usuario.setSenha(senha.getText().toString());
                            dao.atualizarUltimoUsuario(usuario);
                            email.setEnabled(true);
                            senha.setEnabled(true);

                            JSONObject obj = response.getJSONObject("usuario");
                            usuario.setId(Integer.parseInt(obj.getString("id")));
                            usuario.setNome(obj.getString("nome"));
                            usuario.setTelefone(obj.getString("telefone"));
                            usuario.setBairro(obj.getString("bairro"));
                            usuario.setRua(obj.getString("rua"));
                            usuario.setNumero(Integer.parseInt(obj.getString("numero")));
                            usuario.setCep(obj.getString("cep"));
                            //Drawable drawable =LoadImageFromWebOperations("http://www.ceramicasantaclara.ind.br/jachegou/webservice/" + obj.getString("path_imagen"));
                            usuario.setBitmap(downloadImg("http://www.ceramicasantaclara.ind.br/jachegou/webservice/" + obj.getString("path_imagen")));
                            Log.e("URL:","http://www.ceramicasantaclara.ind.br/jachegou/webservice/" + obj.getString("path_imagen"));
                            Intent intent = new Intent(activity,TelaPrincipalControler.class);
                            ItemStaticos.usuarioLogado=usuario;
                            activity.startActivity(intent);
                            activity.finish();
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        error.printStackTrace();
                        progress.cancel();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("email_usuario", email.getText().toString());
                parameters.put("senha_usuario", senha.getText().toString());
                return parameters;
            }
        };

        requestQueue.add(request);
        progress=ProgressDialog.show(activity, "Aguarde ...", "Verificando credenciais ...", true);
    }


    public boolean existeConexao(){
        ConnectivityManager connectivity = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null){
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();

            if (netInfo == null) {
                return false;
            }
            int netType = netInfo.getType();
            if (netType == ConnectivityManager.TYPE_WIFI || netType == ConnectivityManager.TYPE_MOBILE) {
                return netInfo.isConnected();
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
    private Drawable LoadImageFromWebOperations(String url)
    {
        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            System.out.println("Exc="+e);
            return null;
        }
    }

    public Bitmap downloadImg(String url) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bitmap bmImg = null;
        URL myfileurl =null;
        try{
            myfileurl= new URL(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        try{
            HttpURLConnection conn= (HttpURLConnection)myfileurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            int length = conn.getContentLength();
            if(length>0){
                int[] bitmapData =new int[length];
                byte[] bitmapData2 =new byte[length];
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
            } else{
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        return bmImg;
    }

}
