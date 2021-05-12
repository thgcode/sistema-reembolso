package br.com.zup.sistemareembolso.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Table(name = "notas_fiscais")
@Entity
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String linkDaImagem;

    @Column(nullable = false)
    private LocalDate dataDeEmissao;

    public NotaFiscal() {

    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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
}
