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
public class WebServiceListaProduto {
        private Activity activity;
        private String jsonString;
        private ListView listView;
        private AdapterListView adapterListView;
        private ArrayList<ProdutoBean> itens;
        private List<ProdutoBean> listaProdutos;
        private boolean queringIsRuning=false;

        private static  String MY_JSON = "MY_JSON";
        private static  String url_Servidor = "http://ceramicasantaclara.ind.br/jachegou/webservice/listarProduto.php";

        public WebServiceListaProduto(Activity activity){
            url_Servidor=url_Servidor;
            this.activity=activity;
            listView=(ListView)activity.findViewById(R.id.listaProdutosListView);
        }

        public void listaProdutos() {
            class GetJSON extends AsyncTask<String, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(activity, "Consultando produtos ...", null);
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
                        //Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                        listaProdutos=getListaProdutos(s);
                        createListView();
                        loading.dismiss();
                        setQueringIsRuning(false);
                    }else{
                        Toast.makeText(activity, "Error autentar logar", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            GetJSON gj = new GetJSON();
            gj.execute(url_Servidor);
            setQueringIsRuning(true);
        }
    public void carregarMaisProdutos() {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(activity, "Carregando mais produtos ...", null);
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
                    //Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                    List<ProdutoBean> lista=getListaProdutos(s);
                    adicionar(lista);
                    loading.dismiss();
                    setQueringIsRuning(false);
                }else{
                    Toast.makeText(activity, "Error ao tentar adicionar produtos", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute(url_Servidor);
        setQueringIsRuning(true);
    }

    private List<ProdutoBean> getListaProdutos(String jsonString) {
        List<ProdutoBean> produtos = new ArrayList<ProdutoBean>();
        try {
            JSONArray pessoasJson = new JSONArray(jsonString);
            JSONObject produtoJson;

            for (int i = 0; i < pessoasJson.length(); i++) {
                produtoJson = new JSONObject(pessoasJson.getString(i));
                ProdutoBean produtoBean=new ProdutoBean();
                produtoBean.setId(produtoJson.getInt("id"));
                produtoBean.setDescricao(produtoJson.getString("descricao"));
                produtoBean.setValor(produtoJson.getDouble("valor"));
                produtoBean.setImagem(carregarImagemProduto("http://ceramicasantaclara.ind.br/jachegou/site/" + produtoJson.getString("caminho_imagen")));
                Log.i("URL_IMAGEM:","http://ceramicasantaclara.ind.br/jachegou/site/" + produtoJson.getString("caminho_imagen"));
                produtos.add(produtoBean);
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return produtos;
    }

    private void createListView() {
        setAdapterListView(new AdapterListView(activity.getApplicationContext(), listaProdutos));
        listView.setAdapter(getAdapterListView());
        listView.setCacheColorHint(Color.TRANSPARENT);
    }
    private void adicionar(List<ProdutoBean> lista) {
        for(ProdutoBean p:lista){
            listaProdutos.add(p);
        }
        adapterListView.notifyDataSetChanged();
    }
    public Drawable carregarImagemProduto(String url) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL myfileurl =null;
        Drawable imagem=null;
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

    public AdapterListView getAdapterListView() {
        return adapterListView;
    }

    public void setAdapterListView(AdapterListView adapterListView) {
        this.adapterListView = adapterListView;
    }

    public boolean isQueringIsRuning() {
        return queringIsRuning;
    }

    public void setQueringIsRuning(boolean queringIsRuning) {
        this.queringIsRuning = queringIsRuning;
    }
}
