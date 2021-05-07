package br.com.zup.sistemareembolso.repositories;

import br.com.zup.sistemareembolso.models.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {
    boolean existsByDescricao(String descricao);
}
