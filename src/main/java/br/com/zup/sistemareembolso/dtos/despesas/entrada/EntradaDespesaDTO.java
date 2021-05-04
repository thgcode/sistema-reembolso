package br.com.zup.sistemareembolso.dtos.despesas.entrada;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.models.Status;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

public class EntradaDespesaDTO {

    @CPF
    private String cpf;

    @NotBlank
    private String descricao;

    @Positive
    private double valor;

    private Status status;

    private List<Projeto> listaProjetos;

    public List<Projeto> getListaProjetos() {
        return listaProjetos;
    }

    public EntradaDespesaDTO() {}

    public String getCpf() {
        return cpf; }
    public void setCpf(String cpf) {
        this.cpf = cpf; }

    public String getDescricao() {
        return descricao; }
    public void setDescricao(String descricao) {
        this.descricao = descricao; }

    public double getValor() {
        return valor; }
    public void setValor(double valor) {
        this.valor = valor; }

    public Status getStatus() {
        return status; }
    public void setStatus(Status status) {
        this.status = status; }

    public void setListaProjetos(List<Projeto> listaProjetos) {
        this.listaProjetos = listaProjetos;
    }


    public Despesa converterDTOparaDespesas(){
        Despesa despesa = new Despesa();

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(this.cpf);
        despesa.setProjeto(this.listaProjetos);
        despesa.setColaborador(colaborador);
        despesa.setDescricao(this.descricao);
        despesa.setValor(this.valor);
        despesa.setStatus(this.status);

        return despesa;
    }
}
