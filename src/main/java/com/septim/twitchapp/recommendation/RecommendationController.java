package com.septim.twitchapp.recommendation;

import com.septim.twitchapp.db.entity.UserEntity;
import com.septim.twitchapp.model.TypeGroupedItemList;
import com.septim.twitchapp.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final UserService userService;
    public RecommendationController(
            RecommendationService recommendationService,
            UserService userService
    ){
        this.recommendationService = recommendationService;
        this.userService = userService;
    }

    @GetMapping("/recommendation")
    TypeGroupedItemList getRecommendation(@AuthenticationPrincipal User user){
        UserEntity userEntity = user == null ? null : userService.findByUsername(user.getUsername());
        return recommendationService.recommendItems(userEntity);
    }
}
