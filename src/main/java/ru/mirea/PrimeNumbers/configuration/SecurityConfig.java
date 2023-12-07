package ru.mirea.PrimeNumbers.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.mirea.PrimeNumbers.security.filter.JwtFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    // Настройка политики безопасности веб-приложения
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity
                // Отключаем проверку csrf токена
                .csrf(AbstractHttpConfigurer::disable)
               // Включаем политику STATELESS – сервер не хранит пользовательские сессии
                .sessionManagement(
                       config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 )
               // Отключаем базовую HTTP авторизацию
                .httpBasic(AbstractHttpConfigurer::disable)
               // Настройка разрешений для запросов на конкретные end-point-ы
                .authorizeHttpRequests(
                    config -> config
                            .requestMatchers("/v1/prime/num/**").permitAll()
                            .requestMatchers("/v1/auth/**").permitAll()
                            .anyRequest().authenticated()
                )
               // Отключаем авторизацию через форму
               .formLogin(AbstractHttpConfigurer::disable)
               // Ставим JwtFilter ПЕРЕД фильтром UsernamePasswordAuthenticationFilter
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               // Собираем настроенные политики в объект HttpSecurity
               .build();

    }

}
