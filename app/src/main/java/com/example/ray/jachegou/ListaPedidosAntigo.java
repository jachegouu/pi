package com.example.ray.jachegou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ray.jachegou.SERVICE.WebService;
import com.example.ray.jachegou.SERVICE.WebServiceFazerPedido;

public class ListaPedidosAntigo extends AppCompatActivity {
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos_antigo);
        lista=(ListView)findViewById(R.id.listaPedidosAntigo);

        WebServiceFazerPedido web= new WebServiceFazerPedido(this);
        web.listarPedidos(lista);
    }
}
