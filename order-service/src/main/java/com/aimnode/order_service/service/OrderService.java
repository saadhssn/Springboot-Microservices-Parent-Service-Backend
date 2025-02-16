package com.aimnode.order_service.service;

import com.aimnode.order_service.dto.InventoryResponse;
import com.aimnode.order_service.dto.OrderLineItemsDto;
import com.aimnode.order_service.dto.OrderRequest;
import com.aimnode.order_service.model.Order;
import com.aimnode.order_service.model.OrderLineItems;
import com.aimnode.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItems(mapToOrderLineItems(orderRequest.getOrderLineItems()))
                .build();

        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if (inventoryResponses != null && allProductsInStock(skuCodes, inventoryResponses)) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("One or more products are not in stock or do not exist in inventory.");
        }
    }

    private List<OrderLineItems> mapToOrderLineItems(List<OrderLineItemsDto> dtos) {
        return dtos.stream()
                .map(dto -> OrderLineItems.builder()
                        .skuCode(dto.getSkuCode())
                        .price(dto.getPrice())
                        .quantity(dto.getQuantity())
                        .build())
                .toList();
    }

    private boolean allProductsInStock(List<String> requestedSkus, InventoryResponse[] responses) {
        // Convert response to a map for quick lookup
        Map<String, Boolean> stockMap = Arrays.stream(responses)
                .collect(Collectors.toMap(InventoryResponse::getSkuCode, InventoryResponse::isInStock));

        // Ensure every requested SKU exists in response and is in stock
        return requestedSkus.stream().allMatch(sku -> stockMap.containsKey(sku) && stockMap.get(sku));
    }

}
