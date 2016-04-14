package com.example.ray.jachegou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ray-PC on 29/03/2016.
 */
public class AdapterListView extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<ItemList> itens;

    public AdapterListView(Context context, ArrayList<ItemList> itens)
    {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Retorna a quantidade de itens
     *
     * @return
     */
    public int getCount()
    {
        return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public ItemList getItem(int position)
    {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {

        ItemList item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.item, null);
        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.

        ((TextView) view.findViewById(R.id.text)).setText(item.getNomeProduto());
        ((TextView) view.findViewById(R.id.valor)).setText(item.getValorProduto());
        ((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());


        return view;
    }
}
