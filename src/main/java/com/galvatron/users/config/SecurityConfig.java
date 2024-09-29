package com.galvatron.users.config;

import com.galvatron.users.filters.CorsFilter;
import com.galvatron.users.filters.CsrfCookieFilter;
import com.galvatron.users.filters.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthEntryPointJwt authEntryPointJwt;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthEntryPointJwt authEntryPointJwt) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authEntryPointJwt = authEntryPointJwt;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        configureCors(http);
        configureCsrf(http);
        configureAuthorization(http);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void configureCors(HttpSecurity http) throws Exception {
        http.cors(corsConfig -> corsConfig.configurationSource(createCorsConfigurationSource()));
    }

    private CorsConfigurationSource createCorsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setExposedHeaders(Collections.singletonList("Authorization"));
            config.setMaxAge(3600L);
            return config;
        };
    }

    private void configureCsrf(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        http.csrf(csrfConfig -> csrfConfig
                .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                .ignoringRequestMatchers("/api/login")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        ).addFilterAfter(new CsrfCookieFilter(), ChannelProcessingFilter.class);
    }

    private void configureAuthorization(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPointJwt))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/login", "/error").permitAll()
                        .requestMatchers("/api/user/create", "/api/users")
                        .hasAnyRole("SUPERADMIN", "RESELLER", "SUPERDISTRIBUTOR", "DISTRIBUTOR")
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
