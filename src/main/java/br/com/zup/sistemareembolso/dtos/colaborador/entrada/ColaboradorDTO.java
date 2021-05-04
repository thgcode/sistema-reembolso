package br.com.zup.sistemareembolso.dtos.colaborador.entrada;

import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.models.TipoDaConta;
import org.hibernate.validator.constraints.br.CPF;;import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class ColaboradorDTO {

    @Email
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    private String nomeCompleto;
    @CPF
    private String cpf;
    @NotBlank
    private Cargo cargo;
    @NotBlank
    private String banco;
    @NotBlank
    private String numeroDoBando;
    @NotBlank
    private String agencia;
    @NotBlank
    private int digitoDaAgencia;
    @NotBlank
    private String conta;
    @NotBlank
    private int digitoDaConta;
    @NotBlank
    private TipoDaConta tipoDaConta;
    @NotBlank
    private List<Projeto> listaDeProjetos;

    public ColaboradorDTO() {
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

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNumeroDoBando() {
        return numeroDoBando;
    }

    public void setNumeroDoBando(String numeroDoBando) {
        this.numeroDoBando = numeroDoBando;
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

    public List<Projeto> getListaDeProjetos() {
        return listaDeProjetos;
    }

    public void setListaDeProjetos(List<Projeto> listaDeProjetos) {
        this.listaDeProjetos = listaDeProjetos;
    }

    public Colaborador converterDTOparaColaborador(){

        Colaborador colaborador = new Colaborador();
        colaborador.setEmail(this.email);
        colaborador.setSenha(this.senha);
        colaborador.setNomeCompleto(this.nomeCompleto);
        colaborador.setCpf(this.cpf);
        colaborador.setCargo(this.cargo);
        colaborador.setBanco(this.banco);
        colaborador.setNumeroDoBando(this.numeroDoBando);
        colaborador.setAgencia(this.agencia);
        colaborador.setDigitoDaAgencia(this.digitoDaAgencia);
        colaborador.setConta(this.conta);
        colaborador.setDigitoDaConta(this.digitoDaConta);
        colaborador.setTipoDaConta(this.tipoDaConta);
        colaborador.setListaDeProjetos(this.listaDeProjetos);

        return colaborador;
    }
}
