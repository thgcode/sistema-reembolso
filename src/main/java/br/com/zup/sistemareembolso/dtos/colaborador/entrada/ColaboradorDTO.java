package br.com.zup.sistemareembolso.dtos.colaborador.entrada;

import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.models.TipoDaConta;
import org.hibernate.validator.constraints.br.CPF;
;import javax.validation.constraints.*;

public class ColaboradorDTO {

    @Email(message = "{validacao.email}")
    private String email;

    @NotBlank(message = "{validacao.notblank}")
    private String senha;

    @NotBlank(message = "{validacao.notblank}")
    private String nomeCompleto;

    @CPF(message = "{validacao.cpf}")
    private String cpf;

    @NotNull()
    private Cargo cargo;

    @NotBlank(message = "{validacao.notblank}")
    private String banco;

    @NotBlank(message = "{validacao.notblank}")
    private String numeroDoBanco;

    @NotBlank(message = "{validacao.notblank}")
    private String agencia;

    @Positive
    private int digitoDaAgencia;

    @NotBlank(message = "{validacao.notblank}")
    private String conta;

    @NotNull(message = "{validacao.notnull}")
    @Positive
    private int digitoDaConta;

    @NotNull()
    private TipoDaConta tipoDaConta;

    private int projetoId;

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

    public TipoDaConta getTipoDaConta() {
        return tipoDaConta;
    }

    public void setTipoDaConta(TipoDaConta tipoDaConta) {
        this.tipoDaConta = tipoDaConta;
    }

    public int getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }

    public Colaborador converterDTOparaColaborador(){

        Colaborador colaborador = new Colaborador();
        colaborador.setEmail(this.email);
        colaborador.setSenha(this.senha);
        colaborador.setNomeCompleto(this.nomeCompleto);
        colaborador.setCpf(this.cpf);
        colaborador.setCargo(this.cargo);
        colaborador.setBanco(this.banco);
        colaborador.setNumeroDoBanco(this.numeroDoBanco);
        colaborador.setAgencia(this.agencia);
        colaborador.setDigitoDaAgencia(this.digitoDaAgencia);
        colaborador.setConta(this.conta);
        colaborador.setDigitoDaConta(this.digitoDaConta);
        colaborador.setTipoDaConta(this.tipoDaConta);

        Projeto projeto = new Projeto();
        projeto.setId(projetoId);

        colaborador.setProjeto(projeto);

        return colaborador;
    }
}
