package br.com.zup.sistemareembolso.dtos.projeto.saida;

import br.com.zup.sistemareembolso.models.Projeto;

public class SaidaProjetoDTO {
    private int id;
    private String nomeDoProjeto;
    private String nomeDaLocalidade;
    private double verba;

    public SaidaProjetoDTO() { }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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

    public double getVerba() {
        return verba;
    }

    public void setVerba(double verba) {
        this.verba = verba;
    }

    public static SaidaProjetoDTO converterProjetoParaDTO(Projeto projeto){
        SaidaProjetoDTO saidaProjetoDTO = new SaidaProjetoDTO();

        saidaProjetoDTO.setId(projeto.getId());
        saidaProjetoDTO.setNomeDoProjeto(projeto.getNomeDoProjeto());
        saidaProjetoDTO.setNomeDaLocalidade(projeto.getLocalidade().getNome());
        saidaProjetoDTO.setVerba(projeto.getVerba());

        return saidaProjetoDTO;
    }
}

