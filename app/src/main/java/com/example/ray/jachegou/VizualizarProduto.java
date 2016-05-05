package com.example.ray.jachegou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.MODELS.ProdutoBean;

import java.text.NumberFormat;

public class VizualizarProduto extends AppCompatActivity {
    private TextView nomeProduto;
    private ImageView imagem;
    private TextView valor;
    private Button btnAumentar;
    private Button btnDiminuir;
    private Button btnFechar;
    private Button btnContinuar;
    private TextView quantidade;
    private TextView txtIngrediente;
    private Integer qts=0;
    private ProdutoBean produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizar_produto);

        nomeProduto=(TextView)findViewById(R.id.nomeProdutoInf);
        imagem=(ImageView)findViewById(R.id.imagemViewInf);
        valor=(TextView)findViewById(R.id.valorInf);
        btnAumentar=(Button)findViewById(R.id.btnAumentar);
        btnDiminuir=(Button)findViewById(R.id.btnDiminuir);
        btnContinuar=(Button)findViewById(R.id.btnContinuar);
        btnFechar=(Button)findViewById(R.id.btnFechar);
        quantidade=(TextView)findViewById(R.id.qtsPedido);
        txtIngrediente=(TextView)findViewById(R.id.txtIngredientes);

        produto= ItemStaticos.produtoTela;

        if(ItemStaticos.listaProdutosPedidos.size()>0){
            for(ProdutoBean p:ItemStaticos.listaProdutosPedidos){
                if(produto.getId()==p.getId()){
                    qts=p.getQuantidadePedido();
                    break;
                }
            }
        }
        quantidade.setText(qts.toString());

        if(produto!=null){
            nomeProduto.setText(produto.getDescricao());
            imagem.setImageDrawable(produto.getImagem());
            valor.setText(NumberFormat.getCurrencyInstance().format(produto.getValor()));
            txtIngrediente.setText(produto.getIngredientes());
        }

        btnAumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qts++;
                quantidade.setText(qts.toString());
            }
        });

        btnDiminuir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qts!=0){
                    qts--;
                    quantidade.setText(qts.toString());
                }
            }
        });
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qts==0){
                    ItemStaticos.listaProdutosPedidos.remove(produto);
                }else{
                    produto.setQuantidadePedido(qts);
                    ItemStaticos.listaProdutosPedidos.add(produto);
                }
                finish();
            }
        });

        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VizualizarProduto.this,FinalizarPedido.class);
                startActivity(intent);
            }
        });

    }
}
