package br.com.zup.sistemareembolso.dtos.localidade.saida;

import br.com.zup.sistemareembolso.models.Localidade;

public class SaidaLocalidadeDTO {
    private Integer codLocalidade;
    private String descricao;

    public SaidaLocalidadeDTO() {}

    public Integer getCodLocalidade() {
        return codLocalidade;
    }

    public void setCodLocalidade(Integer codLocalidade) {
        this.codLocalidade = codLocalidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static SaidaLocalidadeDTO converterLocalidadeParaDTO(Localidade localidade){

        SaidaLocalidadeDTO saidaCategoriaDTO = new SaidaLocalidadeDTO();

        saidaCategoriaDTO.setCodLocalidade(localidade.getCodLocalidade());
        saidaCategoriaDTO.setDescricao(localidade.getNome());

        return saidaCategoriaDTO;
    }
}
