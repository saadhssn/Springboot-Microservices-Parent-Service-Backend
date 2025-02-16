package com.aimnode.inventory_service.controller;

import com.aimnode.inventory_service.model.Inventory;
import com.aimnode.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryService.createInventory(inventory);
        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    @GetMapping("/{skuCode}") // Fixed path variable name
    public boolean isInStock(@PathVariable String skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
