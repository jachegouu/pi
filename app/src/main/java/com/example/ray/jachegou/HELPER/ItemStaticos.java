package com.example.ray.jachegou.HELPER;

import android.content.Context;

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
    public static PedidoBean pedido;
    public static ProdutoBean produtoTela;
    public static List<ProdutoBean> listaProdutosPedidos=new ArrayList<ProdutoBean>();
    public static Context telaPrincipal;
    public static FiltroConsultaBean filtro=new FiltroConsultaBean();
}
