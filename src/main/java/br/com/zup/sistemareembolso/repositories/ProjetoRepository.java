package br.com.zup.sistemareembolso.repositories;

import br.com.zup.sistemareembolso.models.Projeto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjetoRepository extends CrudRepository <Projeto, Integer> {
    boolean existsByNomeDoProjeto(String nomeDoProjeto);
    Optional <Projeto> findByCodigoDoProjeto(String codigoDoProjeto);
    Optional<Projeto> findByNomeDoProjeto(String nomeDoProjeto);
}
