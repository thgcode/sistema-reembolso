package br.com.zup.sistemareembolso.dtos.projeto.entrada;

import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Projeto;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;

public class ProjetoDTO {
    @NotBlank
    private String nomeDoProjeto;

    @NotBlank
    private String codigoDoProjeto;

    private String cpfColaboradorQueCriou;

    public ProjetoDTO() {

    }

    public ProjetoDTO(String nomeDoProjeto, String codigoDoProjeto, String cpfColaboradorQueCriou) {
        this.nomeDoProjeto = nomeDoProjeto;
        this.codigoDoProjeto = codigoDoProjeto;
        this.cpfColaboradorQueCriou = cpfColaboradorQueCriou;
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

    public String getCpfColaboradorQueCriou() {
        return cpfColaboradorQueCriou;
    }

    public void setCpfColaboradorQueCriou(String cpfColaboradorQueCriou) {
        this.cpfColaboradorQueCriou = cpfColaboradorQueCriou;
    }

    public Projeto converterDTOParaProjeto() {
        Projeto projeto = new Projeto();

        projeto.setNomeDoProjeto(nomeDoProjeto);
        projeto.setCodigoDoProjeto(codigoDoProjeto);

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(cpfColaboradorQueCriou);
        projeto.setColaboradors(Arrays.asList(new Colaborador()));

        return projeto;
    }
}
