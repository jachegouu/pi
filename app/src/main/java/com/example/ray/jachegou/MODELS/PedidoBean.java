package com.example.ray.jachegou.MODELS;

import com.example.ray.jachegou.HELPER.ItemStaticos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray on 26/04/2016.
 */
public class PedidoBean {
    private int id;
    private String dateTime;
    private int status;
    private List<ProdutoBean> lista= new ArrayList<ProdutoBean>();

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

    public String getStatus() {
        if(this.status==0){
            return "RECEBIDO";
        }else if (this.status==1){
            return "EM ANDAMENTO";
        }else if (this.status==2){
            return "CONCLUIDO";
        }else if (this.status==3){
            return "CANCELADO";
        }else if (this.status==4){
            return "SEM STATUS";
        }else if (this.status==5){
            return "SEM STATUS 2";
        }else{
            return "";
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
