package br.com.zup.sistemareembolso.dtos.localidade.saida;

import br.com.zup.sistemareembolso.models.Localidade;

public class SaidaLocalidadeDTO {
    private Integer id;
    private String descricao;

    public SaidaLocalidadeDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static SaidaLocalidadeDTO converterLocalidadeParaDTO(Localidade localidade){

        SaidaLocalidadeDTO saidaCategoriaDTO = new SaidaLocalidadeDTO();

        saidaCategoriaDTO.setId(localidade.getId());
        saidaCategoriaDTO.setDescricao(localidade.getNome());

        return saidaCategoriaDTO;
    }
}
