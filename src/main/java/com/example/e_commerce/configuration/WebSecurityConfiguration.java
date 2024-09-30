package com.example.e_commerce.configuration;

import com.example.e_commerce.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private final JwtRequestFilter authFilter;

    @Autowired
    public WebSecurityConfiguration(JwtRequestFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean(name = "customSecurityFilterChain")
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authenticate/**", "/sign-up/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

//    @Bean
//    public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                // Xüsusi başlıqlar üçün frameOptions deaktiv edilir
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
//                // CSRF deaktiv edilir, çünki JWT istifadə olunur
//                .csrf(AbstractHttpConfigurer::disable)
//                // Tələblərə əsasən icazə parametrləri
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/authenticate", "/sign-up").permitAll() // Autentifikasiya və qeydiyyat açıqdır
//                        .requestMatchers("/api/**").authenticated() // Qalan API-lər autentifikasiya tələb edir
//                )
//                // Sessiya idarəetməsi: hər bir sorğu üçün JWT əsaslı autentifikasiya
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                // JWT filtresi UsernamePasswordAuthenticationFilter-dan əvvəl əlavə edilir
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//}



//package com.example.e_commerce.configuration;

//import com.example.e_commerce.filters.JwtRequestFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity

//public class WebSecurityConfiguration {
//
//    private final JwtRequestFilter authFilter;
//
//    @Autowired
//    public WebSecurityConfiguration(JwtRequestFilter authFilter) {
//        this.authFilter = authFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/authenticate", "/sign-up").permitAll()
//                        .requestMatchers("/api/**").authenticated()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//}


//import com.example.e_commerce.filters.JwtRequestFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//    @Configuration
//    public class WebSecurityConfiguration {

//public class WebSecurityConfiguration {
//
//    @Autowired
//    private JwtRequestFilter authFilter;
//
//    @Bean
//    public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/authenticate", "/sign-up").permitAll()
//                .and()
//                .authorizeHttpRequests().requestMatchers("/api/**")
//                .authenticated().and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .build()
//    }
//}


