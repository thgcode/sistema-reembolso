package br.com.zup.sistemareembolso.controllers;

import br.com.zup.sistemareembolso.dtos.projeto.entrada.ProjetoDTO;
import br.com.zup.sistemareembolso.jwt.GerenciadorJWT;
import br.com.zup.sistemareembolso.models.Cargo;
import br.com.zup.sistemareembolso.models.Colaborador;
import br.com.zup.sistemareembolso.repositories.ColaboradorRepository;
import br.com.zup.sistemareembolso.services.ProjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@WebMvcTest(ProjetoController.class)
public class ProjetoControllerTest {
    @MockBean
    private ProjetoService projetoService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ProjetoController projetoController;

    private String token;

    @Autowired
    private GerenciadorJWT gerenciadorJWT;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        Colaborador colaborador = new Colaborador();
        colaborador.setCpf("388.004.140-79");
        colaborador.setNomeCompleto("Thiagão Teste");
        colaborador.setSenha("respostadetudo42");
        colaborador.setCargo(Cargo.DIRETOR);

        Mockito.when(colaboradorRepository.findById(colaborador.getCpf())).thenReturn(Optional.of(colaborador));

        token = gerenciadorJWT.gerarToken(colaborador.getCpf());
    }

    @Test
    public void adicionarProjetoCaminhoRuim() throws Exception {
        ProjetoDTO projetoDTO =  new ProjetoDTO();

        String projetoJson = objectMapper.writeValueAsString(projetoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("projetos/")
        .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(projetoJson)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
