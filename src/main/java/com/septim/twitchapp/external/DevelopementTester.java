package com.septim.twitchapp.external;

import com.septim.twitchapp.external.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DevelopementTester implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DevelopementTester.class);
    private final TwitchService twitchService;

    public DevelopementTester(TwitchService twitchService){
        this.twitchService = twitchService;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<Clip> clips = twitchService.getClips("21779", 2);

        logger.info(clips.toString());
    }
}
