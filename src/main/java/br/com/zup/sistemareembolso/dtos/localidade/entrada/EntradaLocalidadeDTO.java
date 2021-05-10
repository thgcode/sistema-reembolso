package br.com.zup.sistemareembolso.dtos.localidade.entrada;

import br.com.zup.sistemareembolso.models.Localidade;

import javax.validation.constraints.NotBlank;

public class EntradaLocalidadeDTO {

    @NotBlank(message = "{validacao.notblank}")
    private String nome;

    public EntradaLocalidadeDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Localidade converterDTOParaLocalidade() {
        Localidade localidade = new Localidade();
        localidade.setNome(nome);
        return localidade;
    }
}
