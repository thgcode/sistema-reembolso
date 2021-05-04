package br.com.zup.sistemareembolso.repositories;

import br.com.zup.sistemareembolso.models.Despesa;
import org.springframework.data.repository.CrudRepository;

public interface DespesaRepository extends CrudRepository<Despesa, Integer> {
    Iterable<Despesa> findAllByColaboradorDespesas(String cpf);
}
