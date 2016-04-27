package com.example.ray.jachegou.ADPATER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.MODELS.PedidoBean;
import com.example.ray.jachegou.MODELS.ProdutoBean;
import com.example.ray.jachegou.R;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Ray-PC on 29/03/2016.
 */
public class AdapterListViewPedidosAnteriores extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<PedidoBean> itens;
    private Integer modelo=0;

    public AdapterListViewPedidosAnteriores(Context context, List<PedidoBean> itens)
    {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
        this.modelo=modelo;
    }

    public int getCount()
    {
        return itens.size();
    }


    public PedidoBean getItem(int position)
    {
        return itens.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {

        PedidoBean item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.item_pedido, null);
        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.

        ((TextView) view.findViewById(R.id.nomeItemPedido)).setText(ItemStaticos.usuarioLogado.getNome());
        ((TextView) view.findViewById(R.id.dataItemPedido)).setText(item.getDateTime());

        return view;
    }

}
