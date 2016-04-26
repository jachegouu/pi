package com.example.ray.jachegou;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.MODELS.CategoriaBean;
import com.example.ray.jachegou.MODELS.EstabelecimentoBean;
import com.example.ray.jachegou.SERVICE.WebServiceTelaPrincipal;

import java.text.NumberFormat;


public class FiltrosFragment extends Fragment {
    private SeekBar seekUm;
    private TextView valorUm,valorDois;
    private Button btnConsultar;
    private TextView nomeCliente;
    private ImageView imagemCliente;
    private AutoCompleteTextView categoriaEdit;
    private AutoCompleteTextView estabelecimentoEdit;
    private ArrayAdapter<String> categorias;
    private EditText descricaoProduto;
    private Double valor=0.0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_fragment_filtros_produtos,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoriaEdit=(AutoCompleteTextView) getView().findViewById(R.id.categoriaPesquisar);
        estabelecimentoEdit=(AutoCompleteTextView) getView().findViewById(R.id.estabelecimentosEdit);
        seekUm=(SeekBar)getView().findViewById(R.id.seekBarInicial);
        valorUm=(TextView)getView().findViewById(R.id.valorUm);
        btnConsultar=(Button)getView().findViewById(R.id.btnConsultar);
        descricaoProduto=(EditText)getView().findViewById(R.id.produtoDescricao);
        seekUm.setProgress(0);
        seekUm.setMax(100);

        //nomeCliente=(TextView)findViewById(R.id.nomeTexviewCliente);
        //imagemCliente=(ImageView)findViewById(R.id.testeImageView);

        if(ItemStaticos.usuarioLogado!=null){
            //  nomeCliente.setText(ItemStaticos.usuarioLogado.getNome());
            //imagemCliente.setImageDrawable(ItemStaticos.usuarioLogado.getImagem());
            //imagemCliente.setImageBitmap(ItemStaticos.usuarioLogado.getBitmap());
        }


        seekUm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valor=progress * 0.5;
                valorUm.setText(NumberFormat.getCurrencyInstance().format((progress * 0.5)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Fra.this, ListaProdutos.class);
                //startActivity(intent);
                TelaPrincipalControler mainActivity = (TelaPrincipalControler)ItemStaticos.telaPrincipal;
                mainActivity.drawerLayout.closeDrawers();
                FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();

                Fragment TelaA = new ProdutosLista();

                ItemStaticos.filtro.setValor(valor);
                ItemStaticos.filtro.setDescricaoCategoria(categoriaEdit.getText().toString());
                ItemStaticos.filtro.setDescricaoEstabelecimento( estabelecimentoEdit.getText().toString());
                ItemStaticos.filtro.setDescricaoProduto(descricaoProduto.getText().toString());

                fragmentTransaction.replace(R.id.containerView,TelaA);
                fragmentTransaction.commit();
            }
        });

        WebServiceTelaPrincipal tela=new WebServiceTelaPrincipal(this.getActivity());
        tela.CarregarTela();
    }
}
