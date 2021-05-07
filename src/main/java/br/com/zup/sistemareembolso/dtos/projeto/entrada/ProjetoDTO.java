package br.com.zup.sistemareembolso.dtos.projeto.entrada;

import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.models.Projeto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProjetoDTO {

    @NotBlank(message = "{validacao.notblank}")
    private String nomeDoProjeto;

    @NotBlank(message = "{validacao.notblank}")
    private String codigoDoProjeto;

    @NotNull()
    private Integer localidade;

    public ProjetoDTO() { }

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

    public Integer getLocalidade() {
        return localidade;
    }
    public void setLocalidade(Integer localidade) {
        this.localidade = localidade;
    }

    public Projeto converterDTOParaProjeto() {
        Projeto projeto = new Projeto();

        projeto.setNomeDoProjeto(nomeDoProjeto);
        projeto.setCodigoDoProjeto(codigoDoProjeto);

        Localidade localidade = new Localidade();

        localidade.setCodLocalidade(this.localidade);

        projeto.setLocalidade(localidade);

        return projeto;
    }
}
