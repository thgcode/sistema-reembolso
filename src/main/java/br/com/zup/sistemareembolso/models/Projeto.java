package br.com.zup.sistemareembolso.models;

import javax.persistence.*;
import java.util.List;

@Table(name = "projetos")
@Entity
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nomeDoProjeto;

    @Column
    private String codigoDoProjeto;

    @OneToMany
    private List<Despesa> despesa;

    @ManyToMany
    private List<Colaborador> colaboradores;

    public Projeto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeDoProjeto() {
        return nomeDoProjeto;
    }

    public void setNomeDoProjeto(String nomeDoProjeto) {
        this.nomeDoProjeto = nomeDoProjeto;
    }

    public String getCodigoDoProjeto() {
        return codigoDoProjeto;
    }

    public void setCodigoDoProjeto(String codigoDoProjeto) {
        this.codigoDoProjeto = codigoDoProjeto;
    }

    public List<Despesa> getDespesa() {
        return despesa;
    }

    public void setDespesa(List<Despesa> despesa) {
        this.despesa = despesa;
    }

    public List<Colaborador> getColaboradors() {
        return colaboradores;
    }

    public void setColaboradors(List<Colaborador> colaboradors) {
        this.colaboradores = colaboradors;
    }
}
