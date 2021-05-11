package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.localidade.entrada.EntradaLocalidadeDTO;
import br.com.zup.sistemareembolso.dtos.localidade.saida.SaidaLocalidadeDTO;
import br.com.zup.sistemareembolso.jwt.ColaboradorLogin;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.services.LocalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("localidades/")
public class LocalidadeController {
    @Autowired
    private LocalidadeService localidadeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaidaLocalidadeDTO adicionarLocalidade(@RequestBody @Valid EntradaLocalidadeDTO entradaLocalidadeDTO, Authentication autenticacao) {
        ColaboradorLogin login = (ColaboradorLogin)autenticacao.getPrincipal();

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(login.getCpf());

        Localidade localidade = entradaLocalidadeDTO.converterDTOParaLocalidade();
        Localidade objetoLocalidade = localidadeService.adicionarLocalidade(localidade, colaborador);
        return SaidaLocalidadeDTO.converterLocalidadeParaDTO(localidade);
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
