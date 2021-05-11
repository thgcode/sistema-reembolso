package br.com.zup.sistemareembolso.service;

import br.com.zup.sistemareembolso.exceptions.DoisProjetosTemOMesmoCodigoException;
import br.com.zup.sistemareembolso.exceptions.ProjetoRepetidoException;
import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.repositories.ProjetoRepository;
import br.com.zup.sistemareembolso.services.LocalidadeService;
import br.com.zup.sistemareembolso.services.ProjetoService;
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

    private Projeto projeto;

    private Localidade localidade;

    @BeforeEach
    public void setUp() {
        projeto = new Projeto();

        projeto.setNomeDoProjeto("Teste");
        projeto.setCodigoDoProjeto("tst");
        projeto.setVerba(5000);

        localidade = new Localidade();
        localidade.setCodLocalidade(1);
        localidade.setNome("Pelotas");

        projeto.setLocalidade(localidade);
    }

    @Test
    public void testarAdicionarProjetoCaminhoPositivo() {
        Mockito.when(projetoRepository.save(Mockito.any(Projeto.class))).thenReturn(projeto);
        Mockito.when(localidadeService.pesquisarLocalidadePeloCodigo(1)).thenReturn(localidade);

        Assertions.assertSame(projeto, projetoService.adicionarProjeto(projeto));
        Mockito.verify(projetoRepository, Mockito.times(1)).save(projeto);
    }

    @Test
    public void testarAdicionarProjetoCaminhoRuimProjetoRepetido() {
        Mockito.when(projetoRepository.save(Mockito.any(Projeto.class))).thenReturn(projeto);
        Mockito.when(projetoRepository.findByNomeDoProjeto(projeto.getNomeDoProjeto())).thenReturn(Optional.of(projeto));
        Mockito.when(localidadeService.pesquisarLocalidadePeloCodigo(1)).thenReturn(localidade);

        Assertions.assertThrows(ProjetoRepetidoException.class, () -> {
            projetoService.adicionarProjeto(projeto);
        });

        Mockito.verify(projetoRepository, Mockito.never()).save(projeto);
    }

    @Test
    public void testarAdicionarProjetoCaminhoRuimProjetosComOMesmoCodigo() {
        Mockito.when(projetoRepository.save(Mockito.any(Projeto.class))).thenReturn(projeto);
        Mockito.when(projetoRepository.findByNomeDoProjeto(projeto.getNomeDoProjeto())).thenReturn(Optional.empty());
        Mockito.when(projetoRepository.findByCodigoDoProjeto(projeto.getCodigoDoProjeto())).thenReturn(Optional.of(projeto));
        Mockito.when(localidadeService.pesquisarLocalidadePeloCodigo(1)).thenReturn(localidade);

        Assertions.assertThrows(DoisProjetosTemOMesmoCodigoException.class, () -> {
            projetoService.adicionarProjeto(projeto);
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
}
