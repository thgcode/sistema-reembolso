package br.com.zup.sistemareembolso.dtos.projeto.entrada;

import br.com.zup.sistemareembolso.models.Projeto;

import javax.validation.constraints.NotBlank;

public class ProjetoDTO {
    @NotBlank
    private String nomeDoProjeto;

    @NotBlank
    private String codigoDoProjeto;

    public ProjetoDTO() {

    }

    public ProjetoDTO(String nomeDoProjeto, String codigoDoProjeto) {
        this.nomeDoProjeto = nomeDoProjeto;
        this.codigoDoProjeto = codigoDoProjeto;
    }

    public String getNomeDoProjeto() {
        return nomeDoProjeto;
    }

    public void setNomeDoProjeto(String nomeDoProjeto) {
        this.nomeDoProjeto = nomeDoProjeto;
    }

    public String getCodigoDoProjeto() {
        return codigoDoProjeto;
    }

    public void setCodigoDoProjeto(String codigoDoProjeto) {
        this.codigoDoProjeto = codigoDoProjeto;
    }

    public Projeto converterDTOParaProjeto() {
        Projeto projeto = new Projeto();

        projeto.setNomeDoProjeto(nomeDoProjeto);
        projeto.setCodigoDoProjeto(codigoDoProjeto);
        return projeto;
    }
}
