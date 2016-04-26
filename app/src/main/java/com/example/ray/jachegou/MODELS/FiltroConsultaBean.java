package com.example.ray.jachegou.MODELS;

/**
 * Created by Ray on 25/04/2016.
 */
public class FiltroConsultaBean {
    private String descricaoProduto;
    //private EstabelecimentoBean estabelecimento=new EstabelecimentoBean();
    //private CategoriaBean categoria= new CategoriaBean();
    private String descricaoCategoria;
    private String descricaoEstabelecimento;
    private Double valor=0.0;

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getDescricaoCategoria() {
        return descricaoCategoria;
    }

    public void setDescricaoCategoria(String descricaoCategoria) {
        this.descricaoCategoria = descricaoCategoria;
    }

    public String getDescricaoEstabelecimento() {
        return descricaoEstabelecimento;
    }

    public void setDescricaoEstabelecimento(String descricaoEstabelecimento) {
        this.descricaoEstabelecimento = descricaoEstabelecimento;
    }
}
