package com.septim.twitchapp.hello;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Person(
        String name,
        String company,
        @JsonProperty("home_address") Address homeAddress,
        @JsonProperty("favorite_book") Book favoriteBook
) {
}
