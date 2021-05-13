package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.projeto.entrada.EntradaProjetoDTO;
import br.com.zup.sistemareembolso.jwt.GerenciadorJWT;
import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.models.Localidade;
import br.com.zup.sistemareembolso.models.Projeto;
import br.com.zup.sistemareembolso.repositories.ColaboradorRepository;
import br.com.zup.sistemareembolso.services.ProjetoService;
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

import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjetoControllerTest {
    @MockBean
    private ProjetoService projetoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjetoController projetoController;

    @MockBean
    private ColaboradorRepository colaboradorRepository;

    private String token;

    @Autowired
    private GerenciadorJWT gerenciadorJWT;

    private ObjectMapper objectMapper;
    private Colaborador colaborador;

    private Projeto projeto;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        colaborador = new Colaborador();
        colaborador.setCpf("388.004.140-79");
        colaborador.setNomeCompleto("Thiagão Teste");
        colaborador.setSenha("respostadetudo42");
        colaborador.setCargo(Cargo.DIRETOR);

        projeto = new Projeto();
        projeto.setNomeDoProjeto("Teste");

        Localidade localidade= new Localidade();
        localidade.setId(1);
        localidade.setNome("Teste");

        projeto.setLocalidade(localidade);

        token = "Token " + gerenciadorJWT.gerarToken(colaborador.getCpf());
    }

    @Test
    public void adicionarProjetoCaminhoBom() throws Exception {
        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(Optional.of(colaborador));
        EntradaProjetoDTO projetoDTO =  new EntradaProjetoDTO();

        projetoDTO.setNomeDoProjeto("Teste");
        projetoDTO.setCodigoDoProjeto("tst");
        projetoDTO.setIdLocalidade(1);

        String projetoJson = objectMapper.writeValueAsString(projetoDTO);

        Mockito.when(projetoService.adicionarProjeto(Mockito.any(Projeto.class), Mockito.any(Colaborador.class))).thenReturn(projeto);

        mockMvc.perform(MockMvcRequestBuilders.post("/projetos/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(projetoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void adicionarProjetoCaminhoRuimErrosDeValidacao() throws Exception {
        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(Optional.of(colaborador));
        EntradaProjetoDTO projetoDTO =  new EntradaProjetoDTO();

        String projetoJson = objectMapper.writeValueAsString(projetoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/projetos/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(projetoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    public void adicionarProjetoCaminhoRuimTestarValidacoes() throws Exception {
        EntradaProjetoDTO projetoDTO =  new EntradaProjetoDTO();

        String projetoJson = objectMapper.writeValueAsString(projetoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/projetos/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(projetoJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
