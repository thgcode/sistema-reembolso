package br.com.zup.sistemareembolso.repositories;

import br.com.zup.sistemareembolso.models.Localidade;
import org.springframework.data.repository.CrudRepository;

public interface LocalidadeRepository extends CrudRepository <Localidade, Integer> {
}
