package com.example.ray.jachegou.HELPER;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ray.jachegou.CadastroUsuarioActivity;
import com.example.ray.jachegou.MODELS.UsuarioBean;
import com.example.ray.jachegou.R;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ray on 24/03/2016.
 */

public class FormularioUsuarioHelper {
    private RequestQueue requestQueue;
    private EditText nomeUsuario;
    private EditText telefoneUsuario;
    private EditText bairroEndereco;
    private EditText ruaEndereco;
    private EditText numeroEndereco;
    private EditText cepEndereco;
    private EditText emailUsuario;
    private EditText senhaUsuario;
    private ImageView imagem;
    private String pathImagemAntiga="null";
    String caminhoPath="http://www.ceramicasantaclara.ind.br/jachegou/webservice/";
    String insertUrl = caminhoPath + "salvarUsuario.php";
    private ProgressDialog progress;
    CadastroUsuarioActivity activity;
    private Bitmap bitmap;

    public FormularioUsuarioHelper(CadastroUsuarioActivity activity){
        nomeUsuario=(EditText)activity.findViewById(R.id.nomeCliente);
        telefoneUsuario=(EditText)activity.findViewById(R.id.telefoneCliente);
        bairroEndereco=(EditText)activity.findViewById(R.id.bairroCliente);
        ruaEndereco=(EditText)activity.findViewById(R.id.ruaEndereco);
        numeroEndereco=(EditText)activity.findViewById(R.id.numeroCliente);
        cepEndereco=(EditText)activity.findViewById(R.id.cep);
        emailUsuario=(EditText)activity.findViewById(R.id.emailCliente);
        senhaUsuario=(EditText)activity.findViewById(R.id.senhaCliente);
        imagem=(ImageView)activity.findViewById(R.id.cadastroFotoUsuario);
        requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        this.activity=activity;
    }
    public void setUsuario(UsuarioBean usuario){
         nomeUsuario.setText(usuario.getNome());
         telefoneUsuario.setText(usuario.getTelefone());
         bairroEndereco.setText(usuario.getBairro());
         ruaEndereco.setText(usuario.getRua());
         numeroEndereco.setText(usuario.getNumero().toString());
         cepEndereco.setText(usuario.getCep());
         emailUsuario.setText(usuario.getEmail());

         senhaUsuario.setText("**123456**");
         pathImagemAntiga=usuario.getPathImagemAntiga();
         /*if (usuario.getPathImagem()!=null) {
            imagem.setImageBitmap(BitmapFactory.decodeFile(usuario.getPathImagem()));
         }else if(usuario.getImagem()!=null){
             imagem.setImageDrawable(usuario.getImagem());
         }*/
        imagem.setImageDrawable(usuario.getImagem());
    }
    public UsuarioBean getUsuario(){
        UsuarioBean usuario = new UsuarioBean();
        usuario.setNome(nomeUsuario.getText().toString());
        usuario.setTelefone(telefoneUsuario.getText().toString());
        usuario.setBairro(bairroEndereco.getText().toString());
        usuario.setRua(ruaEndereco.getText().toString());
        usuario.setCep(cepEndereco.getText().toString());
        usuario.setNumero(Integer.parseInt(numeroEndereco.getText().toString()));
        usuario.setEmail(emailUsuario.getText().toString());
        usuario.setSenha(senhaUsuario.getText().toString());
        usuario.setBitmap(imagem.getDrawingCache());
        return  usuario;
    }

    public void salvar(){
        if(validaCampos()==true) {
            StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progress.cancel();
                    Log.i("URL",response);
                    Toast.makeText(activity, "Cadastrado com sucesso !", Toast.LENGTH_LONG);
                    activity.finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(activity, "Erro no servidor !", Toast.LENGTH_LONG);
                    activity.finish();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<String, String>();
                    if(ItemStaticos.usuarioLogado==null){
                        parameters.put("id", "0");
                    }else{
                        parameters.put("id", ItemStaticos.usuarioLogado.getId().toString());
                    }

                    parameters.put("nome", nomeUsuario.getText().toString());
                    parameters.put("telefone", telefoneUsuario.getText().toString());
                    parameters.put("rua", ruaEndereco.getText().toString());
                    parameters.put("bairro", bairroEndereco.getText().toString());
                    parameters.put("numero", numeroEndereco.getText().toString());
                    parameters.put("cep", cepEndereco.getText().toString());
                    parameters.put("senha", senhaUsuario.getText().toString());
                    parameters.put("email", emailUsuario.getText().toString());
                    parameters.put("imagem", BitMapToString(getBitmap()));
                    parameters.put("path_imagem",pathImagemAntiga);
                    return parameters;
                }
            };
            requestQueue.add(request);
            progress = ProgressDialog.show(activity, "Aguarde ...", "Salvando Informações ...", true);
        }
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean validaCampos(){
        nomeUsuario=(EditText)activity.findViewById(R.id.nomeCliente);
        telefoneUsuario=(EditText)activity.findViewById(R.id.telefoneCliente);
        bairroEndereco=(EditText)activity.findViewById(R.id.bairroCliente);
        ruaEndereco=(EditText)activity.findViewById(R.id.ruaEndereco);
        numeroEndereco=(EditText)activity.findViewById(R.id.numeroCliente);
        cepEndereco=(EditText)activity.findViewById(R.id.cep);
        emailUsuario=(EditText)activity.findViewById(R.id.emailCliente);
        senhaUsuario=(EditText)activity.findViewById(R.id.senhaCliente);

        if(nomeUsuario.getText().toString()==null || nomeUsuario.getText().toString().equals("")){
            visualizaMensagem("Campo Nome Invalido, verifique !");
            return false;
        }else if(telefoneUsuario.getText().toString()==null || telefoneUsuario.getText().toString().equals("")) {
            visualizaMensagem("Campo telefone Invalido, verifique !");
            return false;
        }else if(bairroEndereco.getText().toString()==null || bairroEndereco.getText().toString().equals("")) {
            visualizaMensagem("Campo bairro Invalido, verifique !");
            return false;
        }else if(ruaEndereco.getText().toString()==null || ruaEndereco.getText().toString().equals("")) {
            visualizaMensagem("Campo Rua Invalido, verifique !");
            return false;
        }else if(numeroEndereco.getText().toString()==null || numeroEndereco.getText().toString().equals("")) {
            visualizaMensagem("Campo Endereço Invalido, verifique !");
            return false;
        }else if(cepEndereco.getText().toString()==null || cepEndereco.getText().toString().equals("")) {
            visualizaMensagem("Campo CEP Invalido, verifique !");
            return false;
        }else if(emailUsuario.getText().toString()==null || emailUsuario.getText().toString().equals("")) {
            visualizaMensagem("Campo e-mail Invalido, verifique !");
            return false;
        }else if(senhaUsuario.getText().toString()==null || senhaUsuario.getText().toString().equals("")) {
            visualizaMensagem("Campo senha Invalido, verifique !");
            return false;
        }else if(bitmap==null) {
            visualizaMensagem("Por Favor coloque uma foto !");
            return false;
        }else {
            return true;
        }
    }


    public void visualizaMensagem(String mensagem){
        Toast.makeText(activity,mensagem,Toast.LENGTH_SHORT).show();
    }
}
