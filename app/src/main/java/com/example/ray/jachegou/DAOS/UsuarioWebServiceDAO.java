package com.example.ray.jachegou.DAOS;

import android.app.Application;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ray.jachegou.CadastroUsuarioActivity;
import com.example.ray.jachegou.LoginActivity;
import com.example.ray.jachegou.MODELS.UsuarioBean;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ray-PC on 24/03/2016.
 */

public class UsuarioWebServiceDAO extends Application{
    private RequestQueue requestQueue;
    private boolean retorno;
    private boolean terminouSicronizar=false;
    //String caminhoPath="http://192.168.1.103/wwww/JaChegouPedido/webservice/";
    String caminhoPath="http://www.ceramicasantaclara.ind.br/jachegou/webservice/";
    String insertUrl = caminhoPath + "salvarUsuario.php";
    String loginUrl = caminhoPath + "login.php";

    public UsuarioWebServiceDAO(RequestQueue request){
        requestQueue=request;
    }

    public Boolean salvar(final UsuarioBean usuarioBean){
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                retorno=true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("nome",usuarioBean.getNome());
                parameters.put("telefone",usuarioBean.getTelefone());
                parameters.put("rua",usuarioBean.getRua());
                parameters.put("bairro",usuarioBean.getBairro());
                parameters.put("numero",usuarioBean.getNumero().toString());
                parameters.put("cep",usuarioBean.getCep());
                parameters.put("senha",usuarioBean.getSenha());
                parameters.put("email",usuarioBean.getEmail());
                parameters.put("imagem", BitMapToString(usuarioBean.getBitmap()));
                return parameters;
            }
        };
        requestQueue.add(request);
        return retorno;
    }




    public void loginUsuario(final UsuarioBean usuarioBean, final LoginActivity login){
        StringRequest request = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(login,response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login,error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();                
                parameters.put("email",usuarioBean.getEmail());
                parameters.put("senha", usuarioBean.getSenha());
                return parameters;
            }
        };
        requestQueue.add(request);
    }


    
    public void logarUsuario(final String email, final String senha){
        System.out.print(email);
        StringRequest request = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //retorno= Boolean.valueOf(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("email",email);
                parameters.put("senha",senha);
                return parameters;
            }
        };
        requestQueue.add(request);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }




}

