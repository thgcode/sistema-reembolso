package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.ErroDoSistemaException;
import br.com.zup.sistemareembolso.exceptions.NotaFiscalForaDaValidadeException;
import br.com.zup.sistemareembolso.exceptions.NotaFiscalNaoExistenteException;
import br.com.zup.sistemareembolso.models.Despesa;
import br.com.zup.sistemareembolso.models.NotaFiscal;
import br.com.zup.sistemareembolso.repositories.NotaFiscalRepository;
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
public class NotaFiscalServiceTest {

    @Autowired
    private NotaFiscalService notaFiscalService;

    @MockBean
    private NotaFiscalRepository notaFiscalRepository;

    @MockBean
    private DespesaService despesaService;

    private NotaFiscal notaFiscal;

    @BeforeEach
    public void setUp() {
        this.notaFiscal = new NotaFiscal();

        this.notaFiscal.setId(1);
        this.notaFiscal.setLinkDaImagem("http://imagemTeste.png");
        this.notaFiscal.setDataDeEmissao(LocalDate.now());

    }

    @Test
    public void testarValidadeNotaFiscal(){
        this.notaFiscal.setDataDeEmissao(LocalDate.now().minusDays(31));

        Assertions.assertThrows(NotaFiscalForaDaValidadeException.class, () -> {
            notaFiscalService.adicionarNotaFiscal(this.notaFiscal);
        });
    }

    @Test
    public void testarAdicionarNotaFiscal(){
        Mockito.when(notaFiscalRepository.save(Mockito.any(NotaFiscal.class))).thenReturn(this.notaFiscal);

        Assertions.assertSame(this.notaFiscal, notaFiscalService.adicionarNotaFiscal(this.notaFiscal));
        Mockito.verify(notaFiscalRepository, Mockito.times(1)).save(this.notaFiscal);
    }

    @Test
    public void testarListarTodasNotasFiscais(){
        Iterable <NotaFiscal> notasFiscais = Arrays.asList(this.notaFiscal);
        Mockito.when(notaFiscalRepository.findAll()).thenReturn(notasFiscais);

        Assertions.assertSame(notasFiscais, notaFiscalService.listarNotasFiscais());
        Mockito.verify(notaFiscalRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testarBuscarNotaFiscalPeloIDcaminhoBom(){
        Optional<NotaFiscal> optionalNotaFiscal = Optional.of(this.notaFiscal);
        Mockito.when(notaFiscalRepository.findById(Mockito.anyInt())).thenReturn(optionalNotaFiscal);

        NotaFiscal notaFiscal = notaFiscalService.pesquisarNotaFiscal(10);

        Assertions.assertSame(this.notaFiscal, notaFiscal);
        Assertions.assertEquals(notaFiscal.getId(),1);
    }

    @Test
    public void testarBuscarNotaFiscalPeloIDcaminhoRuim(){
        Optional<NotaFiscal> optionalNotaFiscal = Optional.empty();
        Mockito.when(notaFiscalRepository.findById(Mockito.anyInt())).thenReturn(optionalNotaFiscal);

        ErroDoSistemaException excecao = Assertions.assertThrows(NotaFiscalNaoExistenteException.class, () -> {
            notaFiscalService.pesquisarNotaFiscal(100);
        });

        Assertions.assertEquals(HttpStatus.PRECONDITION_FAILED, excecao.getStatus());
        Assertions.assertEquals("Nota fiscal não existente!", excecao.getMessage());
    }

    @Test
    public void testarExcluirNotaFiscalaminhoBom(){

        Mockito.when(notaFiscalRepository.findById(notaFiscal.getId())).thenReturn(Optional.of(this.notaFiscal));
        Mockito.doNothing().when(notaFiscalRepository).delete(this.notaFiscal);

        notaFiscalService.excluirNotaFiscalPeloCodigo(this.notaFiscal.getId());

        Mockito.verify(notaFiscalRepository, Mockito.times(1)).delete(this.notaFiscal);
    }

    @Test
    public void testarExcluirNotaFiscalCaminhoRuim(){

        Optional<NotaFiscal> optionalNotaFiscal = Optional.empty();
        Mockito.when(notaFiscalRepository.findById(Mockito.anyInt())).thenReturn(optionalNotaFiscal);

        ErroDoSistemaException excecao = Assertions.assertThrows(NotaFiscalNaoExistenteException.class, () -> {
            notaFiscalService.pesquisarNotaFiscal(100);
        });

        Assertions.assertEquals(HttpStatus.PRECONDITION_FAILED, excecao.getStatus());
        Assertions.assertEquals("Nota fiscal não existente!", excecao.getMessage());
    }

    @Test
    public void testarCalcularValorDaNotaFiscal(){

        Despesa despesa = new Despesa();

        despesa.setValor(55.00);

        Iterable <Despesa> despesas = Arrays.asList(despesa);

        Mockito.when(despesaService.pesquisarDespesasPeloCodigoDaNotaFiscal(1)).thenReturn(despesas);
        Mockito.when(notaFiscalRepository.findById(notaFiscal.getId())).thenReturn(Optional.of(this.notaFiscal));

        Assertions.assertEquals(55.00, notaFiscalService.calcularValorDaNotaPeloId(1));
    }
}
