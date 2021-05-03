package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.repositories.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    public Despesa adicionarDespesa(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    public Iterable <Despesa> listarDespesas() {
        return despesaRepository.findAll();
    }
}
