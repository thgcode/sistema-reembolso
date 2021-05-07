package br.com.zup.sistemareembolso.dtos.notafiscal.entrada;

import br.com.zup.sistemareembolso.models.NotaFiscal;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NotaFiscalDTO {
    private int codigoDaNota;

    private String linkDaImagem;

    private LocalDate dataDeEmissao;

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

    public LocalDate getDataDeEmissao() {
        return dataDeEmissao;
    }

    public void setDataDeEmissao(LocalDate dataDeEmissao) {
        this.dataDeEmissao = dataDeEmissao;
    }

    public NotaFiscal converterDTOParaNotaFiscal() {
        NotaFiscal notaFiscal = new NotaFiscal();

        notaFiscal.setLinkDaImagem(linkDaImagem);
        notaFiscal.setDataDeEmissao(dataDeEmissao);

        return notaFiscal;
    }
}
