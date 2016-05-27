package com.example.ray.jachegou.HELPER;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.ray.jachegou.MODELS.EntregadorBean;
import com.example.ray.jachegou.MODELS.FiltroConsultaBean;
import com.example.ray.jachegou.MODELS.PedidoBean;
import com.example.ray.jachegou.MODELS.ProdutoBean;
import com.example.ray.jachegou.MODELS.UsuarioBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray on 04/04/2016.
 */
public class ItemStaticos {
    public static UsuarioBean usuarioLogado;
    public static EntregadorBean entregador;
    public static PedidoBean pedido;
    public static ProdutoBean produtoTela;
    public static List<ProdutoBean> listaProdutosPedidos=new ArrayList<ProdutoBean>();
    public static Context telaPrincipal;
    public static FiltroConsultaBean filtro=new FiltroConsultaBean();

    public static  boolean estaConectadoNoWifiOu3G(Activity activity) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
            Toast.makeText(activity,"Por Favor, Verifique sua Conexão com Internet !",Toast.LENGTH_SHORT).show();
        }
        return conectado;
    }
}
