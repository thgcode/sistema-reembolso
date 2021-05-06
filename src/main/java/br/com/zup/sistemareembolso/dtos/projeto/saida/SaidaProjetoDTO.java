package br.com.zup.sistemareembolso.dtos.projeto.saida;

import br.com.zup.sistemareembolso.models.Projeto;

public class SaidaProjetoDTO {

    private String nomeDoProjeto;
    private String nomeDaLocalidade;

    public SaidaProjetoDTO() { }

    public String getNomeDoProjeto() {
        return nomeDoProjeto;
    }

    public void setNomeDoProjeto(String nomeDoProjeto) {
        this.nomeDoProjeto = nomeDoProjeto;
    }

    public String getNomeDaLocalidade() {
        return nomeDaLocalidade;
    }

    public void setNomeDaLocalidade(String nomeDaLocalidade) {
        this.nomeDaLocalidade = nomeDaLocalidade;
    }

    public static SaidaProjetoDTO converterProjetoParaDTO(Projeto projeto){
        SaidaProjetoDTO saidaProjetoDTO = new SaidaProjetoDTO();

        saidaProjetoDTO.setNomeDoProjeto(projeto.getNomeDoProjeto());
        saidaProjetoDTO.setNomeDaLocalidade(projeto.getLocalidade().getNome());

        return saidaProjetoDTO;
    }
}
