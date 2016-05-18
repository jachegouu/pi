package com.example.ray.jachegou.DAOS;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ray.jachegou.MODELS.UsuarioBean;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
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
                parameters.put("tipo", "1");
                return parameters;
            }
        };
        requestQueue.add(request);
        return retorno;
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

