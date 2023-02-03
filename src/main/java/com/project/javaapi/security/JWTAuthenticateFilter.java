package com.project.javaapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.javaapi.data.PersonData;
import com.project.javaapi.model.Person;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticateFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRATION = 32000000; // 8 hours 32000000
    public static final String TOKEN_SENHA = "b48244e9-9f80-4a52-b206-2262c5b3a574";
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticateFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            Person person = new ObjectMapper().readValue(request.getInputStream(), Person.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    person.getUsername(),
                    person.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar o usuário", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {


        User person = (User) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(person.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(TOKEN_SENHA));


        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime newDateTime = localDateTime.plusHours(8); // 8 horas para o token expirar
        DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String expiration = newDateTime.format(localDateTimeFormatter);

        response.getWriter().write(token);
        response.addHeader("Token Expiration", expiration);
        response.getWriter().flush();

    }
}
