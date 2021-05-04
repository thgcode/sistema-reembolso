package br.com.zup.sistemareembolso.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;

@Table(name = "despesas")
@Entity
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Colaborador colaborador;

    @ManyToMany
    private List<Projeto> projeto;

    @Column(nullable = false)
    private String descricao;
    private double valor;
    private LocalDate dataEntrada;
    private LocalDate dataAprovacao;
    private Status status;

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

    public List<Projeto> getProjeto() { return projeto; }
    public void setProjeto(List<Projeto> projeto) {
        this.projeto = projeto;
    }
}
