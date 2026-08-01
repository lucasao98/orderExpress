package com.foody.delivery.service;

import com.foody.delivery.database.repository.ItemRepository;
import com.foody.delivery.domain.item.Item;
import com.foody.delivery.domain.item.ItemDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItensService {
    private final ItemRepository itemRepository;

    public ItensService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Iterable<Item> getAllItens() {
        return itemRepository.findAll();
    }

    public Item createItem(ItemDTO data) {
        try {
            Item newItem = new Item(data.item_name(), data.quantity_available(), data.price());

            return this.itemRepository.save(newItem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Item updateItem(String id, ItemDTO data) {
        try {
            Optional<Item> foundItem = itemRepository.findById(id);

            if(foundItem.isEmpty()) {
                throw new Exception("Item não encontrado: " + id);
            }

            Item item = foundItem.get();

            item.update(data.item_name(), data.quantity_available(), data.price());

            return this.itemRepository.save(item);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteItem(String id) {
        try {
            boolean exists = itemRepository.existsById(id);

            if (!exists) {
                throw new Exception("Item não encontrado: " + id);
            }

            this.itemRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
