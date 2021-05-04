package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.jwt.ColaboradorLogin;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.repositories.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColaboradorLoginService implements UserDetailsService {
    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Override
    public ColaboradorLogin loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Optional<Colaborador> optionalColaborador = colaboradorRepository.findById(cpf);

        if (optionalColaborador.isPresent()) {
            Colaborador colaborador = optionalColaborador.get();
            return new ColaboradorLogin(colaborador.getCpf(), colaborador.getEmail(), colaborador.getSenha());
        }

        throw new UsernameNotFoundException("Colaborador não encontrado");
    }
}
