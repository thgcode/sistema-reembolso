package br.com.zup.sistemareembolso.repositories;

import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.models.Status;
import org.springframework.data.repository.CrudRepository;

public interface DespesaRepository extends CrudRepository<Despesa, Integer> {
    Iterable <Despesa> findAllByColaborador(Colaborador colaborador);
    Iterable<Despesa> findAllByColaboradorDespesas(String cpf);
    Iterable <Despesa> findAllByProjetoAndStatus(Projeto projeto, Status status);
}
