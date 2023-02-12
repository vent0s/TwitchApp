package com.septim.twitchapp;

import com.septim.twitchapp.external.TwitchApiClient;
import com.septim.twitchapp.external.TwitchIdentityClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


import javax.sql.DataSource;


/*
 * ANNOTATION DESCRIPTION:
 * TO Do
 *   @Configuration:
 *       for dependency injection
 *   @Bean:
 *       for dependency injection
 * */

@Configuration
public class AppConfig {
    //BASE URL IS IMPLEMENT FROM HERE
    //Bean适合用于一个方法，如果要类来实现的话就要用component
    @Bean
    public TwitchIdentityClient twitchIdentityClient() {
        WebClient client = WebClient.builder().baseUrl("https://id.twitch.tv").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(TwitchIdentityClient.class);
    }

    @Bean
    public TwitchApiClient twitchApiClient(@Value("${twitch.client-id}") String twitchClientId) {

        WebClient client = WebClient.builder().
                baseUrl("https://api.twitch.tv").
                defaultHeader("Client-Id", twitchClientId).
                build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(TwitchApiClient.class);
    }

    @Bean
    UserDetailsManager users(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(HttpMethod.GET, "/", "/index.html", "/*.json", "/*.png", "/static/**").permitAll()
                                .requestMatchers("/hello/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/login", "/register", "/logout").permitAll()
                                .requestMatchers(HttpMethod.GET, "/recommendation", "/game","/search").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .formLogin()
                .successHandler((req, res, auth) -> res.setStatus(HttpStatus.NO_CONTENT.value()))
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT));
        return http.build();
    }

}
