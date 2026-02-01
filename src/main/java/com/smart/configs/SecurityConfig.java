package com.smart.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/register", "/about", "/",
                                "/css/**", "/js/**", "/images/**","/templates/**", "/static/**").permitAll()
                        .requestMatchers("/users/**").authenticated()  // Require auth for user pages
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Only admin can access
                        .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/signin")
                                .loginProcessingUrl("/authenticate")
                                .defaultSuccessUrl("/users/dashboard", true)
                                .failureUrl("/signin?error=true")
                                .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/signin?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(Customizer.withDefaults());
        return http.build();
    }
}
