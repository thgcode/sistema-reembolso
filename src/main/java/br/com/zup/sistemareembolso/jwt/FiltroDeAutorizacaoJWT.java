package br.com.zup.sistemareembolso.jwt;

import br.com.zup.sistemareembolso.exceptions.TokenInvalidoException;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FiltroDeAutorizacaoJWT extends BasicAuthenticationFilter {
    private GerenciadorJWT gerenciadorJWT;
    private UserDetailsService userDetailsService;

    public FiltroDeAutorizacaoJWT(AuthenticationManager authenticationManager, GerenciadorJWT gerenciadorJWT,
                                  UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.gerenciadorJWT = gerenciadorJWT;
        this.userDetailsService = userDetailsService;
    }

    private UsernamePasswordAuthenticationToken pegarAutenticacao(HttpServletRequest request, String token) {
        if(!gerenciadorJWT.isTokenValid(token)) {
            throw new TokenInvalidoException("Token inválido");
        }

        // Pega a identificação que o usuário diz ser
        Claims claims = gerenciadorJWT.getClaims(token);
        UserDetails usuario = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String autorizacao = request.getHeader("Authorization");

        if(autorizacao != null && autorizacao.startsWith("Token ")){
            try{
                UsernamePasswordAuthenticationToken auth = pegarAutenticacao(request, autorizacao.substring(6));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (TokenInvalidoException error){
                System.out.println(error.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
