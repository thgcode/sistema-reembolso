package br.com.zup.sistemareembolso.models;

import javax.persistence.*;
import java.util.List;

@Table(name = "colaboradores")
@Entity
public class Colaborador {

    @Column(unique = true, nullable = false)
    @Id
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(unique = true, nullable = false)
    private String nomeCompleto;

    @Column(nullable = false)
    private Cargo cargo;

    @ManyToOne
    private Projeto projeto;

    private String banco;
    private String numeroDoBanco;
    private String agencia;
    private int digitoDaAgencia;
    private String conta;
    private int digitoDaConta;
    private TipoDaConta tipoDaConta;

    @OneToMany()
    private List<Despesa> despesas;

    public Colaborador() { }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getBanco() {
        return banco;
    }

    public List<Despesa> getDespesas() { return despesas; }
    public void setDespesas(List<Despesa> despesas) { this.despesas = despesas; }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public int getDigitoDaAgencia() {
        return digitoDaAgencia;
    }

    public void setDigitoDaAgencia(int digitoDaAgencia) {
        this.digitoDaAgencia = digitoDaAgencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public int getDigitoDaConta() {
        return digitoDaConta;
    }

    public void setDigitoDaConta(int digitoDaConta) {
        this.digitoDaConta = digitoDaConta;
    }

    public TipoDaConta getTipoDaConta() {
        return tipoDaConta;
    }

    public void setTipoDaConta(TipoDaConta tipoDaConta) {
        this.tipoDaConta = tipoDaConta;
    }

    public String getNumeroDoBando() {
        return numeroDoBanco;
    }

    public void setNumeroDoBando(String numeroDoBanco) {
        this.numeroDoBanco = numeroDoBanco;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public String getNumeroDoBanco() {
        return numeroDoBanco;
    }

    public void setNumeroDoBanco(String numeroDoBanco) {
        this.numeroDoBanco = numeroDoBanco;
    }
}
