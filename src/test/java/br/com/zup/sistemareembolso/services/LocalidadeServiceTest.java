package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.LocalidadeNaoExistenteException;
import br.com.zup.sistemareembolso.exceptions.LocalidadeRepetidaException;
import br.com.zup.sistemareembolso.exceptions.PermissaoNegadaParaCriarLocalidadeException;
import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.repositories.LocalidadeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class LocalidadeServiceTest {
    @Autowired
    private LocalidadeService localidadeService;

    @MockBean
    private ColaboradorService colaboradorService;

    @MockBean
    private LocalidadeRepository localidadeRepository;

    private Localidade localidade;

    private Colaborador diretor;

    @BeforeEach
    public void setUp() {
        localidade = new Localidade();
        localidade.setId(1);
        localidade.setNome("Pelotas");

        diretor = new Colaborador();
        diretor.setNomeCompleto("Thiago Seus");
        diretor.setCargo(Cargo.DIRETOR);
        diretor.setEmail("thiago.seus@zup.com.br");
        diretor.setCpf("961.696.140-30");

        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(diretor.getCpf())).thenReturn(diretor);
    }

    @Test
    public void testarAdicionarLocalidadeCaminhoPositivo() {
        Mockito.when(localidadeRepository.findByNome(localidade.getNome())).thenReturn(Optional.empty());
        Mockito.when(localidadeRepository.save(localidade)).thenReturn(localidade);

        Assertions.assertSame(localidade, localidadeService.adicionarLocalidade(localidade, diretor));

        Mockito.verify(localidadeRepository, Mockito.times(1)).save(localidade);
    }

    @Test
    public void testarAdicionarLocalidadeCaminhoRuim() {
        Mockito.when(localidadeRepository.findByNome(localidade.getNome())).thenReturn(Optional.of(localidade));
        Mockito.when(localidadeRepository.save(localidade)).thenReturn(localidade);

        Assertions.assertThrows(LocalidadeRepetidaException.class, () -> {
            localidadeService.adicionarLocalidade(localidade, diretor);
        });

        Mockito.verify(localidadeRepository, Mockito.never()).save(localidade);
    }

    public void testarAdicionarLocalidadeCaminhoRuimOperacionalQuerCriarLocalidade() {
        Mockito.when(localidadeRepository.findByNome(localidade.getNome())).thenReturn(Optional.of(localidade));
        Mockito.when(localidadeRepository.save(localidade)).thenReturn(localidade);

        diretor.setCargo(Cargo.OPERACIONAL);

        Assertions.assertThrows(PermissaoNegadaParaCriarLocalidadeException.class, () -> {
            localidadeService.adicionarLocalidade(localidade, diretor);
        });
    }

    @Test
    public void testarListarLocalidades() {
        Iterable<Localidade> listaDeLocalidades = Arrays.asList(localidade);

        Mockito.when(localidadeRepository.findAll()).thenReturn(listaDeLocalidades);

        Assertions.assertSame(listaDeLocalidades, localidadeService.listarLocalidades());

        Mockito.verify(localidadeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testarPesquisarLocalidadePeloCodigoCaminhoBom() {
        Mockito.when(localidadeRepository.findById(1)).thenReturn(Optional.of(localidade));

        Assertions.assertSame(localidade, localidadeService.pesquisarLocalidadePeloCodigo(1));

        Mockito.verify(localidadeRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testarPesquisarLocalidadePeloCodigoCaminhoRuim() {
        Mockito.when(localidadeRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(LocalidadeNaoExistenteException.class, () -> {
            localidadeService.pesquisarLocalidadePeloCodigo(1);
        });
    }

    @Test
    public void testarPesquisarLocalidadePeloNomeCaminhoBom() {
        Mockito.when(localidadeRepository.findByNome(localidade.getNome())).thenReturn(Optional.of(localidade));

        Assertions.assertSame(localidade, localidadeService.pesquisarLocalidadePeloNome(localidade.getNome()));

        Mockito.verify(localidadeRepository, Mockito.times(1)).findByNome(localidade.getNome());
    }

    @Test
    public void testarPesquisarLocalidadePeloNomeCaminhoRuim() {
        Mockito.when(localidadeRepository.findByNome(localidade.getNome())).thenReturn(Optional.empty());

        Assertions.assertThrows(LocalidadeNaoExistenteException.class, () -> {
            localidadeService.pesquisarLocalidadePeloNome(localidade.getNome());
        });
    }

    @Test
    public void testarExcluirLocalidadePeloIdCaminhoBom() {
        Mockito.when(localidadeRepository.findById(localidade.getId())).thenReturn(Optional.of(localidade));
        Mockito.doNothing().when(localidadeRepository).delete(localidade);

        localidadeService.excluirLocalidadePeloCodigo(localidade.getId());

        Mockito.verify(localidadeRepository, Mockito.times(1)).delete(localidade);
    }

    @Test
    public void testarExcluirLocalidadePeloCodigoCaminhoRuim() {
        Mockito.when(localidadeRepository.findById(localidade.getId())).thenReturn(Optional.empty());
        Mockito.doNothing().when(localidadeRepository).delete(localidade);

        Assertions.assertThrows(LocalidadeNaoExistenteException.class, () -> {
            localidadeService.excluirLocalidadePeloCodigo(localidade.getId());
        });
    }

}