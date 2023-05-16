package kz.comics.account.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class is needed to tell Spring which config we want to use in order to make all this works
 * We created the filter, we implemented UserDetailsService, validation, updating context and so on.
 *
 * And we need to binding, because our filter not yet used -> SecurityConfig
 */

@Configuration
@EnableWebSecurity
/**
 * @Configuration & @EnableWebSecurity - needed to be together
 */
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                /** Within security, we can choose and decide what the URLs and pathways that we want to secure
                 * But of course, in any application we have white list.
                 * White list - some endpoints that not require any auth or any token, which just open
                 */
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/api/v1/mail/**", "/api/v1/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                /**
                 * and() - is used to add new configuration
                 */
                .and()
        /**
         * Configure session manager:
         * when we implemented the filter, we want a once per request filter, means every request should be authenticated.
         * This means that we should not store the authentication state or session state
         * Session should be stateless and this will help us ensure that each request should be authenticated
         */
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        /**
         * SessionCreationPolicy.STATELESS - Spring will create new session for each request
         */
                .and()
                /**
                 * Here I tell Spring which authentication provider I want to use
                 */
                .authenticationProvider(authenticationProvider)
                /**
                 * We want to execute this filter before the filter called 'username password authentication' filter
                 * Because as we remember, when we implemented JWT authentication filter, we check everything
                 * and then we set security context, we update security context holder and after that we will call 'username password authentication' filter
                 */
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
