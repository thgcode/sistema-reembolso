package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.despesas.entrada.AtualizaDespesaDTO;
import br.com.zup.sistemareembolso.dtos.despesas.entrada.EntradaDespesaDTO;
import br.com.zup.sistemareembolso.dtos.despesas.saida.SaidaDespesaDTO;
import br.com.zup.sistemareembolso.jwt.ColaboradorLogin;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.models.Status;
import br.com.zup.sistemareembolso.services.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("despesas/")
public class DespesaController {

    @Autowired
    DespesaService despesaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaidaDespesaDTO cadastrarDespesa(@RequestBody @Valid EntradaDespesaDTO despesaDTO, Authentication autenticacao) {
        ColaboradorLogin login = (ColaboradorLogin)autenticacao.getPrincipal();

        Despesa despesa = despesaDTO.converterDTOParaDespesa(login.getCpf());
        Despesa objetoDespesa = despesaService.adicionarDespesa(despesa);

        return SaidaDespesaDTO.converterDespesaParaDTO(objetoDespesa);
    }

    @GetMapping
    public Iterable <SaidaDespesaDTO> listarTodasDespesas() {
        List<SaidaDespesaDTO> listaDeDespesas = new ArrayList<>();

        for (Despesa despesa: despesaService.listarDespesas()) {
            listaDeDespesas.add(SaidaDespesaDTO.converterDespesaParaDTO(despesa));
        }

        return listaDeDespesas;
    }

    @GetMapping("{id}/")
    public SaidaDespesaDTO buscarDespesaPeloId(@PathVariable int id){
        return SaidaDespesaDTO.converterDespesaParaDTO(despesaService.buscarDespesaPeloId(id));
    }

    @PatchMapping("{id}/")
    public SaidaDespesaDTO atualizarDespesaParcial(@PathVariable int id,
                                           @RequestBody @Valid AtualizaDespesaDTO
                                                 despesaDTO){
        Despesa despesa = despesaDTO.atualizarDTOParaDespesa(id);
        return SaidaDespesaDTO.converterDespesaParaDTO(despesaService.atualizarDespesaParcial(despesa));
    }

    @GetMapping("projetos/{codProjeto}/paraAprovacao")
    public Iterable <SaidaDespesaDTO> pesquisarDespesasParaAprovacao(@PathVariable int codProjeto) {
        List <SaidaDespesaDTO> listaDeDespesas = new ArrayList<>();

        for (Despesa despesa: despesaService.pesquisarDespesasEmUmProjetoComOStatus(codProjeto, Status.ENVIADO_PARA_APROVACAO)) {
            listaDeDespesas.add(SaidaDespesaDTO.converterDespesaParaDTO(despesa));
        }

        return listaDeDespesas;
    }

    @PatchMapping("{codDespesa}/aprovar/")
    public SaidaDespesaDTO aprovarDespesa(@PathVariable int codDespesa, Authentication autenticacao) {
        ColaboradorLogin login = (ColaboradorLogin)autenticacao.getPrincipal();

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(login.getCpf());

        Despesa despesa = new Despesa();
        despesa.setId(codDespesa);

        return SaidaDespesaDTO.converterDespesaParaDTO(despesaService.aprovarDespesa(despesa, colaborador));
    }

    @PatchMapping("{codDespesa}/desaprovar/")
    public SaidaDespesaDTO desaprovarDespesa(@PathVariable int codDespesa, Authentication autenticacao) {
        ColaboradorLogin login = (ColaboradorLogin)autenticacao.getPrincipal();

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(login.getCpf());

        Despesa despesa = new Despesa();
        despesa.setId(codDespesa);

        return SaidaDespesaDTO.converterDespesaParaDTO(despesaService.reprovarDespesa(despesa, colaborador));
    }

    @DeleteMapping("{codigoDespesa}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirDespesa(@PathVariable int codigoDespesa) {
        despesaService.excluirDespesaPeloCodigo(codigoDespesa);
    }

    @GetMapping("porColaborador/{cpf}/")
    public Iterable <SaidaDespesaDTO> pesquisarDespesasPorUmColaborador(@PathVariable String cpf) {
        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(cpf);

        List <SaidaDespesaDTO> listaDeDespesas = new ArrayList<>();

        for (Despesa despesa: despesaService.pesquisarDespesasPorColaborador(colaborador)) {
            listaDeDespesas.add(SaidaDespesaDTO.converterDespesaParaDTO(despesa));
        }

        return listaDeDespesas;
    }
}
