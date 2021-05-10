package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.ProjetoNaoExistenteException;
import br.com.zup.sistemareembolso.exceptions.VerbaDoProjetoInsuficienteException;
import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.repositories.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private LocalidadeService localidadeService;

    public Projeto adicionarProjeto(Projeto projeto) {
        Localidade localidade = localidadeService.pesquisarLocalidadePeloCodigo(projeto.getLocalidade().getCodLocalidade());
        projeto.setLocalidade(localidade);
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

    public void excluirProjetoPeloId(int id) {
        Projeto projeto = pesquisarProjetoPeloId(id);

        projetoRepository.delete(projeto);
    }

    private void validarVerbaDoProjeto(Projeto projeto, double valor) {
        if (projeto.getVerba() < valor) {
            throw new VerbaDoProjetoInsuficienteException();
        }
    }

    public Projeto descontarValorDaDespesa(int codProjeto, double valor) {
        Projeto projeto =  pesquisarProjetoPeloId(codProjeto);

        validarVerbaDoProjeto(projeto, valor);

        projeto.setVerba(projeto.getVerba() - valor);

        return projetoRepository.save(projeto);
    }
}
