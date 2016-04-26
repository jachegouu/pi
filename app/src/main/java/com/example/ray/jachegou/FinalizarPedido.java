package com.example.ray.jachegou;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.ray.jachegou.HELPER.ItemStaticos;

public class FinalizarPedido extends AppCompatActivity {
    private ListView listaView;
    private Button finalizar;
    private Button cancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_pedido);

        listaView=(ListView)findViewById(R.id.listaViewFinalizar);
        finalizar=(Button)findViewById(R.id.btnFinalizar);
        cancelar=(Button)findViewById(R.id.btnCancelarPedido);

        AdapterListView adapterListView = new AdapterListView(this.getApplicationContext(), ItemStaticos.listaProdutosPedidos);
        listaView.setAdapter(adapterListView);
        listaView.setCacheColorHint(Color.TRANSPARENT);
    }
}
