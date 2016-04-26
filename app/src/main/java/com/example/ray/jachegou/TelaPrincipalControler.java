package com.example.ray.jachegou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.ray.jachegou.HELPER.ItemStaticos;


public class TelaPrincipalControler extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    String navTitles[];
    TypedArray navIcons;
    RecyclerView.Adapter recyclerViewAdapter;
    ActionBarDrawerToggle drawerToggle;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal_controler);

        //Vamos primeiro criar barra de ferramentas
        setupToolbar();

        //Inicializamos as Views
        recyclerView  = (RecyclerView) findViewById(R.id.recyclerView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerMainActivity);


        //Configuração de títulos e ícones do menu
        navTitles = getResources().getStringArray(R.array.navDrawerItems);
        navIcons = getResources().obtainTypedArray(R.array.navDrawerIcons);


        /**
         *Aqui passamos os arrays de títulos e ícones para o adapter .
         *Além disso passamos o contexto da activity .
         *Para que depois possamos usar o fragmentManager desta activity para adicionar/substituir fragmentos (telas).
         */

        recyclerViewAdapter = new RecyclerViewAdapter(navTitles,navIcons,this);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Finalmente configuramos o ActionBarDrawerToggle
        setupDrawerToggle();


        //Adicionamos o primeiro fragmento no container
        //Aqui você pode selecionar qual tela será exebida inicialmente
        ItemStaticos.telaPrincipal=this;
        Fragment squadFragment = new FiltrosFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerView,squadFragment,null);
        fragmentTransaction.commit();

    }
    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        //Isso é necessário para mudar o ícone do Drawer quando o estado dele é alterado
        drawerToggle.syncState();
    }

    @Override
    public void finish() {
        //super.finish();
        TelaPrincipalControler mainActivity = (TelaPrincipalControler)ItemStaticos.telaPrincipal;
        mainActivity.drawerLayout.closeDrawers();
        FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();

        Fragment TelaA = new FiltrosFragment();
        fragmentTransaction.replace(R.id.containerView,TelaA);
        fragmentTransaction.commit();
    }
}
