package com.septim.twitchapp.model;

import com.septim.twitchapp.db.entity.ItemEntity;

public record FavoriteRequestBody(ItemEntity favorite) {
}
