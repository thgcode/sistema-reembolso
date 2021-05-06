package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.DespesaNaoEncontradaException;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.models.Projeto;
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
}
