package com.septim.twitchapp.external.model;

import com.fasterxml.jackson.annotation.JsonProperty;

//THIS ENTITY MEANT TO STORE RETURN VALUE FOR ACCESS TOKEN REQUEST
public record TwitchToken(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") long expiresIn,
        @JsonProperty("token_type") String tokenType
) {
}
