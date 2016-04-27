package com.example.ray.jachegou;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.MODELS.ProdutoBean;
import com.example.ray.jachegou.SERVICE.WebServiceFazerPedido;

import java.text.NumberFormat;
import java.util.ArrayList;

public class VizualizarPedido extends AppCompatActivity {
    private ListView listaView;
    private Button sair;
    private TextView valorTotal;
    private Double valor=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizar_pedido);

        listaView=(ListView)findViewById(R.id.listaViewPedidoAntigo);
        sair=(Button)findViewById(R.id.btnSairPedidoAntigo);
        valorTotal=(TextView)findViewById(R.id.totalPedidoAntigo);

        AdapterListView adapterListView = new AdapterListView(this.getApplicationContext(), ItemStaticos.pedido.getLista(),1);

        for(ProdutoBean produto:ItemStaticos.pedido.getLista()){
          valor=valor+(produto.getValor()*produto.getQuantidadePedido());
        }

        valorTotal.setText("TOTAL: "+ NumberFormat.getCurrencyInstance().format(valor));
        listaView.setAdapter(adapterListView);
        listaView.setCacheColorHint(Color.TRANSPARENT);

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
