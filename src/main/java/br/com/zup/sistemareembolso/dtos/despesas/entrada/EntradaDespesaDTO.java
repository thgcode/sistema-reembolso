package br.com.zup.sistemareembolso.dtos.despesas.entrada;

import br.com.zup.sistemareembolso.dtos.notafiscal.entrada.NotaFiscalDTO;
import br.com.zup.sistemareembolso.models.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class EntradaDespesaDTO {

    @CPF(message = "{validacao.cpf}")
    private String cpf;

    @NotBlank(message = "{validacao.notblank}")
    private String descricao;

    @Positive
    private double valor;

    @Positive
    private int projetoId;

    @Positive
    private Integer codCategoria;

    @NotNull
    private NotaFiscalDTO notaFiscal;

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

    public int getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }

    public Integer getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
    }

    public NotaFiscalDTO getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscalDTO notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public Despesa converterDTOparaDespesas(){
        Despesa despesa = new Despesa();

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(this.cpf);

        Projeto projeto = new Projeto();
        projeto.setId(projetoId);

        despesa.setProjeto(projeto);
        despesa.setColaborador(colaborador);

        despesa.setDescricao(this.descricao);
        despesa.setValor(this.valor);

        Categoria categoria = new Categoria();
        categoria.setCodCategoria(this.codCategoria);

        despesa.setCategoria(categoria);
        despesa.setNotaFiscal(notaFiscal.converterDTOParaNotaFiscal());

        return despesa;
    }
}
