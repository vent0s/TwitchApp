package com.septim.twitchapp.external.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TwitchErrorResponse(
        String message,
        String error,
        String details
) {
}
