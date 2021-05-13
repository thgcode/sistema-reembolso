package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.categoria.entrada.EntradaCategoriaDTO;
import br.com.zup.sistemareembolso.jwt.GerenciadorJWT;
import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Categoria;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.repositories.CategoriaRepository;
import br.com.zup.sistemareembolso.repositories.ColaboradorRepository;
import br.com.zup.sistemareembolso.services.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoriaControllerTest {
    @MockBean
    private CategoriaService categoriaService;

    @MockBean
    private CategoriaRepository categoriaRepository;

    @MockBean
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaController categoriaController;

    private String token;

    @Autowired
    private GerenciadorJWT gerenciadorJWT;

    private EntradaCategoriaDTO categoriaDTO;
    private Categoria categoria;
    private Colaborador colaborador;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() {

        this.objectMapper = new ObjectMapper();

        this.colaborador = new Colaborador();
        this.colaborador.setCpf("388.004.140-79");
        this.colaborador.setNomeCompleto("Franklin Hanemann");
        this.colaborador.setSenha("1234");
        this.colaborador.setCargo(Cargo.DIRETOR);

        this.categoria = new Categoria();
        this.categoria.setDescricao("Alimentação");

        token = "Token " + gerenciadorJWT.gerarToken(colaborador.getCpf());
    }

    @Test
    public void testarAdicionarCategoriaCaminhoBom() throws Exception {
        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(Optional.of(colaborador));

        this.categoriaDTO = new EntradaCategoriaDTO();
        categoriaDTO.setDescricao("Alimentação");

        String categoriaJson = objectMapper.writeValueAsString(this.categoriaDTO);

        Mockito.when(categoriaService.salvarCategoria(Mockito.any(Categoria.class), Mockito.any(Colaborador.class))).thenReturn(this.categoria);

        mockMvc.perform(MockMvcRequestBuilders.post("/categorias/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(categoriaJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void testarAdicionarCategoriaCaminhoRuim() throws Exception {
        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(Optional.of(colaborador));
        EntradaCategoriaDTO categoriaDTO =  new EntradaCategoriaDTO();

        String categoriaJson = objectMapper.writeValueAsString(categoriaDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/categorias/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(categoriaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testarListarTodasAsCategorias() throws Exception {
        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(Optional.of(colaborador));

        String categoriaJson = objectMapper.writeValueAsString(this.categoriaDTO);

        Iterable <Categoria> categorias = Arrays.asList(this.categoria);
        Mockito.when(categoriaService.listarCategorias()).thenReturn(categorias);

        mockMvc.perform(MockMvcRequestBuilders.post("/categorias/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(categoriaJson));

    }

    @Test
    public void testarListarCategoriaPorIdCaminhoBom() throws Exception {
        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(Optional.of(colaborador));

        String categoriaJson = objectMapper.writeValueAsString(this.categoriaDTO);

        Optional<Categoria> optionalCategoria = Optional.of(this.categoria);
        Mockito.when(categoriaRepository.findById(Mockito.anyInt())).thenReturn(optionalCategoria);

        mockMvc.perform(MockMvcRequestBuilders.get("/categorias/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(categoriaJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
