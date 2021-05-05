package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.ColaboradorNaoExistenteException;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.repositories.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColaboradorService {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public Colaborador adicionarColaborador(Colaborador colaborador) {
        String senhaEncoder = encoder.encode(colaborador.getSenha());
        colaborador.setSenha(senhaEncoder);
        return colaboradorRepository.save(colaborador);
    }

    public Iterable <Colaborador> listarColaboradores() {
        return colaboradorRepository.findAll();
    }

    public Colaborador pesquisarColaboradorPorCpf(String cpf){
        Optional<Colaborador> optionalColaborador = colaboradorRepository.findById(cpf);
        return optionalColaborador.orElseThrow( () -> new ColaboradorNaoExistenteException () );
    }

    public void excluirColaboradorPorCpf(String cpf){
        Colaborador colaborador = pesquisarColaboradorPorCpf(cpf);
        colaboradorRepository.delete(colaborador);
    }
}
