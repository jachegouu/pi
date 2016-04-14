package com.example.ray.jachegou;

/**
 * Created by Ray-PC on 29/03/2016.
 */
public class ItemList {
    private String nomeProduto;
    private String valorProduto;
    private int iconeRid;

    public ItemList()    {

    }

    public ItemList(String nomeProduto, int iconeRid,String valorProduto)    {
        this.nomeProduto = nomeProduto;
        this.iconeRid = iconeRid;
        this.valorProduto=valorProduto;
    }

    public int getIconeRid()    {
        return iconeRid;
    }

    public void setIconeRid(int iconeRid)    {
        this.iconeRid = iconeRid;
    }

    public String getNomeProduto()    {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto)    {
        this.nomeProduto = nomeProduto;
    }

    public String getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(String valorProduto) {
        this.valorProduto = valorProduto;
    }
}
