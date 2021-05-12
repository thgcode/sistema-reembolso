package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.ColaboradorNaoEstaNoProjetoException;
import br.com.zup.sistemareembolso.exceptions.DespesaNaoEncontradaException;
import br.com.zup.sistemareembolso.exceptions.ErroDoSistemaException;
import br.com.zup.sistemareembolso.models.*;
import br.com.zup.sistemareembolso.repositories.DespesaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class DespesaServiceTest {

    @Autowired
    DespesaService despesaService;

    @MockBean
    DespesaRepository despesaRepository;

    @MockBean
    ColaboradorService colaboradorService;

    @MockBean
    NotaFiscalService notaFiscalService;

    @MockBean
    ProjetoService projetoService;

    private Despesa despesa;
    private Colaborador colaborador;
    private Colaborador gerente;
    private Projeto projeto;
    private NotaFiscal notaFiscal;

    @BeforeEach
    public void setUp() {
        this.despesa = new Despesa();

        this.colaborador = new Colaborador();
        this.colaborador.setCargo(Cargo.OPERACIONAL);
        this.colaborador.setCpf("061.779.129-58");

        this.gerente = new Colaborador();
        this.gerente.setCargo(Cargo.GERENTE);
        this.gerente.setCpf("061.779.129-58");

        this.despesa.setColaborador(colaborador);
        this.despesa.setAprovador(gerente);

        this.projeto = new Projeto();
        this.projeto.setId(1);

        this.colaborador.setProjeto(this.projeto);
        this.gerente.setProjeto(this.projeto);

        despesa.setProjeto(projeto);

        despesa.setId(1);
        despesa.setDescricao("Alimentação");
        despesa.setValor(35.00);
        despesa.setDataEntrada(LocalDate.now());
        despesa.setDataAprovacao(LocalDate.now().minusDays(30));
        despesa.setStatus(Status.ENVIADO_PARA_APROVACAO);

        this.notaFiscal = new NotaFiscal();
        this.notaFiscal.setCodigoDaNota(1);

        despesa.setNotaFiscal(notaFiscal);
    }

    @Test
    public void testarCadastrarDespesaCaminhoBom() {

        despesa.setStatus(Status.ENVIADO_PARA_APROVACAO);
        despesa.setDataEntrada(LocalDate.now());

        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf("061.779.129-58")).thenReturn(this.colaborador);
        Mockito.when(projetoService.pesquisarProjetoPeloId(1)).thenReturn(this.projeto);

        despesa.setProjeto(this.projeto);

        if (this.despesa.getNotaFiscal().getCodigoDaNota() > 0) {
            Mockito.when(notaFiscalService.pesquisarNotaFiscal(1)).thenReturn(this.notaFiscal);
        } else {
            Mockito.when(notaFiscalService.adicionarNotaFiscal(this.despesa.getNotaFiscal())).thenReturn(this.notaFiscal);
        }

        despesa.setNotaFiscal(this.notaFiscal);

        Mockito.when(despesaRepository.save(Mockito.any(Despesa.class))).thenReturn(this.despesa);

        Assertions.assertSame(this.despesa, despesaService.adicionarDespesa(this.despesa));
        Mockito.verify(despesaRepository, Mockito.times(1)).save(this.despesa);
    }

    @Test
    public void testarSeColaboradorEstaAlocadoNoProjeto(){
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf("061.779.129-58")).thenReturn(this.colaborador);

        if (this.colaborador.getProjeto().getId() != this.colaborador.getProjeto().getId()) {

            Assertions.assertThrows(ColaboradorNaoEstaNoProjetoException.class, () -> {
                despesaService.adicionarDespesa(this.despesa);
            });

            Mockito.verify(despesaRepository, Mockito.never()).save(this.despesa);
        }
    }

    @Test
    public void testarBuscarDespesaPeloIDcaminhoBom(){
        Optional<Despesa> optionalDespesa = Optional.of(this.despesa);
        Mockito.when(despesaRepository.findById(Mockito.anyInt())).thenReturn(optionalDespesa);

        Despesa despesa= despesaService.buscarDespesaPeloId(10);

        Assertions.assertSame(this.despesa, despesa);
        Assertions.assertEquals(despesa.getId(),1);
    }

    @Test
    public void testarBuscarDespesaPeloIDcaminhoRuim(){
        Optional<Despesa> optionalDespesa = Optional.empty();
        Mockito.when(despesaRepository.findById(Mockito.anyInt())).thenReturn(optionalDespesa);

        ErroDoSistemaException excecao = Assertions.assertThrows(DespesaNaoEncontradaException.class, () -> {
            despesaService.buscarDespesaPeloId(100);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, excecao.getStatus());
        Assertions.assertEquals("Despesa não encontrada", excecao.getMessage());
    }

    @Test
    public void testarListarTodasDespesas(){
        Iterable <Despesa> despesas = Arrays.asList(this.despesa);
        Mockito.when(despesaRepository.findAll()).thenReturn(despesas);

        Assertions.assertSame(despesas, despesaService.listarDespesas());
        Mockito.verify(despesaRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testarListarDespesasPorColaborador(){
        Iterable <Despesa> despesas = Arrays.asList(this.despesa);
        Mockito.when(despesaRepository.findAll()).thenReturn(despesas);

        Assertions.assertSame(despesas, despesaService.pesquisarDespesasPorColaborador(null));
    }

    @Test
    public void testarListarDespesasPorColaboradorPassandoOcolaborador(){
        Iterable <Despesa> despesas = Arrays.asList(this.despesa);
        Mockito.when(despesaRepository.findAllByColaborador(this.colaborador)).thenReturn(despesas);

        Assertions.assertSame(despesas, despesaService.pesquisarDespesasPorColaborador(this.colaborador));
    }

    @Test
    public void testarAtualizarDespesaCaminhoBom(){
        Optional<Despesa> optionalDespesa = Optional.of(this.despesa);

        Mockito.when(despesaRepository.save(Mockito.any(Despesa.class))).thenReturn(this.despesa);
        Mockito.when(despesaRepository.findById(despesa.getId())).thenReturn(optionalDespesa);

        Despesa despesaAtualizada = new Despesa();

        despesaAtualizada.setDescricao("Despesa atualizada");
        despesaAtualizada.setValor(1000.00);
        despesaAtualizada.setCategoria(this.despesa.getCategoria());

        Despesa novaDespesa = despesaService.atualizarDespesaParcial(this.despesa);

        Assertions.assertEquals(this.despesa, novaDespesa);

    }

    @Test
    public void testarAtualizarDespesaCaminhoRuim(){
        Optional<Despesa> optionalDespesa = Optional.empty();

        Mockito.when(despesaRepository.save(Mockito.any(Despesa.class))).thenReturn(this.despesa);
        Mockito.when(despesaRepository.findById(Mockito.anyInt())).thenReturn(optionalDespesa);

        Despesa despesaAtualizada = new Despesa();

        despesaAtualizada.setDescricao("Despesa atualizada");
        despesaAtualizada.setValor(1000.00);
        despesaAtualizada.setCategoria(this.despesa.getCategoria());

        ErroDoSistemaException excecao = Assertions.assertThrows(DespesaNaoEncontradaException.class, () -> {
            despesaService.atualizarDespesaParcial(despesaAtualizada);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, excecao.getStatus());
        Assertions.assertEquals("Despesa não encontrada", excecao.getMessage());

    }

    // Testar a partir da linha 97
}



