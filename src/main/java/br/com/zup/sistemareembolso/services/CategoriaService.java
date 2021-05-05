package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.ColaboradorNaoExistenteException;
import br.com.zup.sistemareembolso.models.Categoria;
import br.com.zup.sistemareembolso.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public  Categoria  salvarCategoria ( Categoria  categoria ) {
        return categoriaRepository.save(categoria);
    }

    public  Iterable < Categoria > pesquisandoTodasCategorias () {
        return categoriaRepository.findAll();
    }

    public Categoria pesquisarCategoriaPorCodCategoria(Integer codCategoria){
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(codCategoria);
        return optionalCategoria.orElseThrow(() -> new ColaboradorNaoExistenteException() );
    }

    public  void  deletarCategoria(Integer codCategoria) {
        Categoria categoria = pesquisarCategoriaPorCodCategoria(codCategoria);
        categoriaRepository.delete(categoria);
    }
}
