package br.com.zup.sistemareembolso.services;

import br.com.zup.sistemareembolso.exceptions.CategoriaNaoExistenteException;
import br.com.zup.sistemareembolso.exceptions.CategoriaRepetidaException;
import br.com.zup.sistemareembolso.exceptions.ErroDoSistemaException;
import br.com.zup.sistemareembolso.models.Categoria;
import br.com.zup.sistemareembolso.repositories.CategoriaRepository;
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
public class CategoriaServiceTest {

    @Autowired
    CategoriaService categoriaService;

    @MockBean
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;

    @BeforeEach
    public void setUp() {
        this.categoria = new Categoria();
        categoria.setId(1);
        categoria.setDescricao("Alimentação");
    }

    @Test
    public void testarCadastrarCategoriaCaminhoBom(){
        Mockito.when(categoriaRepository.save(Mockito.any(Categoria.class))).thenReturn(this.categoria);

        Categoria categoria = categoriaService.salvarCategoria(this.categoria);

        Assertions.assertSame(this.categoria, categoria);
        Assertions.assertEquals(this.categoria, categoria);
    }

    @Test
    public void testarCadastrarCategoriaCaminhoRuim(){

        Mockito.when(categoriaRepository.existsByDescricao(this.categoria.getDescricao())).thenReturn(true);
        Mockito.when(categoriaRepository.save(Mockito.any(Categoria.class))).thenReturn(this.categoria);

        Assertions.assertThrows(CategoriaRepetidaException.class, () -> {
            categoriaService.salvarCategoria(this.categoria);
        });

        Mockito.verify(categoriaRepository, Mockito.never()).save(this.categoria);
    }

    @Test
    public void testarListarTodasCategorias(){
        Iterable <Categoria> categorias = Arrays.asList(this.categoria);
        Mockito.when(categoriaRepository.findAll()).thenReturn(categorias);
        Assertions.assertSame(categorias, categoriaService.listarCategorias());
        Mockito.verify(categoriaRepository, Mockito.times(1)).findAll();
    }


    @Test
    public void testarBuscarCategoriaPeloIDcaminhoBom(){
        Optional<Categoria> optionalCategoria = Optional.of(this.categoria);
        Mockito.when(categoriaRepository.findById(Mockito.anyInt())).thenReturn(optionalCategoria);

        Categoria categoria = categoriaService.pesquisarCategoriaPorCodCategoria(10);

        Assertions.assertSame(this.categoria, categoria);
        Assertions.assertEquals(categoria.getId(),1);
    }

    @Test
    public void testarBuscarCategoriaPeloIDcaminhoRuim(){
        Optional<Categoria> optionalCategoria = Optional.empty();
        Mockito.when(categoriaRepository.findById(Mockito.anyInt())).thenReturn(optionalCategoria);

        ErroDoSistemaException excecao = Assertions.assertThrows(CategoriaNaoExistenteException.class, () -> {
            categoriaService.pesquisarCategoriaPorCodCategoria(100);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, excecao.getStatus());
        Assertions.assertEquals("Categoria não existente!", excecao.getMessage());
    }

    @Test
    public void testarExcluirCategoriaCaminhoBom(){

        Mockito.when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(this.categoria));
        Mockito.doNothing().when(categoriaRepository).delete(this.categoria);

        categoriaService.deletarCategoria(categoria.getId());

        Mockito.verify(categoriaRepository, Mockito.times(1)).delete(this.categoria);
    }

    @Test
    public void testarExcluirCategoriaCaminhoRuim(){

        Optional<Categoria> optionalCategoria = Optional.empty();
        Mockito.when(categoriaRepository.findById(Mockito.anyInt())).thenReturn(optionalCategoria);

        ErroDoSistemaException excecao = Assertions.assertThrows(CategoriaNaoExistenteException.class, () -> {
            categoriaService.pesquisarCategoriaPorCodCategoria(100);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, excecao.getStatus());
        Assertions.assertEquals("Categoria não existente!", excecao.getMessage());
    }
}
