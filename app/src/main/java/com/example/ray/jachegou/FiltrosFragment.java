package com.example.ray.jachegou;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.SERVICE.WebServiceTelaPrincipal;
import java.text.NumberFormat;

public class FiltrosFragment extends Fragment {
    private SeekBar seekUm;
    private TextView valorUm;
    private Button btnConsultar;
    private AutoCompleteTextView categoriaEdit;
    private AutoCompleteTextView estabelecimentoEdit;
    private EditText descricaoProduto;
    private Double valor=0.0;
    private Spinner combox;
    private WebServiceTelaPrincipal tela;

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
        combox=(Spinner)getView().findViewById(R.id.ordenarSpiner);

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
                if(ItemStaticos.estaConectadoNoWifiOu3G(getActivity())==true && tela.selecionou(estabelecimentoEdit.getText().toString())!=null) {
                    TelaPrincipalControler mainActivity = (TelaPrincipalControler) ItemStaticos.telaPrincipal;
                    mainActivity.drawerLayout.closeDrawers();
                    FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();

                    Fragment TelaA = new ProdutosLista();

                    ItemStaticos.filtro.setValor(valor);
                    ItemStaticos.filtro.setDescricaoCategoria(categoriaEdit.getText().toString());
                    ItemStaticos.filtro.setEstabelecimento(tela.selecionou(estabelecimentoEdit.getText().toString()));
                    ItemStaticos.filtro.setDescricaoProduto(descricaoProduto.getText().toString());
                    ItemStaticos.filtro.setOrdenar(combox.getSelectedItem().toString());
                    fragmentTransaction.replace(R.id.containerView, TelaA);
                    fragmentTransaction.commit();
                }else if(tela.selecionou(estabelecimentoEdit.getText().toString())==null){
                    Toast.makeText(getActivity(),"Escolha um estabelecimento para fazer o pedido !",Toast.LENGTH_SHORT).show();
                }
            }
        });
        tela=new WebServiceTelaPrincipal(this.getActivity());
        tela.CarregarTela();
        if(ItemStaticos.listaProdutosPedidos!=null && ItemStaticos.listaProdutosPedidos.size()>0){
            estabelecimentoEdit.setEnabled(false);
            estabelecimentoEdit.setText(ItemStaticos.filtro.getEstabelecimento().getDescricao());
        }else{
            estabelecimentoEdit.setEnabled(true);
        }
    }


}
