package com.septim.twitchapp.db;

import com.septim.twitchapp.db.entity.ItemEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface ItemRepository extends ListCrudRepository<ItemEntity,Long> {
    ItemEntity findByTwitchId(String twitchId);
}
