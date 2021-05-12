package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.*;
import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Categoria;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ColaboradorService colaboradorService;

    public  Categoria  salvarCategoria ( Categoria  categoria, Colaborador colaborador) {
        Colaborador colaboradorDoBanco = colaboradorService.pesquisarColaboradorPorCpf(colaborador.getCpf());

        if (!colaboradorDoBanco.getCargo().equals(Cargo.GERENTE) && !colaboradorDoBanco.getCargo().equals(Cargo.DIRETOR)) {
            throw new PermissaoNegadaParaCriarCategoriaException();
        }

        if (categoriaRepository.existsByDescricao(categoria.getDescricao())) {
            throw new CategoriaRepetidaException();
        }

        return categoriaRepository.save(categoria);
    }

    public Iterable <Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria pesquisarCategoriaPorCodCategoria(Integer codCategoria) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(codCategoria);

        if (optionalCategoria.isPresent()) {
            return optionalCategoria.get();
        }

        throw new CategoriaNaoExistenteException();
    }

    public  void  deletarCategoria(Integer codCategoria, Colaborador colaborador) {
        Colaborador colaboradorDoBanco = colaboradorService.pesquisarColaboradorPorCpf(colaborador.getCpf());

        if (!colaboradorDoBanco.getCargo().equals(Cargo.GERENTE) && !colaboradorDoBanco.getCargo().equals(Cargo.DIRETOR)) {
            throw new PermissaoNegadaParaExcluirCategoriaException();
        }

        Categoria categoria = pesquisarCategoriaPorCodCategoria(codCategoria);
        categoriaRepository.delete(categoria);
    }
}
