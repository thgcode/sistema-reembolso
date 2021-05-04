package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.ProjetoNaoExistenteException;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.repositories.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    public Projeto adicionarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Iterable <Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public Projeto pesquisarProjetoPeloId(int id) {
        Optional <Projeto> optionalProjeto = projetoRepository.findById(id);

        if (optionalProjeto.isPresent()) {
            return optionalProjeto.get();
        }

        throw new ProjetoNaoExistenteException();
    }
}
