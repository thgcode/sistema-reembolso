package br.com.zup.sistemareembolso.repositories;

import br.com.zup.sistemareembolso.models.Colaborador;
import org.springframework.data.repository.CrudRepository;

public interface ColaboradorRepository extends CrudRepository<Colaborador, String> {
    Iterable <Colaborador> findAllByProjetoId(int idProjeto);

}
