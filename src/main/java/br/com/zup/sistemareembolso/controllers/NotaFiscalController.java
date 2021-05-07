package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.notafiscal.saida.NotaFiscalSaidaDTO;
import br.com.zup.sistemareembolso.models.NotaFiscal;
import br.com.zup.sistemareembolso.services.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notasfiscais/")
public class NotaFiscalController {
    @Autowired
    private NotaFiscalService notaFiscalService;

    @GetMapping("{codigoDaNota}/")
    public NotaFiscalSaidaDTO pesquisarNotaFiscalPeloCodigo(@PathVariable int codigoDaNota) {
        NotaFiscal notaFiscal = notaFiscalService.pesquisarNotaFiscal(codigoDaNota);
        double valor = notaFiscalService.calcularValorDaNotaPeloId(codigoDaNota);

        return NotaFiscalSaidaDTO.converterDTODeNotaFiscalEValor(notaFiscal, valor);
    }
}
