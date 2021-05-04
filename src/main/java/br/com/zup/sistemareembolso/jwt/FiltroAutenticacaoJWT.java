package br.com.zup.sistemareembolso.jwt;

import br.com.zup.sistemareembolso.dtos.colaborador.entrada.LoginDTO;
import br.com.zup.sistemareembolso.exceptions.TokenInvalidoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class FiltroAutenticacaoJWT extends UsernamePasswordAuthenticationFilter {
    private GerenciadorJWT gerenciadorJWT;
    private AuthenticationManager authenticationManager;

    @Autowired
    public FiltroAutenticacaoJWT(GerenciadorJWT gerenciadorJWT, AuthenticationManager authenticationManager) {
        this.gerenciadorJWT = gerenciadorJWT;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            LoginDTO login = objectMapper.readValue(request.getInputStream(), LoginDTO.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getCpf(), login.getSenha(), new ArrayList<>());
// Autentica o usuário
            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
        } catch (IOException error){
            throw new TokenInvalidoException(error.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((ColaboradorLogin) authResult.getPrincipal()).getUsername();
        String token = gerenciadorJWT.gerarToken(username);
        response.addHeader("Authorization", "Token "+token);
    }
}
