package com.septim.twitchapp.external;

import com.septim.twitchapp.db.FavoriteRecordRepository;
import com.septim.twitchapp.db.ItemRepository;
import com.septim.twitchapp.db.UserRepository;
import com.septim.twitchapp.db.entity.FavoriteRecordEntity;
import com.septim.twitchapp.db.entity.ItemEntity;
import com.septim.twitchapp.db.entity.UserEntity;
import com.septim.twitchapp.external.model.*;
import com.septim.twitchapp.model.ItemType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;


@Component
public class DevelopmentTester implements ApplicationRunner {


    private static final Logger logger = LoggerFactory.getLogger(DevelopmentTester.class);


    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final FavoriteRecordRepository favoriteRecordRepository;


    public DevelopmentTester(
            UserRepository userRepository,
            ItemRepository itemRepository,
            FavoriteRecordRepository favoriteRecordRepository
    ) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.favoriteRecordRepository = favoriteRecordRepository;
    }


    @Override
    public void run(ApplicationArguments args) {
        UserEntity newUser = new UserEntity(null,"user0","Foo","Bar","Password");
        userRepository.save(newUser);


        //UserEntity user1 = new UserEntity(null, "user1", "John", "Smith", "password");
        //UserEntity user2 = new UserEntity(null, "user2", "Jane", "Smith", "password");
        //UserEntity user3 = new UserEntity(null, "user3", "John", "Doe", "password");


        //userRepository.saveAll(List.of(user1, user2, user3));
/*

        ItemEntity newItem = new ItemEntity(null, "abc0", "Title0", "url0", "thumb0", "name0", "game0", ItemType.CLIP);
        itemRepository.save(newItem);


        List<ItemEntity> newItems = List.of(
                new ItemEntity(null, "abc1", "Title1", "url1", "thumb1", "name1", "game1", ItemType.VIDEO),
                new ItemEntity(null, "abc2", "Title2", "url2", "thumb2", "name2", "game2", ItemType.VIDEO),
                new ItemEntity(null, "abc3", "Title3", "url3", "thumb3", "name3", "game3", ItemType.STREAM),
                new ItemEntity(null, "abc4", "Title4", "url4", "thumb4", "name4", "game4", ItemType.STREAM)
        );
        itemRepository.saveAll(newItems);
        favoriteRecordRepository.saveAll(
                List.of(
                        new FavoriteRecordEntity(null, 1L, 1L, Instant.now()),
                        new FavoriteRecordEntity(null, 1L, 3L, Instant.now()),
                        new FavoriteRecordEntity(null, 1L, 4L, Instant.now()),
                        new FavoriteRecordEntity(null, 2L, 2L, Instant.now())
                )
        );

         */

    }
}

