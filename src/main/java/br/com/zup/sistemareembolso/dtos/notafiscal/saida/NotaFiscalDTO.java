package br.com.zup.sistemareembolso.dtos.notafiscal.saida;

import br.com.zup.sistemareembolso.models.NotaFiscal;

import java.time.LocalDate;

public class NotaFiscalDTO {
    private int codigoDaNota;
    private String linkDaImagem;
    private LocalDate dataDaEmissao;
    private double valor;

    public NotaFiscalDTO() {
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

    public static NotaFiscalDTO converterDTODeNotaFiscalEValor(NotaFiscal notaFiscal, double valor) {
        NotaFiscalDTO dto = new NotaFiscalDTO();
        
        dto.setCodigoDaNota(notaFiscal.getCodigoDaNota());
        dto.setLinkDaImagem(notaFiscal.getLinkDaImagem());
        dto.setDataDaEmissao(dto.getDataDaEmissao());
        dto.setValor(valor);

        return dto;
    }
}
