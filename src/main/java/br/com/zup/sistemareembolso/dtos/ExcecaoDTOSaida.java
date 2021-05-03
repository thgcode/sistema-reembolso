package br.com.zup.sistemareembolso.dtos;

import java.util.List;

public class ExcecaoDTOSaida {
    private String tipo;
    private List<ErroSaida> erros;

    public ExcecaoDTOSaida() {

    }

    public ExcecaoDTOSaida(String tipo, List<ErroSaida> erros) {
        this.tipo = tipo;
        this.erros = erros;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<ErroSaida> getErros() {
        return erros;
    }

    public void setErros(List<ErroSaida> erros) {
        this.erros = erros;
    }
}
