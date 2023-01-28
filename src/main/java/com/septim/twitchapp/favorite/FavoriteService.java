package com.septim.twitchapp.favorite;

import com.septim.twitchapp.db.FavoriteRecordRepository;
import com.septim.twitchapp.db.ItemRepository;
import com.septim.twitchapp.db.entity.FavoriteRecordEntity;
import com.septim.twitchapp.db.entity.ItemEntity;
import com.septim.twitchapp.db.entity.UserEntity;
import com.septim.twitchapp.model.TypeGroupedItemList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class FavoriteService {
    private final ItemRepository itemRepository;
    private final FavoriteRecordRepository favoriteRecordRepository;

    public FavoriteService(ItemRepository itemRepository,
                           FavoriteRecordRepository favoriteRecordRepository) {
        this.itemRepository = itemRepository;
        this.favoriteRecordRepository = favoriteRecordRepository;
    }

    @Transactional
    public void setFavoriteItem(UserEntity user, ItemEntity item) {
        ItemEntity peristedItem = itemRepository.findByTwitchId(item.twitchId());
        if (peristedItem == null) {
            peristedItem = itemRepository.save(item);
        }
        FavoriteRecordEntity favoriteRecord = new FavoriteRecordEntity(null, user.id(), peristedItem.id(), Instant.now());
        favoriteRecordRepository.save(favoriteRecord);
    }

    public void unsetFavoriteItem(UserEntity user, String twitchId) {
        ItemEntity item = itemRepository.findByTwitchId(twitchId);
        if (item != null) {
            favoriteRecordRepository.delete(user.id(), item.id());
        }
    }

    public List<ItemEntity> getFavoriteItems(UserEntity user) {
        List<Long> favoriteItemIds = favoriteRecordRepository.findFavoriteItemIdsByUserId(user.id());
        return itemRepository.findAllById(favoriteItemIds);
    }

    public TypeGroupedItemList getGroupedFavoriteItems(UserEntity user) {
        List<ItemEntity> items = getFavoriteItems(user);
        return new TypeGroupedItemList(items);
    }
}
