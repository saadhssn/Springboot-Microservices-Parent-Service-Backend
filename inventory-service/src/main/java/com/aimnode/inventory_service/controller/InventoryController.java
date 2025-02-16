package com.aimnode.inventory_service.controller;

import com.aimnode.inventory_service.dto.InventoryResponse;
import com.aimnode.inventory_service.model.Inventory;
import com.aimnode.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.createInventory(inventory));
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> checkStock(@RequestParam List<String> skuCode) {
        return ResponseEntity.ok(inventoryService.isInStock(skuCode));
    }
}
