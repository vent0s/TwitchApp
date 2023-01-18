package com.septim.twitchapp;

import com.septim.twitchapp.external.TwitchApiClient;
import com.septim.twitchapp.external.TwitchIdentityClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

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
}
