package br.com.zup.sistemareembolso.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Table(name = "despesas")
@Entity
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Colaborador colaborador;

    @ManyToOne
    private Colaborador aprovador;

    @ManyToOne
    private Projeto projeto;

    @Column(nullable = false)
    private String descricao;
    private double valor;
    private LocalDate dataEntrada;
    private LocalDate dataAprovacao;
    private Status status;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne(optional = false)
    private NotaFiscal notaFiscal;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }
    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public Colaborador getAprovador() {
        return aprovador;
    }

    public void setAprovador(Colaborador aprovador) {
        this.aprovador = aprovador;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataAprovacao() { return dataAprovacao; }
    public void setDataAprovacao(LocalDate dataAprovacao) { this.dataAprovacao = dataAprovacao; }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }
}
