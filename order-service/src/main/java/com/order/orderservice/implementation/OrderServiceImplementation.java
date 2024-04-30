package com.order.orderservice.implementation;


import com.order.orderservice.dto.InventoryResponse;
import com.order.orderservice.dto.OrderLineItemsDTO;
import com.order.orderservice.dto.OrderRequest;
import com.order.orderservice.model.Order;
import com.order.orderservice.model.OrderLineItems;
import com.order.orderservice.repository.OrderRepository;
import com.order.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository; //constructor based dependency injection


    private final WebClient webClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDTOList()
                .stream().map((orderLineItemsDTO) -> mapToOrderLineItemsDto(orderLineItemsDTO))
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);


        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();


        // Call inventory service , and place order is product is in stock
       InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve() //in order to retrieve the response
                .bodyToMono(InventoryResponse[].class)// in order to read the response from client we have to add this body to mono and type of the response too
                .block(); //by default webclient makes async requests, so in order to make sync requests we have to add .block

        boolean allProductInStock = Arrays.stream(inventoryResponses)
                .allMatch(inventoryResponse -> inventoryResponse.getIsInStock());
        if(allProductInStock){
            orderRepository.save(order);
        }else{
            throw  new IllegalArgumentException("Product is not in stock. Please try again!!");
        }

    }

    private OrderLineItems mapToOrderLineItemsDto(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());

        return orderLineItems;
    }
}
