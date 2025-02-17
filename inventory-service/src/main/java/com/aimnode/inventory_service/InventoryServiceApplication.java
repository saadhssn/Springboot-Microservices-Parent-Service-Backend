package com.aimnode.inventory_service;

import com.aimnode.inventory_service.model.Inventory;
import com.aimnode.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
//        return args -> {
//            Inventory inventory = new Inventory();
//            inventory.setSkuCode("iphone_15");
//            inventory.setQuantity(100);
//
//            Inventory inventory1 = new Inventory();
//            inventory1.setSkuCode("iphone_15_pro");
//            inventory1.setQuantity(100);
//
//            inventoryRepository.save(inventory);
//            inventoryRepository.save(inventory1);
//        };
//    }



}