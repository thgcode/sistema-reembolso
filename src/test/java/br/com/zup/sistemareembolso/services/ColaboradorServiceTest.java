package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.ColaboradorNaoExistenteException;
import br.com.zup.sistemareembolso.exceptions.ColaboradorRepetidoException;
import br.com.zup.sistemareembolso.exceptions.ErroDoSistemaException;
import br.com.zup.sistemareembolso.exceptions.PermissaoNegadaParaAtualizarOsDadosException;
import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.models.TipoDaConta;
import br.com.zup.sistemareembolso.repositories.ColaboradorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class ColaboradorServiceTest {

    @Autowired
    ColaboradorService colaboradorService;

    @MockBean
    ColaboradorRepository colaboradorRepository;

    private Colaborador colaborador;
    private Colaborador diretor;
    private Projeto projeto;

    @BeforeEach
    public void setUp() {
        this.colaborador = new Colaborador();

        colaborador.setNomeCompleto("Franklin Hanemann");
        colaborador.setCpf("061.779.129-58");
        colaborador.setSenha("1234");
        colaborador.setEmail("franklin.hanemann@zup.com.br");
        colaborador.setBanco("Banco Itau");
        colaborador.setConta("Teste");
        colaborador.setTipoDaConta(TipoDaConta.CORRENTE);
        colaborador.setAgencia("Teste");
        colaborador.setNumeroDoBanco("123");
        colaborador.setCargo(Cargo.OPERACIONAL);

        diretor = new Colaborador();
        diretor.setNomeCompleto("Thiago Seus");
        diretor.setCargo(Cargo.DIRETOR);
        diretor.setEmail("thiago.seus@zup.com.br");
        diretor.setCpf("961.696.140-30");
    }

    @Test
    public void testarCadastrarColaboradorCaminhoBom(){
        Mockito.when(colaboradorRepository.save(Mockito.any(Colaborador.class))).thenReturn(this.colaborador);

        Colaborador colaborador = colaboradorService.adicionarColaborador(this.colaborador);

        Assertions.assertSame(this.colaborador, colaborador);
        Assertions.assertEquals(this.colaborador, colaborador);
    }

    @Test
    public void testarCadastrarColaboradorCaminhoRuim(){

        Optional<Colaborador> optionalColaborador = Optional.of(this.colaborador);
        Mockito.when(colaboradorRepository.findById(Mockito.anyString())).thenReturn(optionalColaborador);

        Mockito.when(colaboradorRepository.save(Mockito.any(Colaborador.class))).thenReturn(this.colaborador);

        Assertions.assertThrows(ColaboradorRepetidoException.class, () -> {
            colaboradorService.adicionarColaborador(this.colaborador);
        });

        Mockito.verify(colaboradorRepository, Mockito.never()).save(this.colaborador);
    }

    @Test
    public void testarListarTodosColaboradores(){
        Iterable <Colaborador> colaboradores = Arrays.asList(this.colaborador);
        Mockito.when(colaboradorRepository.findAll()).thenReturn(colaboradores);
        Assertions.assertSame(colaboradores, colaboradorService.listarColaboradores());
        Mockito.verify(colaboradorRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testarBuscarColaboradorPeloCPFcaminhoBom(){
        Optional<Colaborador> optionalColaborador = Optional.of(this.colaborador);
        Mockito.when(colaboradorRepository.findById(Mockito.anyString())).thenReturn(optionalColaborador);

        Colaborador colaborador = colaboradorService.pesquisarColaboradorPorCpf("061.779.129-58");

        Assertions.assertSame(this.colaborador, colaborador);
        Assertions.assertEquals(colaborador.getCpf(), this.colaborador.getCpf());
    }

    @Test
    public void testarBuscarColaboradorPeloIDcaminhoRuim(){

        Optional<Colaborador> optionalColaborador = Optional.empty();
        Mockito.when(colaboradorRepository.findById(Mockito.anyString())).thenReturn(optionalColaborador);

        ErroDoSistemaException excecao = Assertions.assertThrows(ColaboradorNaoExistenteException.class, () -> {
            colaboradorService.pesquisarColaboradorPorCpf("061.779.129-58");
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, excecao.getStatus());
        Assertions.assertEquals("Colaborador não existente!", excecao.getMessage());
    }

    @Test
    public void testarExcluirColaboradorCaminhoBom(){
        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(Optional.of(this.colaborador));
        Mockito.doNothing().when(colaboradorRepository).delete(this.colaborador);

        colaboradorService.excluirColaboradorPorCpf(colaborador.getCpf());

        Mockito.verify(colaboradorRepository, Mockito.times(1)).delete(this.colaborador);
    }

    @Test
    public void testarExcluirColaboradorCaminhoRuim(){

        Optional<Colaborador> optionalColaborador = Optional.empty();
        Mockito.when(colaboradorRepository.findById(Mockito.anyString())).thenReturn(optionalColaborador);

        ErroDoSistemaException excecao = Assertions.assertThrows(ColaboradorNaoExistenteException.class, () -> {
            colaboradorService.pesquisarColaboradorPorCpf("061.779.129-58");
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, excecao.getStatus());
        Assertions.assertEquals("Colaborador não existente!", excecao.getMessage());
    }

    @Test
    public void testarAtualizarColaboradorCaminhoBomColaboradorAtualizarASiProprio(){
        Optional<Colaborador> optionalColaborador = Optional.of(this.colaborador);

        Mockito.when(colaboradorRepository.save(Mockito.any(Colaborador.class))).thenReturn(this.colaborador);
        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(optionalColaborador);

        Colaborador colaboradorAtualizado = new Colaborador();
        colaboradorAtualizado.setCpf(colaborador.getCpf());
        colaboradorAtualizado.setEmail("teste@teste.com.br");

        colaboradorService.atualizarColaborador(colaborador, colaboradorAtualizado);

        Assertions.assertEquals("teste@teste.com.br", colaborador.getEmail());
    }

    @Test
    public void testarAtualizarColaboradorCaminhoBomDiretorAtualizaColaborador(){
        Optional<Colaborador> optionalColaborador = Optional.of(this.colaborador);
        Optional <Colaborador> optionalDiretor = optionalColaborador.of(diretor);

        Mockito.when(colaboradorRepository.save(Mockito.any(Colaborador.class))).thenReturn(this.colaborador);
        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(optionalColaborador);
        Mockito.when(colaboradorRepository.findById(diretor.getCpf())).thenReturn(optionalDiretor);

        Colaborador colaboradorAtualizado = new Colaborador();
        colaboradorAtualizado.setCpf(colaborador.getCpf());
        colaboradorAtualizado.setEmail("teste@teste.com.br");

        colaboradorService.atualizarColaborador(diretor, colaboradorAtualizado);

        Assertions.assertEquals("teste@teste.com.br", colaborador.getEmail());
    }

    @Test
    public void testarAtualizarColaboradorCaminhoRuimColaboradorQuererSerDiretor(){
        Optional<Colaborador> optionalColaborador = Optional.of(this.colaborador);

        Mockito.when(colaboradorRepository.save(Mockito.any(Colaborador.class))).thenReturn(this.colaborador);
        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(optionalColaborador);

        Colaborador colaboradorAtualizado = new Colaborador();
        colaboradorAtualizado.setCpf(colaborador.getCpf());
        colaboradorAtualizado.setCargo(Cargo.DIRETOR);

        Assertions.assertThrows(PermissaoNegadaParaAtualizarOsDadosException.class, () -> {
            colaboradorService.atualizarColaborador(colaborador, colaboradorAtualizado);
        });
    }

    @Test
    public void testarAtualizarColaboradorCaminhoRuimColaboradorSerPromovidoPeloDiretor() {
        Optional<Colaborador> optionalColaborador = Optional.of(this.colaborador);
        Optional <Colaborador> optionalDiretor = Optional.of(diretor);

        Mockito.when(colaboradorRepository.save(Mockito.any(Colaborador.class))).thenReturn(this.colaborador);
        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(optionalColaborador);
        Mockito.when(colaboradorRepository.findById(diretor.getCpf())).thenReturn(optionalDiretor);

        Colaborador colaboradorAtualizado = new Colaborador();
        colaboradorAtualizado.setCpf(colaborador.getCpf());
        colaboradorAtualizado.setCargo(Cargo.SUPERVISOR);

        colaboradorService.atualizarColaborador(diretor, colaboradorAtualizado);

        Assertions.assertEquals(Cargo.SUPERVISOR, colaboradorAtualizado.getCargo());
    }

}