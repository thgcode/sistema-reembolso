package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.*;
import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Colaborador;
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

    @Autowired
    private ColaboradorService colaboradorService;

    private Projeto validarProjeto(Projeto projeto, Colaborador colaborador) {
        if (!colaborador.getCargo().equals(Cargo.GERENTE) && !colaborador.getCargo().equals(Cargo.DIRETOR)) {
            throw new PermissaoNegadaParaCriarProjetoException();
        }

        try {
            Projeto projetoDoBanco = pesquisarProjetoPeloNome(projeto.getNomeDoProjeto());
            throw new ProjetoRepetidoException();
        } catch (ProjetoNaoExistenteException exception) {
            Projeto projetoDoBanco = pesquisarProjetoPeloCodigo(projeto.getCodigoDoProjeto());
            throw new DoisProjetosTemOMesmoCodigoException();
        }
    }

    public Projeto adicionarProjeto(Projeto projeto, Colaborador colaborador) {
        Colaborador colaboradorDoBanco = colaboradorService.pesquisarColaboradorPorCpf(colaborador.getCpf());

        try {
            return validarProjeto(projeto, colaboradorDoBanco);
        } catch (ProjetoNaoExistenteException exception) {
            Localidade localidade = localidadeService.pesquisarLocalidadePeloCodigo(projeto.getLocalidade().getId());
            projeto.setLocalidade(localidade);
            return projetoRepository.save(projeto);
        }
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

    public Projeto pesquisarProjetoPeloNome(String nome) {
        Optional <Projeto> optionalProjeto = projetoRepository.findByNomeDoProjeto(nome);

        if (optionalProjeto.isPresent()) {
            return optionalProjeto.get();
        }

        throw new ProjetoNaoExistenteException();
    }

    public void excluirProjetoPeloId(int id, Colaborador colaborador) {
        Colaborador colaboradorDoBanco = colaboradorService.pesquisarColaboradorPorCpf(colaborador.getCpf());

        if (!colaboradorDoBanco.getCargo().equals(Cargo.GERENTE) && !colaboradorDoBanco.getCargo().equals(Cargo.DIRETOR)) {
            throw new PermissaoNegadaParaExcluirProjetoException();
        }

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

    public Projeto pesquisarProjetoPeloCodigo(String codigoDoProjeto) {
        Optional <Projeto> optionalProjeto = projetoRepository.findByCodigoDoProjeto(codigoDoProjeto);

        if (optionalProjeto.isPresent()) {
            return optionalProjeto.get();
        }

        throw new ProjetoNaoExistenteException();
    }
}
