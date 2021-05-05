package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.localidade.entrada.LocalidadeDTO;
import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.services.LocalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("localidade/")
public class LocalidadeController {
    @Autowired
    private LocalidadeService localidadeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Localidade adicionarLocalidade(LocalidadeDTO dto) {
        return localidadeService.adicionarLocalidade(dto.converterDTOParaLocalidade());
    }

    @GetMapping
    public Iterable <Localidade> listarLocalidades() {
        return localidadeService.listarLocalidades();
    }

    @GetMapping("{codLocalidade}/")
    public Localidade pesquisarLocalidadePeloId(@PathVariable int codLocalidade) {
        return localidadeService.pesquisarLocalidadePeloCodigo(codLocalidade);
    }

    @DeleteMapping("{codLocalidade}/")
    public void excluirLocalidadePeloId(@PathVariable int codLocalidade) {
        localidadeService.excluirLocalidadePeloCodigo(codLocalidade);
    }
}