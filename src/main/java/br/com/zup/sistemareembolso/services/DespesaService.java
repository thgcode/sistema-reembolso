package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.*;
import br.com.zup.sistemareembolso.models.*;
import br.com.zup.sistemareembolso.repositories.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private NotaFiscalService notaFiscalService;

    @Autowired
    private ProjetoService projetoService;

    public Despesa adicionarDespesa(Despesa despesa) {
        despesa.setStatus(Status.ENVIADO_PARA_APROVACAO);
        despesa.setDataEntrada(LocalDate.now());

        /* Verificar se existe o colaborador */
        Colaborador colaborador = colaboradorService.pesquisarColaboradorPorCpf(despesa.getColaborador().getCpf());
        despesa.setColaborador(colaborador);

        Projeto projeto = projetoService.pesquisarProjetoPeloId(despesa.getProjeto().getId());
        despesa.setProjeto(projeto);

        NotaFiscal notaDoBanco = notaFiscalService.adicionarNotaFiscal(despesa.getNotaFiscal());
        despesa.setNotaFiscal(notaDoBanco);

        return despesaRepository.save(despesa);
    }

    public Despesa buscarDespesaPeloId(int id){
        Optional<Despesa> despesaPesquisada = despesaRepository.findById(id);

        if( despesaPesquisada.isPresent() ){
            return despesaPesquisada.get();
        }

        throw new DespesaNaoEncontradaException();
    }

    public Iterable <Despesa> listarDespesas() {
        return despesaRepository.findAll();
    }

    public Iterable<Despesa> pesquisarDespesasPorColaborador(Colaborador colaborador) {
        if (colaborador == null) {
            return despesaRepository.findAll();
        }

        return despesaRepository.findAllByColaborador(colaborador);
    }

    public Despesa atualizarDespesaParcial(Despesa despesa){

        Despesa objetoDespesa = buscarDespesaPeloId(despesa.getId());

        if (!objetoDespesa.getDescricao().equals(despesa.getDescricao()) && despesa.getDescricao() != null ){
            objetoDespesa.setDescricao(despesa.getDescricao());
        }

        if (objetoDespesa.getValor() != despesa.getValor() && despesa.getValor() > 0.00){
            objetoDespesa.setValor(despesa.getValor());
        }

        if (despesa.getCategoria() != null && !objetoDespesa.getCategoria().getDescricao().equals(despesa.getCategoria().getDescricao()) && despesa.getCategoria().getDescricao() != null){
            objetoDespesa.setCategoria(despesa.getCategoria());
        }

        return despesaRepository.save(objetoDespesa);
    }

    public void validarSePodeAprovarDespesa(Despesa despesaDoBanco, Colaborador colaboradorDoBanco) {
        if (despesaDoBanco.getStatus() == Status.APROVADO) {
            throw new DespesaJaAprovadaException();
        }

        if (despesaDoBanco.getStatus().equals(Status.REPROVADO)) {
            throw new DespesaJaReprovadaException();
        }

        if (colaboradorDoBanco.getCargo().equals(Cargo.OPERACIONAL)) {
            throw new ColaboradorNaoEAprovadorException();
        }

        if (!colaboradorDoBanco.getProjeto().equals(despesaDoBanco.getProjeto())) {
            throw new ColaboradorNaoEstaNoProjetoException();
        }
    }

    public Despesa aprovarDespesa(Despesa despesa, Colaborador colaborador) {
        Despesa despesaDoBanco = buscarDespesaPeloId(despesa.getId());
        Colaborador colaboradorDoBanco = colaboradorService.pesquisarColaboradorPorCpf(colaborador.getCpf());

        validarSePodeAprovarDespesa(despesaDoBanco, colaboradorDoBanco);

        if (despesaDoBanco.getProjeto().getVerba() > despesaDoBanco.getValor()) {
            throw new VerbaDoProjetoInsuficienteException();
        }

        despesaDoBanco.setDataAprovacao(LocalDate.now());
        despesaDoBanco.setAprovador(colaboradorDoBanco);
        despesaDoBanco.setStatus(Status.APROVADO);

        return despesaRepository.save(despesaDoBanco);
    }

    public Despesa desaprovarDespesa(Despesa despesa, Colaborador colaborador) {
        Despesa despesaDoBanco = buscarDespesaPeloId(despesa.getId());
        Colaborador colaboradorDoBanco = colaboradorService.pesquisarColaboradorPorCpf(colaborador.getCpf());

        validarSePodeAprovarDespesa(despesaDoBanco, colaboradorDoBanco);

        despesaDoBanco.setDataAprovacao(LocalDate.now());
        despesa.setAprovador(colaboradorDoBanco);
        despesaDoBanco.setStatus(Status.REPROVADO);

        return despesaRepository.save(despesaDoBanco);
    }

    public Iterable <Despesa> pesquisarDespesasEmUmProjetoComOStatus(int codProjeto, Status status) {
        Projeto projeto = projetoService.pesquisarProjetoPeloId(codProjeto);

        return despesaRepository.findAllByProjetoAndStatus(projeto, status);
    }
}
