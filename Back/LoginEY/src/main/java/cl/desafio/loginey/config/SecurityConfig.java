package cl.desafio.loginey.config;

import cl.desafio.loginey.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;


  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    http
        .csrf(
//            csrf ->
//            csrf.ignoringAntMatchers("/h2-console/**")
//                .ignoringAntMatchers("/api-docs.html")
//                .ignoringAntMatchers("/swagger-ui/**")
//            .disable()
        )
        .disable()
        .authorizeHttpRequests()
        .antMatchers("/login/**").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .antMatchers("/swagger-ui/**").permitAll()
        .antMatchers("/api-docs.html").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .headers(h -> h.frameOptions().sameOrigin())
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
    ;
    return http.build();
  }
}
