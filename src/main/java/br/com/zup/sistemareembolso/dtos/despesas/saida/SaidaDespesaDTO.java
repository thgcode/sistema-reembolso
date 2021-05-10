package br.com.zup.sistemareembolso.dtos.despesas.saida;

import br.com.zup.sistemareembolso.dtos.colaborador.saida.SaidaColaboradorDTO;
import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.models.Status;

public class SaidaDespesaDTO {
    private SaidaColaboradorDTO colaborador;
    private String descricao;
    private double valor;
    private Status status;

    public SaidaDespesaDTO() {}

    public SaidaColaboradorDTO getColaborador() { return colaborador; }
    public void setColaborador(SaidaColaboradorDTO colaborador) { this.colaborador = colaborador; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public static SaidaDespesaDTO converterDespesaParaDTO(Despesa despesa){
        SaidaDespesaDTO saidaDespesaDTO = new SaidaDespesaDTO();

        saidaDespesaDTO.setColaborador(SaidaColaboradorDTO.converterColaboradorParaDTO(despesa.getColaborador()));
        saidaDespesaDTO.setDescricao(despesa.getDescricao());
        saidaDespesaDTO.setValor(despesa.getValor());
        saidaDespesaDTO.setStatus(despesa.getStatus());

        return saidaDespesaDTO;
    }

}
