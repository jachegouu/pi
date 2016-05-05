package com.example.ray.jachegou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.MODELS.PedidoBean;
import com.example.ray.jachegou.SERVICE.WebServiceFazerPedido;

public class ListaPedidosAntigo extends AppCompatActivity {
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos_antigo);

        if(ItemStaticos.estaConectadoNoWifiOu3G(ListaPedidosAntigo.this)==true) {
            lista = (ListView) findViewById(R.id.listaPedidosAntigo);

            WebServiceFazerPedido web = new WebServiceFazerPedido(this);
            web.listarPedidos(lista);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PedidoBean item = (PedidoBean) lista.getAdapter().getItem(position);
                    Intent intent = new Intent(ListaPedidosAntigo.this, VizualizarPedido.class);
                    ItemStaticos.pedido = item;
                    startActivity(intent);
                }
            });
        }
    }
}
