package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.colaborador.entrada.AtualizaColaboradorDTO;
import br.com.zup.sistemareembolso.dtos.colaborador.entrada.EntradaColaboradorDTO;
import br.com.zup.sistemareembolso.dtos.colaborador.saida.SaidaColaboradorDTO;
import br.com.zup.sistemareembolso.jwt.ColaboradorLogin;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.services.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("zuppers/")
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaidaColaboradorDTO adicionarColaborador(@RequestBody @Valid EntradaColaboradorDTO entradaColaboradorDTO) {
        Colaborador colaborador = entradaColaboradorDTO.converterDTOparaColaborador();
        Colaborador objetoColaborador = colaboradorService.adicionarColaborador(colaborador);

        return SaidaColaboradorDTO.converterColaboradorParaDTO(objetoColaborador);
    }

    @GetMapping("{cpf}/")
    @ResponseStatus(HttpStatus.OK)
    public SaidaColaboradorDTO pesquisarColaboradorPeloCpf(@PathVariable EntradaColaboradorDTO colaboradorDTO){
        Colaborador colaborador = colaboradorDTO.converterDTOparaColaborador();
        return SaidaColaboradorDTO.converterColaboradorParaDTO(colaboradorService.pesquisarColaboradorPorCpf(colaboradorDTO.getCpf()));
    }

    @GetMapping("porProjeto/{id}/")
    public Iterable<SaidaColaboradorDTO> pesquisarColaboradoresPorProjeto(@PathVariable int id) {
        List <SaidaColaboradorDTO> listaDeColaboradores = new ArrayList<>();

        for (Colaborador colaborador: colaboradorService.pesquisarColaboradoresPorProjeto(id)) {
            listaDeColaboradores.add(SaidaColaboradorDTO.converterColaboradorParaDTO(colaborador));
        }

        return listaDeColaboradores;
    }

    @DeleteMapping("{cpf}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientePeloCpf(@PathVariable String cpf){
        colaboradorService.excluirColaboradorPorCpf(cpf);
    }

    @PatchMapping("{cpf}/")
    public SaidaColaboradorDTO atualizarColaboradorParcial(@PathVariable String cpf, @RequestBody @Valid AtualizaColaboradorDTO atualizacaoParcialDTO, Authentication autenticacao) {
        ColaboradorLogin login = (ColaboradorLogin)autenticacao.getPrincipal();

        Colaborador colaboradorQueVaiAtualizar = new Colaborador();
        colaboradorQueVaiAtualizar.setCpf(login.getCpf());

        Colaborador colaborador = atualizacaoParcialDTO.converterDTOParaColaborador(cpf);
        return SaidaColaboradorDTO.converterColaboradorParaDTO(colaboradorService.atualizarColaborador(colaboradorQueVaiAtualizar, colaborador));
    }
}
