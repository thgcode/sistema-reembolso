package br.com.zup.sistemareembolso.dtos.categoria.entrada;

import br.com.zup.sistemareembolso.models.Categoria;

public class EntradaCategoriaDTO {

    private Integer codCategoria;
    private String descricao;

    public EntradaCategoriaDTO() {
    }

    public Integer getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria converterDTOParaCategoria(){
        Categoria categoria = new Categoria();

        categoria.setCodCategoria(this.codCategoria);
        categoria.setDescricao(this.descricao);
        return categoria;
    }
}
