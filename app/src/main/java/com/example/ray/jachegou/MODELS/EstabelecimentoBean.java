package com.example.ray.jachegou.MODELS;

/**
 * Created by Ray on 25/04/2016.
 */
public class EstabelecimentoBean {
    private Integer id;
    private String descricao;


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
