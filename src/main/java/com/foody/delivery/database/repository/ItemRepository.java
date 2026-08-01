package com.foody.delivery.database.repository;

import com.foody.delivery.domain.item.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, String> {
}
