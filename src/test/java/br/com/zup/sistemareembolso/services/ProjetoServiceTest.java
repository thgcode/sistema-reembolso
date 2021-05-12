package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.*;
import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.repositories.ProjetoRepository;
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
public class ProjetoServiceTest {
    @Autowired
    private ProjetoService projetoService;

    @MockBean
    private ProjetoRepository projetoRepository;

    @MockBean
    private LocalidadeService localidadeService;

    @MockBean
    private ColaboradorService colaboradorService;

    private Projeto projeto;

    private Localidade localidade;

    private Colaborador diretor;

    @BeforeEach
    public void setUp() {
        projeto = new Projeto();

        projeto.setId(1);
        projeto.setNomeDoProjeto("Teste");
        projeto.setCodigoDoProjeto("tst");
        projeto.setVerba(5000);

        localidade = new Localidade();
        localidade.setCodLocalidade(1);
        localidade.setNome("Pelotas");

        projeto.setLocalidade(localidade);

        diretor = new Colaborador();
        diretor.setNomeCompleto("Thiago Seus");
        diretor.setCargo(Cargo.DIRETOR);
        diretor.setEmail("thiago.seus@zup.com.br");
    }

    @Test
    public void testarAdicionarProjetoCaminhoPositivo() {
        Mockito.when(projetoRepository.save(Mockito.any(Projeto.class))).thenReturn(projeto);
        Mockito.when(localidadeService.pesquisarLocalidadePeloCodigo(1)).thenReturn(localidade);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(diretor.getCpf())).thenReturn(diretor);

        Assertions.assertSame(projeto, projetoService.adicionarProjeto(projeto, diretor));
        Mockito.verify(projetoRepository, Mockito.times(1)).save(projeto);
    }

    @Test
    public void testarAdicionarProjetoCaminhoRuimOperacionalQuerCriarProjeto() {
        Mockito.when(projetoRepository.save(Mockito.any(Projeto.class))).thenReturn(projeto);
        Mockito.when(localidadeService.pesquisarLocalidadePeloCodigo(1)).thenReturn(localidade);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(diretor.getCpf())).thenReturn(diretor);

        diretor.setCargo(Cargo.OPERACIONAL);

        Assertions.assertThrows(PermissaoNegadaParaCriarProjetoException.class, () -> {
            projetoService.adicionarProjeto(projeto, diretor);
        });

        Mockito.verify(projetoRepository, Mockito.never()).save(projeto);
    }

    @Test
    public void testarAdicionarProjetoCaminhoRuimProjetoRepetido() {
        Mockito.when(projetoRepository.save(Mockito.any(Projeto.class))).thenReturn(projeto);
        Mockito.when(projetoRepository.findByNomeDoProjeto(projeto.getNomeDoProjeto())).thenReturn(Optional.of(projeto));
        Mockito.when(localidadeService.pesquisarLocalidadePeloCodigo(1)).thenReturn(localidade);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(diretor.getCpf())).thenReturn(diretor);

        Assertions.assertThrows(ProjetoRepetidoException.class, () -> {
            projetoService.adicionarProjeto(projeto, diretor);
        });

        Mockito.verify(projetoRepository, Mockito.never()).save(projeto);
    }

    @Test
    public void testarAdicionarProjetoCaminhoRuimProjetosComOMesmoCodigo() {
        Mockito.when(projetoRepository.save(Mockito.any(Projeto.class))).thenReturn(projeto);
        Mockito.when(projetoRepository.findByNomeDoProjeto(projeto.getNomeDoProjeto())).thenReturn(Optional.empty());
        Mockito.when(projetoRepository.findByCodigoDoProjeto(projeto.getCodigoDoProjeto())).thenReturn(Optional.of(projeto));
        Mockito.when(localidadeService.pesquisarLocalidadePeloCodigo(1)).thenReturn(localidade);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(diretor.getCpf())).thenReturn(diretor);

        Assertions.assertThrows(DoisProjetosTemOMesmoCodigoException.class, () -> {
            projetoService.adicionarProjeto(projeto, diretor);
        });

        Mockito.verify(projetoRepository, Mockito.never()).save(projeto);
    }

    @Test
    public void testarListarProjetos() {
        Iterable <Projeto> listaDeProjetos = Arrays.asList(projeto);

        Mockito.when(projetoRepository.findAll()).thenReturn(listaDeProjetos);

        Assertions.assertSame(listaDeProjetos, projetoService.listarProjetos());

        Mockito.verify(projetoRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testarPesquisarProjetoPeloIdCaminhoBom() {
        Mockito.when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.of(projeto));

        Assertions.assertSame(projeto, projetoService.pesquisarProjetoPeloId(1));

        Mockito.verify(projetoRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testarPesquisarProjetoPeloIdCaminhoRuim() {
        Mockito.when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ProjetoNaoExistenteException.class, () -> {
            projetoService.pesquisarProjetoPeloId(1);
        });

        Mockito.verify(projetoRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testarPesquisarProjetoPeloNomeCaminhoBom() {
        Mockito.when(projetoRepository.findByNomeDoProjeto(projeto.getNomeDoProjeto())).thenReturn(Optional.of(projeto));

        Assertions.assertSame(projeto, projetoService.pesquisarProjetoPeloNome("Teste"));

        Mockito.verify(projetoRepository, Mockito.times(1)).findByNomeDoProjeto("Teste");
    }

    @Test
    public void testarPesquisarProjetoPeloNomeCaminhoRuim() {
        Mockito.when(projetoRepository.findByNomeDoProjeto(projeto.getNomeDoProjeto())).thenReturn(Optional.empty());

        Assertions.assertThrows(ProjetoNaoExistenteException.class, () -> {
            projetoService.pesquisarProjetoPeloNome("Teste");
        });
    }

    @Test
    public void testarExcluirProjetoPeloIdCaminhoBom() {
        Mockito.when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.of(projeto));
        Mockito.doNothing().when(projetoRepository).delete(projeto);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(diretor.getCpf())).thenReturn(diretor);

        projetoService.excluirProjetoPeloId(projeto.getId(), diretor);

        Mockito.verify(projetoRepository, Mockito.times(1)).delete(projeto);
    }

    @Test
    public void testarExcluirProjetoPeloIdCaminhoRuimOperacionalQuerExcluirProjeto() {
        Mockito.when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.of(projeto));
        Mockito.doNothing().when(projetoRepository).delete(projeto);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(diretor.getCpf())).thenReturn(diretor);

        diretor.setCargo(Cargo.OPERACIONAL);

        Assertions.assertThrows(PermissaoNegadaParaExcluirProjetoException.class, () -> {
            projetoService.excluirProjetoPeloId(projeto.getId(), diretor);
        });

        Mockito.verify(projetoRepository, Mockito.never()).save(projeto);
    }

    @Test
    public void testarExcluirProjetoPeloIdCaminhoRuim() {
        Mockito.when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.empty());
        Mockito.doNothing().when(projetoRepository).delete(projeto);
        Mockito.when(colaboradorService.pesquisarColaboradorPorCpf(diretor.getCpf())).thenReturn(diretor);

        Assertions.assertThrows(ProjetoNaoExistenteException.class, () -> {
            projetoService.excluirProjetoPeloId(projeto.getId(), diretor);
        });

        Mockito.verify(projetoRepository, Mockito.never()).delete(projeto);
    }

    @Test
    public void testarDescontarVerbaDoProjetoCaminhoBom() {
        Mockito.when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.of(projeto));
        Mockito.when(projetoRepository.save(projeto)).thenReturn(projeto);

        Assertions.assertEquals(4950.0, projetoService.descontarValorDaDespesa(1, 50.0).getVerba());

        Mockito.verify(projetoRepository, Mockito.times(1)).save(projeto);
    }

    @Test
    public void testarDescontarVerbaDoProjetoCaminhoRuim() {
        Mockito.when(projetoRepository.findById(projeto.getId())).thenReturn(Optional.empty());
        Mockito.when(projetoRepository.save(projeto)).thenReturn(projeto);

        Assertions.assertThrows(ProjetoNaoExistenteException.class, () -> {
            projetoService.descontarValorDaDespesa(1, 50.0);
        });

        Mockito.verify(projetoRepository, Mockito.never()).save(projeto);
    }
}
