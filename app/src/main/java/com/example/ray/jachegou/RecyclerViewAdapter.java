package com.example.ray.jachegou;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.MODELS.ProdutoBean;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    String[] titles;
    TypedArray icons;
    Context context;

    // O construtor recebe os títulos e ícones e o contexto da MainActivity.
    RecyclerViewAdapter(String[] titles, TypedArray icons, Context context){

        this.titles = titles;
        this.icons = icons;
        this.context = context;
    }

    /**
     *Estaé uma classe interna da classe RecyclerViewAdapter
     *Esta classe ViewHolder implementa View.OnClickListener para lidar com eventos de clique.
     *Se o itemType==1 ;  isto implica que a view é uma linha única contendo TextView and ImageView.
     *Este ViewHolder descreve uma tela de item em relação ao seu lugar dentro do RecyclerView.
     *Para cada item, há sempre uma ViewHolder associada a ele .
     */

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView  navTitle;
        ImageView navIcon;
        Context context;

        public ViewHolder(View drawerItem , int itemType , Context context){

            super(drawerItem);
            this.context = context;
            drawerItem.setOnClickListener(this);
            if(itemType==1){
                navTitle = (TextView) itemView.findViewById(R.id.tv_NavTitle);
                navIcon = (ImageView) itemView.findViewById(R.id.iv_NavIcon);
            }
        }

        @Override
        public void onClick(View v) {

            TelaPrincipalControler mainActivity = (TelaPrincipalControler)context;
            mainActivity.drawerLayout.closeDrawers();
            FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();

            switch (getPosition()){
                case 1:
                    Intent intent = new Intent(this.context,CadastroUsuarioActivity.class);
                    this.context.startActivity(intent);
                    break;
                case 2:
                    Intent intent4 = new Intent(this.context,ListaPedidosAntigo.class);
                    this.context.startActivity(intent4);
                    break;
                case 3:
                    Intent intent3 = new Intent(this.context,FinalizarPedido.class);
                    this.context.startActivity(intent3);
                    break;
                case 4:
                    ItemStaticos.listaProdutosPedidos=new ArrayList<ProdutoBean>();
                    Toast.makeText(context,"Excluido com sucesso !",Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    Intent intent2 = new Intent(this.context,TelaLogin.class);
                    ItemStaticos.usuarioLogado=null;
                    this.context.startActivity(intent2);
                    break;
            }
        }
    }

    /**
     *Isso é chamado cada vez que precisamos de um novo ViewHolder e uma nova ViewHolder é necessário para cada item em RecyclerView.
     *Então este ViewHolder é passado para onBindViewHolder para exibir os itens.
     */

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType==1){
            View itemLayout =   layoutInflater.inflate(R.layout.drawer_item_layout,null);
            return new ViewHolder(itemLayout,viewType,context);
        }
        else if (viewType==0) {
            View itemHeader = layoutInflater.inflate(R.layout.header_layout,null);
            return new ViewHolder(itemHeader,viewType,context);
        }



        return null;
    }

    /**
     *Este método é chamado pelo RecyclerView.Adapter para exibir os dados na posição especificada.
     *Este método deve atualizar o conteúdo do itemView para refletir o item na posição determinada.
     */

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        if(position!=0){
            holder.navTitle.setText(titles[position - 1]);
            holder.navIcon.setImageResource(icons.getResourceId(position-1,-1));
        }

    }

    /**
     *Ele retorna o número total de itens acrescentando um item
     *Assim, se a contagem total é 5, o método retorna 6.
     *6 implica que existem 5 itens de linha e 1 cabeçalho, sendo que o cabeçalho fina na posição zero.
     */

    @Override
    public int getItemCount() {
        return titles.length+1;
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0)return 0;
        else return 1;
    }


}