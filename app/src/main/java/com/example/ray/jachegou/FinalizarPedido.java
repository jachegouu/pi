package com.example.ray.jachegou;

import android.app.Activity;
import android.content.Intent;
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
import java.util.List;

public class FinalizarPedido extends AppCompatActivity {
    private ListView listaView;
    private Button finalizar;
    private Button cancelar;
    private TextView valorTotal;
    private Double valor=0.0;
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_pedido);

        listaView=(ListView)findViewById(R.id.listaViewFinalizar);
        finalizar=(Button)findViewById(R.id.btnFinalizar);
        cancelar=(Button)findViewById(R.id.btnCancelarPedido);
        valorTotal=(TextView)findViewById(R.id.txValorTotalPedido);
        activity=this;
        AdapterListView adapterListView = new AdapterListView(this.getApplicationContext(), ItemStaticos.listaProdutosPedidos,1);

        for(ProdutoBean produto:ItemStaticos.listaProdutosPedidos ){
            valor=valor+(produto.getValor()*produto.getQuantidadePedido());
        }
        valorTotal.setText("TOTAL: "+ NumberFormat.getCurrencyInstance().format(valor));
        listaView.setAdapter(adapterListView);
        listaView.setCacheColorHint(Color.TRANSPARENT);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServiceFazerPedido web= new WebServiceFazerPedido(activity);
                web.adicionarPedido(ItemStaticos.listaProdutosPedidos);
                ItemStaticos.listaProdutosPedidos=new ArrayList<ProdutoBean>();
                finish();
            }
        });

    }
}
