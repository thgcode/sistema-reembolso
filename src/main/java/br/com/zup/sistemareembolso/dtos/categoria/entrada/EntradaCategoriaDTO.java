package br.com.zup.sistemareembolso.dtos.categoria.entrada;

import br.com.zup.sistemareembolso.models.Categoria;

import javax.validation.constraints.NotBlank;

public class EntradaCategoriaDTO {

    @NotBlank(message = "{validacao.notblank}")
    private String descricao;

    public EntradaCategoriaDTO() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria converterDTOParaCategoria(){
        Categoria categoria = new Categoria();
        categoria.setDescricao(this.descricao);
        return categoria;
    }
}
