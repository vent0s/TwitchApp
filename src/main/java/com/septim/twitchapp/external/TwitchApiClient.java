package com.septim.twitchapp.external;

import com.septim.twitchapp.external.model.ClipResponse;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/helix")
public interface TwitchApiClient {
    @GetExchange("/clips/")
    ClipResponse getClips(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("game_id")String gameId,
            @RequestParam("first") int first
    );
}
