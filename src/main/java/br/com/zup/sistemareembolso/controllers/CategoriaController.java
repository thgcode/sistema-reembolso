package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.categoria.entrada.EntradaCategoriaDTO;
import br.com.zup.sistemareembolso.dtos.categoria.saida.SaidaCategoriaDTO;
import br.com.zup.sistemareembolso.jwt.ColaboradorLogin;
import br.com.zup.sistemareembolso.models.Categoria;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categorias/")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaidaCategoriaDTO cadastrarCategoria(@RequestBody @Valid EntradaCategoriaDTO entradaCategoriaDTO, Authentication autenticacao) {
        ColaboradorLogin login = (ColaboradorLogin)autenticacao.getPrincipal();

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(login.getCpf());

        Categoria categoria = entradaCategoriaDTO.converterDTOParaCategoria();
        Categoria objCategoria = categoriaService.salvarCategoria(categoria, colaborador);
        return SaidaCategoriaDTO.converterCategoriaParaDTO(objCategoria);
    }

    @GetMapping
    public Iterable <Categoria> listarTodasCategorias() {
        return categoriaService.listarCategorias();
    }

    @GetMapping("{codCategoria}/")
    public Categoria pesquisarCategoriaPorCodCategoria(@PathVariable Integer codCategoria){
        return categoriaService.pesquisarCategoriaPorCodCategoria(codCategoria);
    }

    @DeleteMapping("{codCategoria}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCategoria(@PathVariable Integer codCategoria, Authentication autenticacao) {
        ColaboradorLogin login = (ColaboradorLogin)autenticacao.getPrincipal();

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(login.getCpf());

        categoriaService.deletarCategoria(codCategoria, colaborador);
    }
}
