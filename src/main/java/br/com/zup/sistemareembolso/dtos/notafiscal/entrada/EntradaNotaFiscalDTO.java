package br.com.zup.sistemareembolso.dtos.notafiscal.entrada;

import br.com.zup.sistemareembolso.models.NotaFiscal;

import java.time.LocalDate;

public class EntradaNotaFiscalDTO {

    private int id;
    private String linkDaImagem;
    private LocalDate dataDeEmissao;

    public EntradaNotaFiscalDTO() { }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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

        notaFiscal.setId(id);
        notaFiscal.setLinkDaImagem(linkDaImagem);
        notaFiscal.setDataDeEmissao(dataDeEmissao);

        return notaFiscal;
    }
}
