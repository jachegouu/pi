package com.example.ray.jachegou;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaProdutos extends AppCompatActivity {
    private ListView listView;
    private AdapterListView adapterListView;
    private ArrayList<ItemList> itens;
    private Button diminuirQts,aumentarQts;
    private TextView qts;
    private Integer valorTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        listView = (ListView) findViewById(R.id.list);
        diminuirQts=(Button) findViewById(R.id.btnDiminuir);
        aumentarQts=(Button) findViewById(R.id.btnAumentar);
        createListView();


    }
    private void createListView()
    {
        //Criamos nossa lista que preenchera o ListView
        itens = new ArrayList<ItemList>();
        ItemList item1 = new ItemList("LANCHE RECHEADO", R.drawable.img1,"R$ 50,00");
        ItemList item2 = new ItemList("LACHE VEGETARIANO", R.drawable.img2,"R$ 50,00");

        itens.add(item1);
        itens.add(item2);


        //Cria o adapter
        adapterListView = new AdapterListView(this, itens);

        //Define o Adapter

        listView.setAdapter(adapterListView);
        //Cor quando a lista é selecionada para ralagem.
        listView.setCacheColorHint(Color.TRANSPARENT);
    }
}

