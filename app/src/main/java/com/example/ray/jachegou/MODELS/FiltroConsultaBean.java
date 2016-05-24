package com.example.ray.jachegou.MODELS;

/**
 * Created by Ray on 25/04/2016.
 */
public class FiltroConsultaBean {
    private String descricaoProduto;
    private String descricaoCategoria;
    private String descricaoEstabelecimento;
    private Double valor=0.0;
    private int linhaAtual=0;
    private String ordenar="";
    private EstabelecimentoBean estabelecimento;

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

    public int getLinhaAtual() {
        return linhaAtual;
    }

    public void setLinhaAtual(int linhaAtual) {
        this.linhaAtual = linhaAtual;
    }

    public String getOrdenar() {
        return ordenar;
    }

    public void setOrdenar(String ordenar) {
        this.ordenar = ordenar;
    }


    public EstabelecimentoBean getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(EstabelecimentoBean estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}
