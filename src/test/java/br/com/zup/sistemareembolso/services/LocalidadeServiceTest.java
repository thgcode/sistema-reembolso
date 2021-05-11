package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.LocalidadeRepetidaException;
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
    private LocalidadeRepository localidadeRepository;

    private Localidade localidade;

    @BeforeEach
    public void setUp() {
        localidade = new Localidade();
        localidade.setNome("Pelotas");
    }

    @Test
    public void testarAdicionarLocalidadeCaminhoPositivo() {
        Mockito.when(localidadeRepository.findByNome(localidade.getNome())).thenReturn(Optional.empty());
        Mockito.when(localidadeRepository.save(localidade)).thenReturn(localidade);

        Assertions.assertSame(localidade, localidadeService.adicionarLocalidade(localidade));

        Mockito.verify(localidadeRepository, Mockito.times(1)).save(localidade);
    }

    @Test
    public void testarAdicionarLocalidadeCaminhoRuim() {
        Mockito.when(localidadeRepository.findByNome(localidade.getNome())).thenReturn(Optional.of(localidade));
        Mockito.when(localidadeRepository.save(localidade)).thenReturn(localidade);

        Assertions.assertThrows(LocalidadeRepetidaException.class, () -> {
            localidadeService.adicionarLocalidade(localidade);
        });

        Mockito.verify(localidadeRepository, Mockito.never()).save(localidade);
    }

    @Test
    public void testarListarLocalidades() {
        Iterable <Localidade> listaDeLocalidades = Arrays.asList(localidade);

        Mockito.when(localidadeRepository.findAll()).thenReturn(listaDeLocalidades);

        Assertions.assertSame(listaDeLocalidades, localidadeService.listarLocalidades());

        Mockito.verify(localidadeRepository, Mockito.times(1)).findAll();
    }
}
