package br.com.zup.sistemareembolso.models;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "notas_fiscais")
@Entity
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoDaNota;

    @Lob
    @Column(nullable = false)
    private byte []imagem;

    @Column(nullable = false)
    private LocalDate dataDeEmissao;

    public NotaFiscal() {

    }

    public int getCodigoDaNota() {
        return codigoDaNota;
    }

    public void setCodigoDaNota(int codigoDaNota) {
        this.codigoDaNota = codigoDaNota;
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
}
