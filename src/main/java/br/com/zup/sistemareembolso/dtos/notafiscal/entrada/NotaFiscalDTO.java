package br.com.zup.sistemareembolso.dtos.notafiscal.entrada;

import br.com.zup.sistemareembolso.models.NotaFiscal;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NotaFiscalDTO {
    @NotNull
    private String linkDaImagem;

    @NotNull
    private LocalDate dataDeEmissao;

    public NotaFiscalDTO() {

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
