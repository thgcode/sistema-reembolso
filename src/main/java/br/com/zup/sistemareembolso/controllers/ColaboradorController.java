package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.colaborador.entrada.ColaboradorDTO;
import br.com.zup.sistemareembolso.dtos.localidade.entrada.LocalidadeDTO;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Localidade;
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

    @DeleteMapping("{cpf}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientePeloCpf(@PathVariable String cpf){
        colaboradorService.excluirColaboradorPorCpf(cpf);
    }
}
