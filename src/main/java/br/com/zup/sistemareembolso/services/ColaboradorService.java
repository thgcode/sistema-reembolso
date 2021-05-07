package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.ColaboradorNaoExistenteException;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.TipoDaConta;
import br.com.zup.sistemareembolso.repositories.ColaboradorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public Colaborador atualizarColaborador(Colaborador atualizarZupper){
       Colaborador colaborador = pesquisarColaboradorPorCpf(atualizarZupper.getCpf());
       if(!colaborador.getEmail().equals(atualizarZupper.getEmail()) && atualizarZupper.getEmail() != null){
           colaborador.setEmail(atualizarZupper.getEmail());
       }
      if(!colaborador.getSenha().equals(atualizarZupper.getSenha()) && atualizarZupper.getSenha() != null){
          String senhaEncoder = encoder.encode(atualizarZupper.getSenha());
          colaborador.setSenha(senhaEncoder);
       }
        if(!colaborador.getBanco().equals(atualizarZupper.getBanco()) && atualizarZupper.getBanco() != null){
            colaborador.setBanco(atualizarZupper.getBanco());
        }
        if(!colaborador.getNumeroDoBanco().equals(atualizarZupper.getNumeroDoBanco()) && atualizarZupper.getNumeroDoBanco() != null){
            colaborador.setNumeroDoBanco(atualizarZupper.getNumeroDoBanco());
        }
        if(!colaborador.getAgencia().equals(atualizarZupper.getAgencia()) && atualizarZupper.getAgencia() != null){
            colaborador.setAgencia(atualizarZupper.getAgencia());
        }
        if(colaborador.getDigitoDaAgencia() == atualizarZupper.getDigitoDaAgencia() && atualizarZupper.getDigitoDaAgencia() >0){
            colaborador.setDigitoDaAgencia(atualizarZupper.getDigitoDaAgencia());
        }
        if(!colaborador.getConta().equals(atualizarZupper.getConta()) && atualizarZupper.getConta() != null){
            colaborador.setConta(atualizarZupper.getConta());
        }
        if(colaborador.getDigitoDaConta() == atualizarZupper.getDigitoDaConta() && atualizarZupper.getDigitoDaConta() >0){
            colaborador.setDigitoDaConta(atualizarZupper.getDigitoDaConta());
        }
        return colaboradorRepository.save(colaborador);
    }
}
