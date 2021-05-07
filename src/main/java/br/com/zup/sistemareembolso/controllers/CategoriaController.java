package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.categoria.entrada.EntradaCategoriaDTO;
import br.com.zup.sistemareembolso.dtos.categoria.saida.SaidaCategoriaDTO;
import br.com.zup.sistemareembolso.models.Categoria;
import br.com.zup.sistemareembolso.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categorias/")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaidaCategoriaDTO cadastrarCategoria(@RequestBody @Valid EntradaCategoriaDTO entradaCategoriaDTO){
            Categoria categoria = entradaCategoriaDTO.converterDTOParaCategoria();
            Categoria objCategoria = categoriaService.salvarCategoria(categoria);
            return SaidaCategoriaDTO.converterCategoriaParaDTO(objCategoria);
    }

    @GetMapping
    public Iterable <Categoria> listarTodasCategorias() {
        return categoriaService.pesquisandoTodasCategorias();
    }

    @GetMapping("{codCategoria}/")
    public Categoria pequisarCategoriaPorCodCategoria(@PathVariable Integer codCategoria){
        return categoriaService.pesquisarCategoriaPorCodCategoria(codCategoria);
    }

    @DeleteMapping("{codCategoria}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCategoria(@PathVariable Integer codCategoria){
        categoriaService.deletarCategoria(codCategoria);
    }
}
