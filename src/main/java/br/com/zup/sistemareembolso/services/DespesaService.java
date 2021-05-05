package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.ColaboradorNaoEAprovadorException;
import br.com.zup.sistemareembolso.exceptions.ColaboradorNaoEstaNoProjetoException;
import br.com.zup.sistemareembolso.exceptions.DespesaJaAprovadaException;
import br.com.zup.sistemareembolso.exceptions.DespesaNaoEncontradaException;
import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.models.Status;
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
    private ProjetoService projetoService;


    public Despesa adicionarDespesa(Despesa despesa) {

        despesa.setDataEntrada(LocalDate.now());

        /* Verificar se existe o colaborador */
        Colaborador colaborador = colaboradorService.pesquisarColaboradorPorCpf(despesa.getColaborador().getCpf());
        despesa.setColaborador(colaborador);

        Projeto projeto = projetoService.pesquisarProjetoPeloId(despesa.getProjeto().getId());
        despesa.setProjeto(projeto);

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

    public void validarSePodeAprovarDespesa(Despesa despesaDoBanco, Colaborador colaboradorDoBanco) {
        if (despesaDoBanco.getStatus() == Status.APROVADO) {
            throw new DespesaJaAprovadaException();
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

        despesaDoBanco.setDataAprovacao(LocalDate.now());
        despesaDoBanco.setAprovador(colaboradorDoBanco);
        despesaDoBanco.setStatus(Status.APROVADO);

        return despesaRepository.save(despesaDoBanco);
    }

    public Despesa desaprovarDespesa(Despesa despesa, Colaborador colaborador) {
        Despesa despesaDoBanco = buscarDespesaPeloId(despesa.getId());
        Colaborador colaboradorDoBanco = colaboradorService.pesquisarColaboradorPorCpf(colaborador.getCpf());

        validarSePodeAprovarDespesa(despesaDoBanco, colaborador);

        despesaDoBanco.setDataAprovacao(LocalDate.now());
        despesa.setAprovador(colaboradorDoBanco);
        despesaDoBanco.setStatus(Status.NAO_APROVADO);

        return despesaRepository.save(despesaDoBanco);
    }
}
