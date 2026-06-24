package pweb2.quizz.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite o uso de anotações como @PreAuthorize nos Controllers
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desativa CSRF temporariamente para facilitar o desenvolvimento com H2
            .csrf(csrf -> csrf.disable())
            // Configura a liberação do console do H2 nos frames da página
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authorizeHttpRequests(auth -> auth
                // Libera recursos estáticos (CSS, JS, Imagens)
                .requestMatchers("/css/**", "/js/**", "/h2-console/**", "/erro").permitAll()
                // Misto de regras: Rotas de Admin exigem a Role ADMIN de forma centralizada
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Qualquer outra requisição exige que o utilizador esteja logado
                .anyRequest().authenticated()
            )
            // Configura o formulário de login customizado da aplicação
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/lobby", true)
                .permitAll()
            )
            // Configura o encerramento da sessão (Logout)
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Criptografia BCrypt exigida nos requisitos do projeto
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }
}