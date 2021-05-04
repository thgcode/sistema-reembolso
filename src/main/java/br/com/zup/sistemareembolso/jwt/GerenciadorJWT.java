package br.com.zup.sistemareembolso.jwt;

import br.com.zup.sistemareembolso.exceptions.TokenInvalidoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GerenciadorJWT {
    @Value("${jwt.segredo}")
    private String segredo;

    @Value("${jwt.tempoExpirarToken}")
    private Long tempoExpirarToken;

    public String gerarToken(String username){
        Date dataDeVencimento = new Date(System.currentTimeMillis()+tempoExpirarToken);

        String token = Jwts.builder().setSubject(username)
                .setExpiration(dataDeVencimento)
                .signWith(SignatureAlgorithm.HS512, segredo.getBytes()).compact();

        return token;
    }

    public Claims getClaims(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(segredo.getBytes()).parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception error){
            throw new TokenInvalidoException(error.getMessage());
        }
    }

    public boolean isTokenValid(String token){
        try{
            Claims claims = getClaims(token);
            String username = claims.getSubject();
            Date dataDeVencimento = claims.getExpiration();
            Date agora = new Date(System.currentTimeMillis());

            return username != null && dataDeVencimento != null && agora.before(dataDeVencimento);
        }catch (TokenInvalidoException error){
            System.out.println(error.getMessage());
            return false;
        }
    }
}
