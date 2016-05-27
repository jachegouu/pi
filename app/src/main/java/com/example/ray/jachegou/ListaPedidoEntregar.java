package com.example.ray.jachegou;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ray.jachegou.MODELS.PedidoBean;
import com.example.ray.jachegou.SERVICE.WebServiceFazerPedido;

public class ListaPedidoEntregar extends AppCompatActivity {
   private ListView listView;
    private PedidoBean selecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedido_entregar);
        listView=(ListView)findViewById(R.id.listaPedidoEntregar);
        WebServiceFazerPedido pedidos= new WebServiceFazerPedido(this);
        pedidos.listarPedidosEntregador(listView);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selecionado = (PedidoBean) listView.getItemAtPosition(position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selecionado = (PedidoBean) listView.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        getMenuInflater().inflate(R.menu.menu_entregador, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuLigar:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ListaPedidoEntregar.this, "Erro ao tentar efetuar ligação, confira suas permissões!", Toast.LENGTH_LONG).show();
                }else{
                    intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + selecionado.getUsuario().getTelefone()));
                    startActivity(intent);
                }
                break;
            case R.id.menuSms:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ListaPedidoEntregar.this,"Erro ao tentar enviar mensagem SMS, confira suas permissões!",Toast.LENGTH_LONG).show();
                }else{
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("sms:" + selecionado.getUsuario().getTelefone()));
                    intent.putExtra("sms_body", "Por favor, entre em contato com nossa empresa!");
                    startActivity(intent);
                }
                break;
            case R.id.menuMapa:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?z=14&q=" + selecionado.getUsuario().getEndereco()));
                startActivity(intent);
                break;
            case R.id.menuRota:
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + selecionado.getUsuario().getEndereco());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
