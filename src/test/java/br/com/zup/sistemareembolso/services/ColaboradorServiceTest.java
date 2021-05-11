package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.CategoriaNaoExistenteException;
import br.com.zup.sistemareembolso.exceptions.ColaboradorNaoExistenteException;
import br.com.zup.sistemareembolso.exceptions.ColaboradorRepetidoException;
import br.com.zup.sistemareembolso.exceptions.ErroDoSistemaException;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Projeto;
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
    private Projeto projeto;


    @BeforeEach
    public void setUp() {
        this.colaborador = new Colaborador();

        colaborador.setNomeCompleto("Franklin Hanemann");
        colaborador.setCpf("061.779.129-58");
        colaborador.setSenha("1234");
        colaborador.setEmail("franklin.hanemann@zup.com.br");
        colaborador.setBanco("Banco Itau");
        colaborador.setNumeroDoBanco("123");


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
    public void testarBuscarCategoriaPeloIDcaminhoRuim(){

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
    public void testarAtualizarColaboradorCaminhoBom(){
        Optional<Colaborador> optionalColaborador = Optional.of(this.colaborador);

        Mockito.when(colaboradorRepository.save(Mockito.any(Colaborador.class))).thenReturn(this.colaborador);
        Mockito.when(colaboradorRepository.findById(Mockito.anyString())).thenReturn(optionalColaborador);

        Colaborador colaboradorAtualizado = new Colaborador();
        colaboradorAtualizado.setCpf("061.779.129-58");







/*        categoriaAtualizada.setNome("Nova categoria");

        Categoria novaCategoria = categoriaService.atualizarCategoria(categoriaAtualizada);

        Assertions.assertEquals(this.categoria, novaCategoria);*/
    }
}
