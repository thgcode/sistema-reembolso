package br.com.zup.sistemareembolso.dtos.despesas.saida;

import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.models.Status;

public class SaidaDespesaDTO {
    private Colaborador colaborador;
    private Projeto projeto;
    private String descricao;
    private double valor;
    private Status status;

    public SaidaDespesaDTO() {}

    public Colaborador getColaborador() { return colaborador; }
    public void setColaborador(Colaborador colaborador) { this.colaborador = colaborador; }

    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public static SaidaDespesaDTO converterDespesaParaDTO(Despesa despesa){
        SaidaDespesaDTO saidaDespesaDTO = new SaidaDespesaDTO();

        saidaDespesaDTO.setColaborador(despesa.getColaborador());
        saidaDespesaDTO.setProjeto(despesa.getProjeto());
        saidaDespesaDTO.setDescricao(despesa.getDescricao());
        saidaDespesaDTO.setValor(despesa.getValor());
        saidaDespesaDTO.setStatus(despesa.getStatus());

        return saidaDespesaDTO;
    }

}
