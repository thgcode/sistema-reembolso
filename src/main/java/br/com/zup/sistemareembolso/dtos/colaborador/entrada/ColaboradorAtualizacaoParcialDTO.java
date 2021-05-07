package br.com.zup.sistemareembolso.dtos.colaborador.entrada;

import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.TipoDaConta;

public class ColaboradorAtualizacaoParcialDTO {

    private String email;
    private String senha;
    private String banco;
    private String numeroDoBanco;
    private String agencia;
    private int digitoDaAgencia;
    private String conta;
    private int digitoDaConta;

    public ColaboradorAtualizacaoParcialDTO() {
    }

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

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNumeroDoBanco() {
        return numeroDoBanco;
    }

    public void setNumeroDoBanco(String numeroDoBanco) {
        this.numeroDoBanco = numeroDoBanco;
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

    public Colaborador converterDTOParaColaborador(String cpf){
        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(cpf);
        colaborador.setEmail(this.email);
        colaborador.setSenha(this.senha);
        colaborador.setBanco(this.banco);
        colaborador.setNumeroDoBanco(this.numeroDoBanco);
        colaborador.setAgencia(this.agencia);
        colaborador.setDigitoDaAgencia(this.digitoDaAgencia);
        colaborador.setConta(this.conta);
        colaborador.setDigitoDaConta(this.digitoDaConta);
        return colaborador;
    }
}
