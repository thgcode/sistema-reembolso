package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.LocalidadeNaoExistenteException;
import br.com.zup.sistemareembolso.exceptions.LocalidadeRepetidaException;
import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.repositories.LocalidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocalidadeService {
    @Autowired
    private LocalidadeRepository localidadeRepository;

    public Localidade adicionarLocalidade(Localidade localidade) {
        try {
            pesquisarLocalidadePeloNome(localidade.getNome());
            throw new LocalidadeRepetidaException();
        } catch (LocalidadeNaoExistenteException exception) {
            return localidadeRepository.save(localidade);
        }
    }

    public Iterable <Localidade> listarLocalidades() {
        return localidadeRepository.findAll();
    }

    public Localidade pesquisarLocalidadePeloCodigo(int codigo) {
        Optional <Localidade> optionalLocalidade = localidadeRepository.findById(codigo);

        if (optionalLocalidade.isPresent()) {
            return optionalLocalidade.get();
        }

        throw new LocalidadeNaoExistenteException();
    }

    public  Localidade pesquisarLocalidadePeloNome(String nome) {
        Optional <Localidade> optionalLocalidade = localidadeRepository.findByNome(nome);

        if (optionalLocalidade.isPresent()) {
            return optionalLocalidade.get();
        }

        throw new LocalidadeNaoExistenteException();
    }

    public void excluirLocalidadePeloCodigo(int codLocalidade) {
Localidade localidade = pesquisarLocalidadePeloCodigo(codLocalidade);

localidadeRepository.delete(localidade);
    }
}
