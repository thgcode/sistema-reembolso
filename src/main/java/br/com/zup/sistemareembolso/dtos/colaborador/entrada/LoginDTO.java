package br.com.zup.sistemareembolso.dtos.colaborador.entrada;

public class LoginDTO {
    private String cpf;
    private String senha;

    public LoginDTO() {

    }

    public LoginDTO(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
