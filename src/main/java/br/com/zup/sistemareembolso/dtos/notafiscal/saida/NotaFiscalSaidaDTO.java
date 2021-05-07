package br.com.zup.sistemareembolso.dtos.notafiscal.saida;

import br.com.zup.sistemareembolso.models.NotaFiscal;

import java.time.LocalDate;

public class NotaFiscalSaidaDTO {
    private int codigoDaNota;
    private String linkDaImagem;
    private LocalDate dataDaEmissao;
    private double valor;

    public NotaFiscalSaidaDTO() {
    }

    public int getCodigoDaNota() {
        return codigoDaNota;
    }

    public void setCodigoDaNota(int codigoDaNota) {
        this.codigoDaNota = codigoDaNota;
    }

    public String getLinkDaImagem() {
        return linkDaImagem;
    }

    public void setLinkDaImagem(String linkDaImagem) {
        this.linkDaImagem = linkDaImagem;
    }

    public LocalDate getDataDaEmissao() {
        return dataDaEmissao;
    }

    public void setDataDaEmissao(LocalDate dataDaEmissao) {
        this.dataDaEmissao = dataDaEmissao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public static NotaFiscalSaidaDTO converterDTODeNotaFiscalEValor(NotaFiscal notaFiscal, double valor) {
        NotaFiscalSaidaDTO dto = new NotaFiscalSaidaDTO();

        dto.setCodigoDaNota(notaFiscal.getCodigoDaNota());
        dto.setLinkDaImagem(notaFiscal.getLinkDaImagem());
        dto.setDataDaEmissao(notaFiscal.getDataDeEmissao());
        dto.setValor(valor);

        return dto;
    }
}
