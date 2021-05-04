package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.despesas.entrada.EntradaDespesaDTO;
import br.com.zup.sistemareembolso.dtos.despesas.saida.SaidaDespesaDTO;
import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.services.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("despesas/")
public class DespesaController {

    @Autowired
    DespesaService despesaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaidaDespesaDTO cadastrarDespesa(@RequestBody @Valid EntradaDespesaDTO despesaDTO) {
        Despesa despesa = despesaDTO.converterDTOparaDespesas();
        Despesa objetoDespesa = despesaService.adicionarDespesa(despesa);

        return SaidaDespesaDTO.converterDespesaParaDTO(objetoDespesa);
    }
}

