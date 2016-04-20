package com.example.ray.jachegou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ray.jachegou.HELPER.ItemStaticos;
import com.example.ray.jachegou.MODELS.ProdutoBean;
import com.example.ray.jachegou.SERVICE.WebServiceListaProduto;

public class ListaProdutos extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private Button diminuirQts, aumentarQts;
    private TextView qts;
    private Integer valorTextview;
    private ListView listView;
    private WebServiceListaProduto listar;
    private int qtsItenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView=(ListView)findViewById(R.id.listaProdutosListView);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listar = new WebServiceListaProduto(this);
        listar.listaProdutos();
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int primeiroItemVisivel, int totalItemVisivel, int totalItem) {
                if((primeiroItemVisivel+totalItemVisivel)==totalItem){
                    if(listar.isQueringIsRuning()==false) {
                        listar.carregarMaisProdutos();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_produtos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menuPrincipalVerPedidos) {

        } else if (id == R.id.menuPrincipalAlterarDados) {
            //Intent intent = new Intent(TelaPrincipal.this,CadastroUsuarioActivity.class);
            //startActivity(intent);
        } else if (id == R.id.menuPrincipalSair) {
            //Intent intent = new Intent(TelaPrincipal.this,LoginActivity.class);
            //startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
        ProdutoBean item = (ProdutoBean)listView.getAdapter().getItem(arg2);
        Intent intent=new Intent(ListaProdutos.this,InformacoesProduto.class);
        //intent.putExtra("produto",item);
        ItemStaticos.produtoTela=item;
        startActivity(intent);
    }
}