package br.com.zup.sistemareembolso.repositories;

import br.com.zup.sistemareembolso.models.Localidade;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocalidadeRepository extends CrudRepository <Localidade, Integer> {
    boolean existsByNome(String nome);
    Optional<Localidade> findByNome(String nome);
}
