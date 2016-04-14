package com.example.ray.jachegou;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ray.jachegou.HELPER.UsuarioHelper;

import java.text.NumberFormat;

public class TelaPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SeekBar seekUm;
    private TextView valorUm,valorDois;
    private Button btnConsultar;
    private TextView nomeCliente;
    private ImageView imagemCliente;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        seekUm=(SeekBar)findViewById(R.id.seekBarInicial);
        valorUm=(TextView)findViewById(R.id.valorUm);
        btnConsultar=(Button)findViewById(R.id.btnConsultar);

        seekUm.setProgress(0);
        seekUm.setMax(100);

        nomeCliente=(TextView)findViewById(R.id.nomeTexviewCliente);
        imagemCliente=(ImageView)findViewById(R.id.testeImageView);

        if(UsuarioHelper.usuarioLogado!=null){
            nomeCliente.setText(UsuarioHelper.usuarioLogado.getNome());
            //imagemCliente.setImageDrawable(UsuarioHelper.usuarioLogado.getImagem());
            imagemCliente.setImageBitmap(UsuarioHelper.usuarioLogado.getBitmap());
        }


        seekUm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
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
                Intent intent = new Intent(TelaPrincipal.this, ListaProdutos.class);
                startActivity(intent);
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
        getMenuInflater().inflate(R.menu.tela_principal, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menuPrincipalVerPedidos) {

        } else if (id == R.id.menuPrincipalAlterarDados) {
            Intent intent = new Intent(TelaPrincipal.this,CadastroUsuarioActivity.class);
            startActivity(intent);
        } else if (id == R.id.menuPrincipalSair) {
            Intent intent = new Intent(TelaPrincipal.this,LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
