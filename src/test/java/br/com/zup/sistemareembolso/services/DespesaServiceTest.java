package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.*;
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
        this.gerente.setNomeCompleto("Thiago Seus");
        this.gerente.setCpf("961.696.140-30");

        this.despesa.setColaborador(colaborador);
        this.despesa.setAprovador(gerente);

        this.projeto = new Projeto();
        this.projeto.setId(1);
        this.projeto.setVerba(5000);

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
    public void testarAdicionarDespesaCaminhoRuimColaboradorEstaAlocadoNoProjeto(){
        Projeto projetoDoColaborador = new Projeto();
        projeto.setId(2);

        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.colaborador.getCpf())).thenReturn(this.colaborador);
        Mockito.when(projetoService.pesquisarProjetoPeloId(this.projeto.getId())).thenReturn(this.projeto);
        Mockito.when(projetoService.pesquisarProjetoPeloId(projetoDoColaborador.getId())).thenReturn(projetoDoColaborador);


        colaborador.setProjeto(projetoDoColaborador);

        Assertions.assertThrows(ColaboradorNaoEstaNoProjetoException.class, () -> {
            despesaService.adicionarDespesa(this.despesa);
        });

        Mockito.verify(despesaRepository, Mockito.never()).save(this.despesa);
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

    @Test
    public void testarAprovarDespesaCaminhoBom() {
        Optional <Despesa> optionalDespesa = Optional.of(this.despesa);
        Mockito.when(despesaRepository.findById(1)).thenReturn(optionalDespesa);
        Mockito.when(despesaRepository.save(this.despesa)).thenReturn(this.despesa);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.colaborador.getCpf())).thenReturn(colaborador);
        Mockito.when(projetoService.pesquisarProjetoPeloId(1)).thenReturn(this.projeto);
        Mockito.when(projetoService.descontarValorDaDespesa(this.projeto.getId(), this.despesa.getValor())).thenReturn(projeto);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.gerente.getCpf())).thenReturn(gerente);

        despesaService.aprovarDespesa(this.despesa, this.gerente);

        Assertions.assertEquals(Status.APROVADO, despesa.getStatus());
        Mockito.verify(despesaRepository, Mockito.times(1)).save(this.despesa);
        Mockito.verify(projetoService, Mockito.times(1)).descontarValorDaDespesa(1, despesa.getValor());
    }

    @Test
    public void testarAprovarDespesaCaminhoRuimColaboradorTentaAprovarSuaPropriaDespesa() {
        Optional <Despesa> optionalDespesa = Optional.of(this.despesa);
        Mockito.when(despesaRepository.findById(1)).thenReturn(optionalDespesa);
        Mockito.when(despesaRepository.save(this.despesa)).thenReturn(this.despesa);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.colaborador.getCpf())).thenReturn(colaborador);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.gerente.getCpf())).thenReturn(gerente);

        Assertions.assertThrows(ColaboradorNaoEAprovadorException.class, () -> {
            despesaService.aprovarDespesa(this.despesa, this.colaborador);
        });

        Mockito.verify(despesaRepository, Mockito.never()).save(this.despesa);
    }

    @Test
    public void testarAprovarDespesaCaminhoRuimGerenteEDeOutroProjeto() {
        Projeto projetoDoGerente = new Projeto();
        projetoDoGerente.setId(2);
        this.gerente.setProjeto(projetoDoGerente);

        Optional <Despesa> optionalDespesa = Optional.of(this.despesa);
        Mockito.when(despesaRepository.findById(1)).thenReturn(optionalDespesa);
        Mockito.when(despesaRepository.save(this.despesa)).thenReturn(this.despesa);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.colaborador.getCpf())).thenReturn(colaborador);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.gerente.getCpf())).thenReturn(gerente);

        Assertions.assertThrows(ColaboradorNaoEstaNoProjetoException.class, () -> {
            despesaService.aprovarDespesa(this.despesa, this.gerente);
        });

        Mockito.verify(despesaRepository, Mockito.never()).save(this.despesa);
    }

    @Test
    public void testarReprovarDespesaCaminhoBom() {
        Optional <Despesa> optionalDespesa = Optional.of(this.despesa);
        Mockito.when(despesaRepository.findById(1)).thenReturn(optionalDespesa);
        Mockito.when(despesaRepository.save(this.despesa)).thenReturn(this.despesa);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.colaborador.getCpf())).thenReturn(colaborador);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.gerente.getCpf())).thenReturn(gerente);

        despesaService.reprovarDespesa(this.despesa, this.gerente);

        Assertions.assertEquals(Status.REPROVADO, despesa.getStatus());
        Assertions.assertSame(this.gerente, this.despesa.getAprovador());
        Mockito.verify(despesaRepository, Mockito.times(1)).save(this.despesa);
    }

    @Test
    public void testarReprovarDespesaCaminhoRuimColaboradorTentaReprovarSuaPropriaDespesa() {
        Optional <Despesa> optionalDespesa = Optional.of(this.despesa);
        Mockito.when(despesaRepository.findById(1)).thenReturn(optionalDespesa);
        Mockito.when(despesaRepository.save(this.despesa)).thenReturn(this.despesa);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.colaborador.getCpf())).thenReturn(colaborador);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.gerente.getCpf())).thenReturn(gerente);

        Assertions.assertThrows(ColaboradorNaoEAprovadorException.class, () -> {
            despesaService.reprovarDespesa(this.despesa, this.colaborador);
        });

        Mockito.verify(despesaRepository, Mockito.never()).save(this.despesa);
    }

    @Test
    public void testarReprovarDespesaCaminhoRuimGerenteEDeOutroProjeto() {
        Projeto projetoDoGerente = new Projeto();
        projetoDoGerente.setId(2);
        this.gerente.setProjeto(projetoDoGerente);

        Optional <Despesa> optionalDespesa = Optional.of(this.despesa);
        Mockito.when(despesaRepository.findById(1)).thenReturn(optionalDespesa);
        Mockito.when(despesaRepository.save(this.despesa)).thenReturn(this.despesa);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.colaborador.getCpf())).thenReturn(colaborador);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(this.gerente.getCpf())).thenReturn(gerente);

        Assertions.assertThrows(ColaboradorNaoEstaNoProjetoException.class, () -> {
            despesaService.reprovarDespesa(this.despesa, this.gerente);
        });

        Mockito.verify(despesaRepository, Mockito.never()).save(this.despesa);
    }

    @Test
    public void pesquisarDespesasPeloIdDoProjetoEnviadasParaAprovacao() {
        Iterable <Despesa> listaDeDespesas = Arrays.asList(this.despesa);

        Mockito.when(despesaRepository.findAllByProjetoAndStatus(this.projeto, Status.ENVIADO_PARA_APROVACAO)).thenReturn(listaDeDespesas);
        Mockito.when(projetoService.pesquisarProjetoPeloId(this.projeto.getId())).thenReturn(this.projeto);

        Assertions.assertSame(listaDeDespesas, despesaService.pesquisarDespesasEmUmProjetoComOStatus(this.projeto.getId(), Status.ENVIADO_PARA_APROVACAO));
        Mockito.verify(despesaRepository, Mockito.times(1)).findAllByProjetoAndStatus(this.projeto, Status.ENVIADO_PARA_APROVACAO);
    }

    @Test
    public void testarExcluirDespesaPeloCodigoCaminhoBom() {
        Optional <Despesa> optionalDespesa = Optional.of(this.despesa);

        Mockito.when(despesaRepository.findById(this.despesa.getId())).thenReturn(optionalDespesa);
        Mockito.doNothing().when(notaFiscalService).excluirNotaFiscalPeloCodigo(this.despesa.getNotaFiscal().getCodigoDaNota());
        Mockito.doNothing().when(despesaRepository).delete(this.despesa);

        despesaService.excluirDespesaPeloCodigo(this.despesa.getId());

        Mockito.verify(notaFiscalService, Mockito.times(1)).excluirNotaFiscalPeloCodigo(notaFiscal.getCodigoDaNota());
        Mockito.verify(despesaRepository, Mockito.times(1)).delete(this.despesa);
    }

    @Test
    public void testarExcluirDespesaPeloCodigoCaminhoRuimDespesaJaEstaComStatus() {
        Optional <Despesa> optionalDespesa = Optional.of(this.despesa);

        Mockito.when(despesaRepository.findById(this.despesa.getId())).thenReturn(optionalDespesa);
        Mockito.doNothing().when(notaFiscalService).excluirNotaFiscalPeloCodigo(this.despesa.getNotaFiscal().getCodigoDaNota());
        Mockito.doNothing().when(despesaRepository).delete(this.despesa);

        despesa.setStatus(Status.APROVADO);
        Assertions.assertThrows(DespesaJaAprovadaException.class, () -> {
            despesaService.excluirDespesaPeloCodigo(despesa.getId());
        });

        despesa.setStatus(Status.REPROVADO);
        Assertions.assertThrows(DespesaJaReprovadaException.class, () -> {
            despesaService.excluirDespesaPeloCodigo(despesa.getId());
        });

        Mockito.verify(notaFiscalService, Mockito.never()).excluirNotaFiscalPeloCodigo(notaFiscal.getCodigoDaNota());
        Mockito.verify(despesaRepository, Mockito.never()).delete(this.despesa);
    }

    @Test
    public void testarPesquisarDespesasPeloCodigoDaNotaFiscal() {
        Iterable <Despesa> listaDeDespesas = Arrays.asList(this.despesa);

        Mockito.when(despesaRepository.findAllByNotaFiscal_codigoDaNota(this.despesa.getNotaFiscal().getCodigoDaNota())).thenReturn(listaDeDespesas);

        Assertions.assertSame(listaDeDespesas, despesaService.pesquisarDespesasPeloCodigoDaNotaFiscal(this.despesa.getNotaFiscal().getCodigoDaNota()));

        Mockito.verify(despesaRepository, Mockito.times(1)).findAllByNotaFiscal_codigoDaNota(this.despesa.getNotaFiscal().getCodigoDaNota());
    }

}