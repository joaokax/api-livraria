package com.project.javaapi.security;

import com.project.javaapi.services.PersonServiceImpl;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JWTConfiguration extends WebSecurityConfigurerAdapter {

    private final PersonServiceImpl personService;
    private final PasswordEncoder passwordEncoder;

    public JWTConfiguration(PersonServiceImpl personService, PasswordEncoder passwordEncoder) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()

                // Autorização para ADMIN - Cadastrar e Atualizar - AUTHORS
//                .antMatchers(HttpMethod.POST, "/authors").hasRole("ROLE_ADMIN")
//                .antMatchers(HttpMethod.PUT, "/authors/**").hasRole("ROLE_ADMIN")

                // Autorização para ADMIN - Cadastrar e Atualizar - PAPERS
//                .antMatchers(HttpMethod.POST, "/papers").hasRole("ROLE_ADMIN")
//                .antMatchers(HttpMethod.PUT, "/papers/**").hasRole("ROLE_ADMIN")

                // Autorização para ADMIN - Visualizar - PERSONS
//                .antMatchers(HttpMethod.GET, "/persons/**").hasAuthority("ROLE_ADMIN")
//                .antMatchers(HttpMethod.POST, "/persons/create").hasAuthority("ROLE_ADMIN")
//                .antMatchers(HttpMethod.GET, "/persons/validate-password").hasAuthority("ROLE_ADMIN")

                // Autorização para USER e ADMIN - Visualizar - AUTHORS e PAPERS
//                .antMatchers(HttpMethod.GET, "/authors").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
//                .antMatchers(HttpMethod.GET, "/authors/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")

                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticateFilter(authenticationManager()))
                .addFilter(new JWTValidateFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .antMatchers()
//        return http.build();
//    }

}
