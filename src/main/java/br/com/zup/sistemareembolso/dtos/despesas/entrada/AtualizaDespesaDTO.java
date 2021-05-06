package br.com.zup.sistemareembolso.dtos.despesas.entrada;

import br.com.zup.sistemareembolso.models.Categoria;
import br.com.zup.sistemareembolso.models.Despesa;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class AtualizaDespesaDTO {

    @NotBlank
    private String descricao;
    @Positive
    private double valor;

    private Categoria categoria;

    public AtualizaDespesaDTO() {}

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Despesa atualizarDTOParaDespesa(int id){

        Despesa despesa = new Despesa();

        despesa.setId(id);
        despesa.setDescricao(this.descricao);
        despesa.setValor(this.valor);
        despesa.setCategoria(this.categoria);

        return despesa;
    }
}
