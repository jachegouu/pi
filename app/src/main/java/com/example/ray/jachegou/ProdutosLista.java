package com.example.ray.jachegou;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.MODELS.ProdutoBean;
import com.example.ray.jachegou.SERVICE.WebServiceListaProduto;


public class ProdutosLista extends Fragment {
    private Button diminuirQts, aumentarQts;
    private TextView qts;
    private Integer valorTextview;
    private ListView listView;
    private WebServiceListaProduto listar;
    private int qtsItenList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_produtos_lista, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        listView=(ListView)getActivity().findViewById(R.id.listaProdutosListView);
        listar = new WebServiceListaProduto(this.getActivity());
        listar.listaProdutos();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProdutoBean item = (ProdutoBean)listView.getAdapter().getItem(position);
                Intent intent=new Intent(getActivity(),InformacoesProduto.class);
                ItemStaticos.produtoTela=item;
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int primeiroItemVisivel, int totalItemVisivel, int totalItem) {
                if ((primeiroItemVisivel + totalItemVisivel) == totalItem) {
                    if (listar.isQueringIsRuning() == false) {
                        listar.carregarMaisProdutos();
                    }
                }
            }
        });
    }

}