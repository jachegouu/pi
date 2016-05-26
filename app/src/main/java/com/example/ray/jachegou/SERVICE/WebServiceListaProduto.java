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
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ray.jachegou.AdapterListView;
import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.MODELS.EstabelecimentoBean;
import com.example.ray.jachegou.MODELS.ProdutoBean;
import com.example.ray.jachegou.MODELS.UsuarioBean;
import com.example.ray.jachegou.R;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray-PC on 15/04/2016.
 */
public class WebServiceListaProduto {
        private Activity activity;
        private ListView listView;
        private AdapterListView adapterListView;
        private List<ProdutoBean> listaProdutos;
        private List<ProdutoBean> listaProdutosComImagens= new ArrayList<ProdutoBean>();
        private boolean queringIsRuning=false;
        private boolean acabouProdutos=false;
        private static  String url_Servidor = "http://ceramicasantaclara.ind.br/jachegou/webservice/listarProduto.php";
        private int totalDownloadImg=0;
        private int posicaoAtualDownload=1;
        private ProgressBar loading;


        public WebServiceListaProduto(Activity activity){
            this.activity=activity;
            listView=(ListView)activity.findViewById(R.id.listaProdutosListView);
            this.loading=(ProgressBar)activity.findViewById(R.id.login_progress);
        }

        public void listaProdutos() {
            class GetJSON extends AsyncTask<String, Void, String> {


                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //loading = ProgressDialog.show(activity, "Consultando produtos ...", null);
                    loading.setVisibility(View.VISIBLE);
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
                    if(s!=null) {
                        listaProdutos=getListaProdutos(s);
                        totalDownloadImg=listaProdutos.size();
                        for (ProdutoBean p:listaProdutos){
                            DonwnloadImagenAsyncProdutos(p);
                        }
                    }else{
                        Toast.makeText(activity, "Error autentar logar", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    }
                }
            }

            GetJSON gj = new GetJSON();
            Log.i("URL:", montarUrlPesquisa());
            gj.execute(montarUrlPesquisa());
            setQueringIsRuning(true);
        }
    public void carregarMaisProdutos() {
        class GetJSON extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading.setVisibility(View.VISIBLE);
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
                    List<ProdutoBean> listaNovos=getListaProdutos(s);
                    totalDownloadImg=listaNovos.size();
                    for (ProdutoBean p:listaNovos){
                        DonwnloadImagenAsyncProdutosMais(p);
                    }
                }else{
                    Toast.makeText(activity, "Error ao tentar adicionar produtos", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                }
            }
        }

        GetJSON gj = new GetJSON();
        montarUrlPesquisa();
        gj.execute(montarUrlPesquisa());
        setQueringIsRuning(true);
    }

    private List<ProdutoBean> getListaProdutos(String jsonString) {
        List<ProdutoBean> produtos = new ArrayList<ProdutoBean>();
        if(jsonString.length()>0) {
            try {
                JSONArray pessoasJson = new JSONArray(jsonString.trim());
                JSONObject produtoJson;

                for (int i = 0; i < pessoasJson.length(); i++) {
                    produtoJson = new JSONObject(pessoasJson.getString(i));
                    ProdutoBean produtoBean = new ProdutoBean();
                    produtoBean.setId(produtoJson.getInt("id"));
                    produtoBean.setDescricao(produtoJson.getString("descricao"));
                    produtoBean.setValor(produtoJson.getDouble("valor"));
                    produtoBean.setIngredientes(produtoJson.getString("ingredientes"));
                    produtoBean.setEstabelecimento(ItemStaticos.filtro.getEstabelecimento());
                    produtoBean.setPathImagem("http://ceramicasantaclara.ind.br/jachegou/site/" + produtoJson.getString("caminho_imagen"));
                    Log.i("URL_IMAGEM:", "http://ceramicasantaclara.ind.br/jachegou/site/" + produtoJson.getString("caminho_imagen"));
                    produtos.add(produtoBean);
                }

            } catch (JSONException e) {
                Log.e("Erro", "Erro no parsing do JSON", e);
            }
        }else{
            setAcabouProdutos(true);
        }
        return produtos;
    }

    private void createListView() {
        setAdapterListView(new AdapterListView(activity.getApplicationContext(), listaProdutos,0));
        listView.setAdapter(getAdapterListView());
        listView.setCacheColorHint(Color.TRANSPARENT);
    }
    private void adicionar(List<ProdutoBean> lista) {
        for(ProdutoBean p:lista){
            listaProdutos.add(p);
        }
        adapterListView.notifyDataSetChanged();
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

    public String montarUrlPesquisa(){
        return  url_Servidor+
                "?categoria="+((ItemStaticos.filtro.getDescricaoCategoria()==null)?0:ItemStaticos.filtro.getDescricaoCategoria())+
                "&estabelicimento="+ItemStaticos.filtro.getEstabelecimento().getId().toString()+
                "&valor="+((ItemStaticos.filtro.getValor()==null)?0.0:ItemStaticos.filtro.getValor())+
                "&descricao="+ItemStaticos.filtro.getDescricaoProduto() +"&linha="+ItemStaticos.filtro.getLinhaAtual()+"&ordenar="+ItemStaticos.filtro.getOrdenar().replace("Selecione","");
    }

    public boolean isAcabouProdutos() {
        return acabouProdutos;
    }

    public void setAcabouProdutos(boolean acabouProdutos) {
        this.acabouProdutos = acabouProdutos;
    }

    public void DonwnloadImagenAsyncProdutos(final ProdutoBean produto) {
        class Down extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... urls) {
                return download_Image(urls[0]);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                Drawable img= new BitmapDrawable(result);
                produto.setImagem(img);
                listaProdutosComImagens.add(produto);
                Log.i("URL",produto.getPathImagem()+"COMPLETED");
                if(totalDownloadImg==posicaoAtualDownload){
                    totalDownloadImg=0;
                    posicaoAtualDownload=1;
                    listaProdutos=listaProdutosComImagens;
                    createListView();
                    setQueringIsRuning(false);
                    loading.setVisibility(View.GONE);
                }else{
                    posicaoAtualDownload=posicaoAtualDownload+1;
                }
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
        d.execute(produto.getPathImagem());
    }
    public void DonwnloadImagenAsyncProdutosMais(final ProdutoBean produto) {
        listaProdutosComImagens=new ArrayList<ProdutoBean>();
        class Down extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... urls) {
                return download_Image(urls[0]);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                Drawable img= new BitmapDrawable(result);
                produto.setImagem(img);
                listaProdutosComImagens.add(produto);
                Log.i("TESTE","TOtal:"+totalDownloadImg+"POsicao"+posicaoAtualDownload);
                if(totalDownloadImg==posicaoAtualDownload){
                    totalDownloadImg=0;
                    posicaoAtualDownload=1;
                    setQueringIsRuning(false);
                    adicionar(listaProdutosComImagens);
                    loading.setVisibility(View.GONE);
                    Log.i("TESTE", "Download COmpleteed");
                }else if(posicaoAtualDownload<totalDownloadImg){
                    posicaoAtualDownload=posicaoAtualDownload+1;
                }
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
        d.execute(produto.getPathImagem());
    }
}
