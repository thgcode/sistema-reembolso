package br.com.zup.sistemareembolso.repositories;

import br.com.zup.sistemareembolso.models.Projeto;
import org.springframework.data.repository.CrudRepository;

public interface ProjetoRepository extends CrudRepository <Projeto, Integer> {
    boolean existsByNomeDoProjeto(String nomeDoProjeto);
}
