package br.com.zup.sistemareembolso.dtos.notafiscal.entrada;

import br.com.zup.sistemareembolso.models.NotaFiscal;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NotaFiscalDTO {
    @NotNull
    private byte []imagem;

    @NotNull
    private LocalDate dataDeEmissao;

    public NotaFiscalDTO() {

    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public LocalDate getDataDeEmissao() {
        return dataDeEmissao;
    }

    public void setDataDeEmissao(LocalDate dataDeEmissao) {
        this.dataDeEmissao = dataDeEmissao;
    }

    public NotaFiscal converterDTOParaNotaFiscal() {
        NotaFiscal notaFiscal = new NotaFiscal();

        notaFiscal.setImagem(imagem);
        notaFiscal.setDataDeEmissao(dataDeEmissao);

        return notaFiscal;
    }
}
