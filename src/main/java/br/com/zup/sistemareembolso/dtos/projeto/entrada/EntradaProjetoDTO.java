package br.com.zup.sistemareembolso.dtos.projeto.entrada;

import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.models.Projeto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EntradaProjetoDTO {

    @NotBlank(message = "{validacao.notblank}")
    private String nomeDoProjeto;

    @NotBlank(message = "{validacao.notblank}")
    private String codigoDoProjeto;

    @NotNull()
    private Integer idLocalidade;

    public EntradaProjetoDTO() { }

    public String getNomeDoProjeto() {
        return nomeDoProjeto;
    }
    public void setNomeDoProjeto(String nomeDoProjeto) {
        this.nomeDoProjeto = nomeDoProjeto;
    }

    public String getCodigoDoProjeto() {
        return codigoDoProjeto;
    }
    public void setCodigoDoProjeto(String codigoDoProjeto) {
        this.codigoDoProjeto = codigoDoProjeto;
    }

    public Integer getIdLocalidade() { return idLocalidade; }
    public void setIdLocalidade(Integer idLocalidade) { this.idLocalidade = idLocalidade; }

    public Projeto converterDTOParaProjeto() {
        Projeto projeto = new Projeto();

        projeto.setNomeDoProjeto(nomeDoProjeto);
        projeto.setCodigoDoProjeto(codigoDoProjeto);

        Localidade localidade = new Localidade();

        localidade.setId(this.idLocalidade);

        projeto.setLocalidade(localidade);

        return projeto;
    }
}
