package br.com.zup.sistemareembolso.dtos.colaborador.saida;

import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Colaborador;

public class SaidaColaboradorDTO {
    private String nomeCompleto;
    private String cpf;
    private String email;
    private Cargo cargo;

    public SaidaColaboradorDTO() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public static SaidaColaboradorDTO converterColaboradorParaDTO(Colaborador colaborador) {
        SaidaColaboradorDTO saidaColaboradorDTO = new SaidaColaboradorDTO();

        saidaColaboradorDTO.setNomeCompleto(colaborador.getNomeCompleto());
        saidaColaboradorDTO.setCpf(colaborador.getCpf());
        saidaColaboradorDTO.setEmail(colaborador.getEmail());
        saidaColaboradorDTO.setCargo(colaborador.getCargo());

        return saidaColaboradorDTO;
    }
}
