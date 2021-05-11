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

@RestController
@RequestMapping("despesas/")
public class DespesaController {

    @Autowired
    DespesaService despesaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaidaDespesaDTO cadastrarDespesa(@RequestBody @Valid EntradaDespesaDTO despesaDTO) {
        Despesa despesa = despesaDTO.converterDTOParaDespesa();
        Despesa objetoDespesa = despesaService.adicionarDespesa(despesa);

        return SaidaDespesaDTO.converterDespesaParaDTO(objetoDespesa);
    }

    @GetMapping
    public Iterable <Despesa> listarTodasDespesas() {
        return despesaService.listarDespesas();
    }

    @GetMapping("{id}/")
    public Despesa buscarDespesaPeloId(@PathVariable int id){
        return despesaService.buscarDespesaPeloId(id);
    }

    @PatchMapping("{id}/")
    public Despesa atualizarDespesaParcial(@PathVariable int id,
                                           @RequestBody @Valid AtualizaDespesaDTO
                                                 despesaDTO){
        Despesa despesa = despesaDTO.atualizarDTOParaDespesa(id);
        return despesaService.atualizarDespesaParcial(despesa);
    }

    @GetMapping("projetos/{codProjeto}/paraAprovacao")
    public Iterable <Despesa> pesquisarDespesasParaAprovacao(@PathVariable int codProjeto) {
        return despesaService.pesquisarDespesasEmUmProjetoComOStatus(codProjeto, Status.ENVIADO_PARA_APROVACAO );
    }

    @PatchMapping("{codDespesa}/aprovar/")
    public Despesa aprovarDespesa(@PathVariable int codDespesa, Authentication autenticacao) {
        ColaboradorLogin login = (ColaboradorLogin)autenticacao.getPrincipal();

        Colaborador colaborador = new Colaborador();
        System.out.println(autenticacao.toString());
        System.out.println(login);
        colaborador.setCpf(login.getCpf());

        Despesa despesa = new Despesa();
        despesa.setId(codDespesa);

        return despesaService.aprovarDespesa(despesa, colaborador);
    }

    @PatchMapping("{codDespesa}/desaprovar/")
    public Despesa desaprovarDespesa(@PathVariable int codDespesa, Authentication autenticacao) {
        ColaboradorLogin login = (ColaboradorLogin)autenticacao.getPrincipal();

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(login.getCpf());

        Despesa despesa = new Despesa();
        despesa.setId(codDespesa);

        return despesaService.desaprovarDespesa(despesa, colaborador);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirDespesa(@PathVariable int codigoDespesa) {
        despesaService.excluirDespesaPeloCodigo(codigoDespesa);
    }

    @GetMapping("porColaborador/{cpf}/")
    public Iterable <Despesa> pesquisarDespesasPorUmColaborador(@PathVariable String cpf) {
        Colaborador colaborador = new Colaborador();
        colaborador.setCpf(cpf);

        return despesaService.pesquisarDespesasPorColaborador(colaborador);
    }
}
