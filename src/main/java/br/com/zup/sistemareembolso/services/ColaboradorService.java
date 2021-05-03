package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.repositories.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColaboradorService {
    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public Colaborador adicionarColaborador(Colaborador colaborador) {
        return colaboradorRepository.save(colaborador);
    }

    public Iterable <Colaborador> listarColaboradores() {
        return colaboradorRepository.findAll();
    }
}
