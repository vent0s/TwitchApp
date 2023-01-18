package com.septim.twitchapp.external;

import com.septim.twitchapp.external.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class TwitchService {
    final String twitchClientId;
    final String twitchSecret;
    final TwitchApiClient twitchApiClient;
    final TwitchIdentityClient twitchIdentityClient;

    TwitchToken token = null;

    /*
     * ANNOTATION DESCRIPTION:
     *
     * */

    public TwitchService(
            @Value("${twitch.client-id}") String twitchClientId,
            @Value("${twitch.secret}") String twitchSecret,
            TwitchApiClient twitchApiClient,
            TwitchIdentityClient twitchIdentityClient) {
        this.twitchClientId = twitchClientId;
        this.twitchSecret = twitchSecret;
        this.twitchApiClient = twitchApiClient;
        this.twitchIdentityClient = twitchIdentityClient;
    }

    public List<Game> getTopGames() {
        return requestWithToken(() ->
                twitchApiClient.getTopGames(bearerToken()).data()
        );
    }

    public List<Game> getGames(String name) {
        return requestWithToken(() ->
                twitchApiClient.getGames(bearerToken(), name).data()
        );
    }

    public List<Stream> getStreams(List<String> gameIds, int first) {
        return requestWithToken(() ->
                twitchApiClient.getStreams(bearerToken(), gameIds, first).data()
        );
    }

    public List<Video> getVideos(String gameId, int first) {
        return requestWithToken(() ->
                twitchApiClient.getVideos(bearerToken(), gameId, first).data()
        );
    }

    public List<Clip> getClips(String gameId, int first) {
        return requestWithToken(() ->
                twitchApiClient.getClips(bearerToken(), gameId, first).data()
        );
    }

    public List<String> getTopGameIds() {
        List<String> topGameIds = new ArrayList<>();
        for (Game game : getTopGames()) {
            topGameIds.add(game.id());
        }
        return topGameIds;
    }



    private <T> T requestWithToken(Supplier<T> requestSupplier) {
        if (token == null) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret, "client_credentials");
        }
        try {
            return requestSupplier.get();
        } catch (WebClientResponseException.Unauthorized e) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret, "client_credentials");
            return requestSupplier.get();
        }
    }


    private String bearerToken() {
        //Twitch Requires Certain Format For Their Request.
        //EXAMPLE
        /*
            curl -X GET 'https://api.twitch.tv/helix/clips?broadcaster_id=1234&first=5' \
            -H 'Authorization: Bearer 2gbdx6oar67tqtcmt49t3wpcgycthx' \
            -H 'Client-Id: uo6dggojyb8d6soh92zknwmi5ej1q2'
        * */
        //Therefore, we need to combine prefix "Bearer" before our access token
        return "Bearer " + token.accessToken();
    }
}
