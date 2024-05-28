package gm.servicedesk.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.NullRequestCache;

import gm.servicedesk.service.UserAuthService;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
@EnableScheduling
public class AppConfig {
    private final UserAuthService userAuthService;

    public AppConfig(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable requestCache to force spring not create sessions for anonymous users,
        // otherwise many requests from them leads to OOM
        return http
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/register/*").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/staff/**").hasAnyAuthority("ADMIN", "STAFF")
                        .requestMatchers("/customer/admin/**").hasAnyAuthority("CUSTOMER_ORG_ADMIN")
                        .requestMatchers("/customer/**").hasAnyAuthority("CUSTOMER", "CUSTOMER_ORG_ADMIN")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .userDetailsService(userAuthService)
                .oauth2Login(o -> o.userInfoEndpoint(uie -> uie.userService(userAuthService)))
                .requestCache(rc -> rc.requestCache(new NullRequestCache()))
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
