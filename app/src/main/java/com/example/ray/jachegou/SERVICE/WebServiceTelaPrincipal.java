package com.example.ray.jachegou.SERVICE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ray.jachegou.AdapterListView;
import com.example.ray.jachegou.MODELS.ProdutoBean;
import com.example.ray.jachegou.R;

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
public class WebServiceTelaPrincipal {
        private Activity activity;
        private static  String URL_CATEGORIAS = "http://ceramicasantaclara.ind.br/jachegou/webservice/listarCategorias.php";
        private static  String URL_ESTABELECIMENTOS = "http://ceramicasantaclara.ind.br/jachegou/webservice/listarEstabelecimentos.php";
        private AutoCompleteTextView categoriasAutoComplete;
        private AutoCompleteTextView estabelecimentosAutoComplete;
        private ProgressDialog loading;
        public WebServiceTelaPrincipal(Activity activity){
            this.activity=activity;
            categoriasAutoComplete=(AutoCompleteTextView)activity.findViewById(R.id.categoriaPesquisar);
            estabelecimentosAutoComplete=(AutoCompleteTextView)activity.findViewById(R.id.estabelecimentosEdit);
        }

        public void listarCategorias() {
            class GetJSON extends AsyncTask<String, Void, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(activity, "Carregando Informações Tela Principal ...", null);
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
                    Log.i("JSON", s);
                    if(s!=null) {
                        ArrayAdapter<String> categorias=new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,getCategoriasJson(s));
                        categoriasAutoComplete.setAdapter(categorias);
                        categoriasAutoComplete.setThreshold(2);
                        //loading.dismiss();
                    }else{
                        Toast.makeText(activity, "Error ao pegar Categorias !", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            GetJSON gj = new GetJSON();
            gj.execute(URL_CATEGORIAS);
        }
    public void listarEstabelecimentos() {
        class GetJSON extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(activity, "Carregando Informações Tela Principal ...", null);
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
                Log.i("JSON", s);
                if(s!=null) {
                    ArrayAdapter<String> ets=new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,getEstabelecimentosJson(s));
                    estabelecimentosAutoComplete.setAdapter(ets);
                    estabelecimentosAutoComplete.setThreshold(2);
                    loading.dismiss();
                }else{
                    Toast.makeText(activity, "Error ao pegar Categorias !", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute(URL_ESTABELECIMENTOS);
    }
    private List<String> getCategoriasJson(String jsonString) {
        List<String> categorias = new ArrayList<String>();
        try {
            JSONArray pessoasJson = new JSONArray(jsonString);
            JSONObject object;

            for (int i = 0; i < pessoasJson.length(); i++) {
                object = new JSONObject(pessoasJson.getString(i));
                categorias.add(object.getString("descricao"));
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return categorias;
    }
    private List<String> getEstabelecimentosJson(String jsonString) {
        List<String> estabelecimentos = new ArrayList<String>();
        try {
            JSONArray pessoasJson = new JSONArray(jsonString);
            JSONObject object;

            for (int i = 0; i < pessoasJson.length(); i++) {
                object = new JSONObject(pessoasJson.getString(i));
                estabelecimentos.add(object.getString("nome"));
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return estabelecimentos;
    }
    public  void CarregarTela(){
        listarCategorias();
        listarEstabelecimentos();
    }
}
