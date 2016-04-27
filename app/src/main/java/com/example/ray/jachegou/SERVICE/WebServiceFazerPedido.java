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
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ray.jachegou.ADPATER.AdapterListViewPedidosAnteriores;
import com.example.ray.jachegou.AdapterListView;
import com.example.ray.jachegou.FinalizarPedido;
import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.MODELS.PedidoBean;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ray-PC on 15/04/2016.
 */
public class WebServiceFazerPedido {
        private Activity activity;
        private String jsonString;
        private ListView listView;
        private AdapterListView adapterListView;
        private ArrayList<ProdutoBean> itens;
        private List<ProdutoBean> listaProdutos;
        private boolean queringIsRuning=false;

        private static  String MY_JSON = "MY_JSON";
        private static  String url_Servidor = "http://www.ceramicasantaclara.ind.br/jachegou/webservice/salvarPedido.php";
        private static  String url_Servidor2 = "http://www.ceramicasantaclara.ind.br/jachegou/webservice/listarPedidos.php";

        public WebServiceFazerPedido(Activity activity){
            this.activity=activity;
        }

        public void adicionarPedido(List<ProdutoBean> produtos) {
            class GetJSON extends AsyncTask<String, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(activity, "Enviando pedido ...", null);
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
                        //listaProdutos=getListaProdutos(s);
                        loading.dismiss();
                        Toast.makeText(activity,"Pedido Realizado com sucesso !",Toast.LENGTH_SHORT).show();
                        setQueringIsRuning(false);
                    }else{
                        Toast.makeText(activity, "Error no Servidor !", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            GetJSON gj = new GetJSON();
            Log.i("URL:",montarUrl(produtos));
            gj.execute(montarUrl(produtos));
            setQueringIsRuning(true);
        }
    public void listarPedidos(final ListView lista) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(activity, "Carregando Informações !", null);
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
                    loading.dismiss();
                    List<PedidoBean> pedidos=getPedido(s);
                    //ArrayAdapter<PedidoBean> adapter = new ArrayAdapter<PedidoBean>(activity, android.R.layout.simple_list_item_1, pedidos);
                    AdapterListViewPedidosAnteriores adapter= new AdapterListViewPedidosAnteriores(activity,pedidos);
                    lista.setAdapter(adapter);
                    setQueringIsRuning(false);
                }else{
                    Toast.makeText(activity, "Error no Servidor !", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute(url_Servidor2 + "?id="+ItemStaticos.usuarioLogado.getId());
        setQueringIsRuning(true);
    }

   private List<PedidoBean> getPedido(String jsonString) {
        List<PedidoBean> pedidos = new ArrayList<PedidoBean>();
        try {
            JSONArray pessoasJson = new JSONArray(jsonString);
            JSONObject produtoJson;
            int ultimoid=0;
            PedidoBean pedido=null;
            for (int i = 0; i < pessoasJson.length(); i++) {
                produtoJson = new JSONObject(pessoasJson.getString(i));

                if(ultimoid!=produtoJson.getInt("id")){
                    pedido=new PedidoBean();
                    pedido.setId(produtoJson.getInt("id"));
                    SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date=new Date();
                    try {
                        date=ft.parse(produtoJson.getString("data_time"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    pedido.setDateTime(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(date));
                }
                ProdutoBean item= new ProdutoBean();
                item.setId(produtoJson.getInt("id_prd"));
                item.setDescricao(produtoJson.getString("descricao"));
                item.setValor(produtoJson.getDouble("valor_pago"));
                item.setImagem(carregarImagemProduto("http://ceramicasantaclara.ind.br/jachegou/site/" + produtoJson.getString("caminho_imagen")));
                pedido.getLista().add(item);
                pedidos.add(pedido);
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return pedidos;
    }

    public String montarUrl(List<ProdutoBean> lista){

        String codigosProdutos="";
        String valoresProdutos="";
        String quantidadesProdutos="";
        for(ProdutoBean produto:lista){
                codigosProdutos=codigosProdutos+produto.getId()+",";
                valoresProdutos=valoresProdutos+produto.getValor()+",";
                quantidadesProdutos=quantidadesProdutos+produto.getQuantidadePedido()+",";
        }
        url_Servidor=url_Servidor+"?ids_produtos="+codigosProdutos+
                "&codigo_usuario="+ItemStaticos.usuarioLogado.getId()+
                "&qts_produtos="+quantidadesProdutos+"&valores_produtos="+valoresProdutos;
        return  url_Servidor;
    }

    public boolean isQueringIsRuning() {
        return queringIsRuning;
    }

    public void setQueringIsRuning(boolean queringIsRuning) {
        this.queringIsRuning = queringIsRuning;
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

}
