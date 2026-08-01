package com.foody.delivery.controller;

import com.foody.delivery.domain.item.Item;
import com.foody.delivery.domain.item.ItemDTO;
import com.foody.delivery.service.ItensService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/itens")
public class ItemController {
    private ItensService itensService;

    public ItemController(ItensService itensService) {
        this.itensService = itensService;
    }

    @GetMapping
    public List<Item> index() {
        return (List<Item>) this.itensService.getAllItens();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Validated ItemDTO data) {
        this.itensService.createItem(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody @Validated ItemDTO data) {
        this.itensService.updateItem(id, data);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        this.itensService.deleteItem(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
