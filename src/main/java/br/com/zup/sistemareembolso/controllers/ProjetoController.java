package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.projeto.entrada.ProjetoDTO;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("projetos/")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Projeto adicionarProjeto(@Valid @RequestBody ProjetoDTO projetoDTO) {
        return projetoService.adicionarProjeto(projetoDTO.converterDTOParaProjeto());
    }

    @GetMapping
    public Iterable <Projeto> listarProjetos() {
        return projetoService.listarProjetos();
    }

    @GetMapping("{id}/")
    public Projeto pesquisarProjetoPeloId(@PathVariable int id) {
        return projetoService.pesquisarProjetoPeloId(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirProjetoPeloId(@PathVariable int id) {
        projetoService.excluirProjetoPeloId(id);
    }
}
