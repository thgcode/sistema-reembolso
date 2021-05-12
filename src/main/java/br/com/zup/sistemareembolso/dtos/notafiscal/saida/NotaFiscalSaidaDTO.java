package br.com.zup.sistemareembolso.dtos.notafiscal.saida;

import br.com.zup.sistemareembolso.models.NotaFiscal;

import java.time.LocalDate;

public class NotaFiscalSaidaDTO {
    private Integer id;
    private String linkDaImagem;
    private LocalDate dataDaEmissao;
    private double valor;

    public NotaFiscalSaidaDTO() { }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

        dto.setId(notaFiscal.getId());
        dto.setLinkDaImagem(notaFiscal.getLinkDaImagem());
        dto.setDataDaEmissao(notaFiscal.getDataDeEmissao());
        dto.setValor(valor);

        return dto;
    }
}
