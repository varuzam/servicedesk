package gm.servicedesk.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import gm.servicedesk.service.UserAuthService;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {
    private final UserAuthService userAuthService;

    public AppConfig(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/staff/**").hasAnyAuthority("ADMIN", "STAFF")
                        .requestMatchers("/customer/**").hasAnyAuthority("CUSTOMER")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .userDetailsService(userAuthService)
                .oauth2Login(o -> o.userInfoEndpoint(uie -> uie.userService(userAuthService)))
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
