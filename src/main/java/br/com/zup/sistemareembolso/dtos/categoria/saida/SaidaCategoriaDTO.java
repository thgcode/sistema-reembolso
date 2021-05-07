package br.com.zup.sistemareembolso.dtos.categoria.saida;

import br.com.zup.sistemareembolso.models.Categoria;

public class SaidaCategoriaDTO {

    private Integer codCategoria;
    private String descricao;

    public SaidaCategoriaDTO() {
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

    public static SaidaCategoriaDTO converterCategoriaParaDTO(Categoria categoria){
        SaidaCategoriaDTO saidaCategoriaDTO = new SaidaCategoriaDTO();
        saidaCategoriaDTO.setCodCategoria(categoria.getCodCategoria());
        saidaCategoriaDTO.setDescricao(categoria.getDescricao());
        return saidaCategoriaDTO;
    }
}
