package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.despesas.saida.SaidaDespesaDTO;
import br.com.zup.sistemareembolso.dtos.projeto.entrada.ProjetoDTO;
import br.com.zup.sistemareembolso.dtos.projeto.saida.SaidaProjetoDTO;
import br.com.zup.sistemareembolso.jwt.ColaboradorLogin;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("projetos/")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaidaProjetoDTO adicionarProjeto(@Valid @RequestBody ProjetoDTO projetoDTO, Authentication autenticacao) {
        ColaboradorLogin login = (ColaboradorLogin)autenticacao.getPrincipal();

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(login.getCpf());

        Projeto projeto = projetoDTO.converterDTOParaProjeto();
        Projeto objetoProjeto = projetoService.adicionarProjeto(projeto, colaborador);

        return SaidaProjetoDTO.converterProjetoParaDTO(objetoProjeto);
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
    public void excluirProjetoPeloId(@PathVariable int id, Authentication autenticacao) {
        ColaboradorLogin login = (ColaboradorLogin)autenticacao.getPrincipal();

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(login.getCpf());

        projetoService.excluirProjetoPeloId(id, colaborador);
    }
}
