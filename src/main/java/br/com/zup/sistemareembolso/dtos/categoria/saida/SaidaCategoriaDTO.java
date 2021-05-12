package br.com.zup.sistemareembolso.dtos.categoria.saida;

import br.com.zup.sistemareembolso.models.Categoria;

public class SaidaCategoriaDTO {

    private Integer id;
    private String descricao;

    public SaidaCategoriaDTO() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static SaidaCategoriaDTO converterCategoriaParaDTO(Categoria categoria){
        SaidaCategoriaDTO saidaCategoriaDTO = new SaidaCategoriaDTO();
        saidaCategoriaDTO.setId(categoria.getId());
        saidaCategoriaDTO.setDescricao(categoria.getDescricao());
        return saidaCategoriaDTO;
    }
}
