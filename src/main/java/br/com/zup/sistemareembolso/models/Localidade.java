package br.com.zup.sistemareembolso.models;

import javax.persistence.*;

@Table(name = "localidades")
@Entity
public class Localidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codLocalidade;

    @Column(nullable = false, unique = true)
    private String nome;

    public Localidade() {

    }

    public int getCodLocalidade() {
        return codLocalidade;
    }

    public void setCodLocalidade(int codLocalidade) {
        this.codLocalidade = codLocalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}