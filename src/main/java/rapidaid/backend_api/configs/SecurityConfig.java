package rapidaid.backend_api.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rapidaid.backend_api.models.enums.Role;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/crews/**").hasRole(Role.DISPATCHER.getRole())
                                .requestMatchers(HttpMethod.POST, "/emergencies").hasAnyRole(Role.USER.getRole(), Role.DISPATCHER.getRole())
                                .requestMatchers(HttpMethod.PUT, "/emergencies/{id}").hasAnyRole(Role.USER.getRole(), Role.DISPATCHER.getRole())
                                .requestMatchers("/emergencies/**").hasRole(Role.DISPATCHER.getRole())
                                .requestMatchers("/routes/**").hasRole(Role.DISPATCHER.getRole())
                                .requestMatchers("/users/{id}").hasAnyRole(Role.USER.getRole(), Role.ADMIN.getRole())
                                .requestMatchers("/users/**").hasRole(Role.ADMIN.getRole())
                                .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
