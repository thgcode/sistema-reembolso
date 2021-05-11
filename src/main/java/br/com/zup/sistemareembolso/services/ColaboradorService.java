package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.ColaboradorNaoExistenteException;
import br.com.zup.sistemareembolso.exceptions.ColaboradorRepetidoException;
import br.com.zup.sistemareembolso.exceptions.PermissaoNegadaParaAtualizarOsDadosException;
import br.com.zup.sistemareembolso.models.Cargo;
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
        try {
            pesquisarColaboradorPorCpf(colaborador.getCpf());
            throw new ColaboradorRepetidoException();
        } catch (ColaboradorNaoExistenteException exception) {
            String senhaEncoder = encoder.encode(colaborador.getSenha());
            colaborador.setSenha(senhaEncoder);
            colaborador.setCargo(Cargo.OPERACIONAL);
            return colaboradorRepository.save(colaborador);
        }
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

    private void validarSePodeAlterarColaborador(Colaborador colaboradorAtualizador, Colaborador colaboradorAtualizado) {
        if (!colaboradorAtualizado.getCpf().equals(colaboradorAtualizador.getCpf()) && !colaboradorAtualizador.getCargo().equals(Cargo.DIRETOR)) {
            throw new PermissaoNegadaParaAtualizarOsDadosException();
        }
    }

    public Colaborador atualizarColaborador(Colaborador colaboradorAtualizador, Colaborador colaboradorAtualizado){
        Colaborador colaboradorAtualizadorDoBanco = pesquisarColaboradorPorCpf(colaboradorAtualizador.getCpf());
        Colaborador colaborador = pesquisarColaboradorPorCpf(colaboradorAtualizado.getCpf());

        validarSePodeAlterarColaborador(colaboradorAtualizador, colaboradorAtualizado);

        if(!colaborador.getEmail().equals(colaboradorAtualizado.getEmail()) && colaboradorAtualizado.getEmail() != null){
            colaborador.setEmail(colaboradorAtualizado.getEmail());
        }

        if(!colaborador.getSenha().equals(colaboradorAtualizado.getSenha()) && colaboradorAtualizado.getSenha() != null){
            String senhaEncoder = encoder.encode(colaboradorAtualizado.getSenha());
            colaborador.setSenha(senhaEncoder);
        }

        if(!colaborador.getBanco().equals(colaboradorAtualizado.getBanco()) && colaboradorAtualizado.getBanco() != null){
            colaborador.setBanco(colaboradorAtualizado.getBanco());
        }

        if(!colaborador.getNumeroDoBanco().equals(colaboradorAtualizado.getNumeroDoBanco()) && colaboradorAtualizado.getNumeroDoBanco() != null){
            colaborador.setNumeroDoBanco(colaboradorAtualizado.getNumeroDoBanco());
        }

        if(!colaborador.getAgencia().equals(colaboradorAtualizado.getAgencia()) && colaboradorAtualizado.getAgencia() != null){
            colaborador.setAgencia(colaboradorAtualizado.getAgencia());
        }

        if(colaborador.getDigitoDaAgencia() == colaboradorAtualizado.getDigitoDaAgencia() && colaboradorAtualizado.getDigitoDaAgencia() >0){
            colaborador.setDigitoDaAgencia(colaboradorAtualizado.getDigitoDaAgencia());
        }

        if(!colaborador.getConta().equals(colaboradorAtualizado.getConta()) && colaboradorAtualizado.getConta() != null){
            colaborador.setConta(colaboradorAtualizado.getConta());
        }

        if(colaborador.getDigitoDaConta() == colaboradorAtualizado.getDigitoDaConta() && colaboradorAtualizado.getDigitoDaConta() >0){
            colaborador.setDigitoDaConta(colaboradorAtualizado.getDigitoDaConta());
        }

        if (colaboradorAtualizado.getCargo() != null && !colaborador.getCargo().equals(colaboradorAtualizado.getCargo()) && colaboradorAtualizadorDoBanco.getCpf().equals(colaboradorAtualizado.getCpf())) {
            throw new PermissaoNegadaParaAtualizarOsDadosException();
        }

        if (colaboradorAtualizado.getCargo() != null && !colaboradorAtualizado.getCargo().equals(colaborador.getCargo())) {
            colaborador.setCargo(colaboradorAtualizado.getCargo());
        }

        return colaboradorRepository.save(colaborador);
    }

    public Iterable<Colaborador> pesquisarColaboradorPorProjeto(int idProjeto){
        return colaboradorRepository.findAllByProjetoId(idProjeto);
    }
}
