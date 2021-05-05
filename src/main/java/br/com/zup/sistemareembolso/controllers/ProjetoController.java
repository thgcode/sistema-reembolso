package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.despesas.saida.SaidaDespesaDTO;
import br.com.zup.sistemareembolso.dtos.projeto.entrada.ProjetoDTO;
import br.com.zup.sistemareembolso.dtos.projeto.saida.SaidaProjetoDTO;
import br.com.zup.sistemareembolso.models.Despesa;
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
    public SaidaProjetoDTO adicionarProjeto(@Valid @RequestBody ProjetoDTO projetoDTO) {
        Projeto projeto = projetoDTO.converterDTOParaProjeto();
        Projeto objetoProduto = projetoService.adicionarProjeto(projeto);

        return SaidaProjetoDTO.converterProjetoParaDTO(objetoProduto);
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
