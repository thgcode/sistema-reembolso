package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.colaborador.entrada.ColaboradorAtualizacaoParcialDTO;
import br.com.zup.sistemareembolso.dtos.colaborador.entrada.ColaboradorDTO;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.services.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/zupper/")
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Colaborador adicionarColaborador(@RequestBody @Valid ColaboradorDTO dto) {
        return colaboradorService.adicionarColaborador(dto.converterDTOparaColaborador());
    }

    @GetMapping("{cpf}/")
    @ResponseStatus(HttpStatus.OK)
    public Colaborador pesquisarPeloCpf(@PathVariable ColaboradorDTO colaboradorDTO){
        Colaborador colaborador = colaboradorDTO.converterDTOparaColaborador();
        return  colaboradorService.pesquisarColaboradorPorCpf(colaboradorDTO.getCpf());
    }

    @GetMapping("porProjeto/{id}/")
    public Iterable<Colaborador> pesquisarColaboradorPorProjeto(@PathVariable int id) {
        return colaboradorService.pesquisarColaboradorPorProjeto(id);
    }
        @DeleteMapping("{cpf}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientePeloCpf(@PathVariable String cpf){
        colaboradorService.excluirColaboradorPorCpf(cpf);
    }

    @PatchMapping("{cpf}/")
    public Colaborador atualizarColaboradorParcial(@PathVariable String cpf, @RequestBody @Valid ColaboradorAtualizacaoParcialDTO atualizacaoParcialDTO){
        Colaborador colaborador = atualizacaoParcialDTO.converterDTOParaColaborador(cpf);
        return colaboradorService.atualizarColaborador(colaborador);
    }
}
