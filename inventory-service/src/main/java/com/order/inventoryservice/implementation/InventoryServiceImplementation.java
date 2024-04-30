package com.order.inventoryservice.implementation;

import com.order.inventoryservice.dto.InventoryResponse;
import com.order.inventoryservice.repository.InventoryRepository;
import com.order.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImplementation implements InventoryService {

    private final InventoryRepository inventoryRepository;
    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return (inventoryRepository.findBySkuCodeIn(skuCode)).stream()
                .map(inventory -> {
                    InventoryResponse inventoryResponse = new InventoryResponse();
                    inventoryResponse.setSkuCode(inventory.getSkuCode());
                    inventoryResponse.setIsInStock(inventory.getQuantity() > 0);

                    return inventoryResponse;
                }).toList();
    }
}
