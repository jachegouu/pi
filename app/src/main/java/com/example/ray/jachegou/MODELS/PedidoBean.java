package com.example.ray.jachegou.MODELS;

import com.example.ray.jachegou.HELPER.ItemStaticos;

import java.util.List;

/**
 * Created by Ray on 26/04/2016.
 */
public class PedidoBean {
    private int id;
    private String dateTime;
    private List<ProdutoBean> lista;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<ProdutoBean> getLista() {
        return lista;
    }

    public void setLista(List<ProdutoBean> lista) {
        this.lista = lista;
    }

    @Override
    public String toString() {
        return ItemStaticos.usuarioLogado.getNome() + " | "+dateTime;
    }
}
