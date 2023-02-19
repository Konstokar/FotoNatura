package com.example.demo.security;

import com.example.demo.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean; // Информирует Spring о том, что возвращаемый данным методом объект
// должен быть зарегистрирован, как бин.
import org.springframework.context.annotation.Configuration; // Указывает, что класс содержит методы определения @Bean
import org.springframework.security.authentication.AuthenticationManager; // Обрабатывает Authentication запрос.
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity; // Включает
// глобальную безопасность метода Spring Security, аналогичную поддержке <global-method-security> xml.
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Позволяет настраивать
// веб-безопасность для определенных HTTP-запросов
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Позволяет Spring найти и
// автоматически применить класс к глобальному WebSecurity.
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter; // Предоставляет
// удобный базовый класс для создания WebSecurityConfigurer экземпляра
import org.springframework.security.config.http.SessionCreationPolicy; // Указывает различные политики создания сеансов
// для Spring Security
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Реализация PasswordEncoder, использующая
// функцию строгого хеширования BCrypt
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Обрабатывает отправку
// формы аутентификации.

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        proxyTargetClass = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(SecurityConstants.SIGN_UP_URLS).permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER) // BeanIds содержит глобально используемые идентификаторы Bean ID по умолчанию
    // для bean-компонентов, созданных поддержкой пространства имен в Spring Security 2 (используются по умолчанию).
    // AUTHENTICATION_MANAGER - «глобальный» экземпляр AuthenticationManager, зарегистрированный элементом <authentication-manager>
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
}
