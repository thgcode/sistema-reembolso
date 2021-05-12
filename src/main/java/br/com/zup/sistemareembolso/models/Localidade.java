package br.com.zup.sistemareembolso.models;

import javax.persistence.*;

@Table(name = "localidades")
@Entity
public class Localidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nome;

    public Localidade() {

    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}