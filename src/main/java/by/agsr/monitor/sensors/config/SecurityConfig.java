package by.agsr.monitor.sensors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/agsr/api/sensors/**").authenticated();
                    auth.requestMatchers(HttpMethod.POST, "/agsr/api/sensors/**").hasRole("Administrator");
                    auth.requestMatchers(HttpMethod.PUT, "/agsr/api/sensors/**").hasRole("Administrator");
                    auth.requestMatchers(HttpMethod.DELETE, "/agsr/api/sensors/**").hasRole("Administrator");
                })
                .httpBasic(Customizer.withDefaults())
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user_password"))
                .roles("Viewer")
                .build();

        UserDetails adminDetails = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin_password"))
                .roles("Administrator")
                .build();

        return new InMemoryUserDetailsManager(userDetails, adminDetails);
    }
}
