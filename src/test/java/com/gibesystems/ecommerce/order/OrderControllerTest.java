package com.gibesystems.ecommerce.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.OrderController;
import com.gibesystems.ecommerce.order.dto.*;
import com.gibesystems.ecommerce.order.entity.OrderStatus;
import com.gibesystems.ecommerce.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateOrder() throws Exception {
        User user = new User();
        user.setId("user-1");
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCartItemIds(Collections.singletonList(1L));
        request.setShippingAddress("123 Main St");
        request.setPaymentMethod("COD");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderUuid(UUID.randomUUID().toString());
        orderDTO.setStatus(OrderStatus.PENDING);
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(orderService.createOrder(any(User.class), any(CreateOrderRequest.class))).thenReturn(orderDTO);
        mockMvc.perform(post("/api/v1/orders/create")
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderUuid("order-uuid");
        orderDTO.setStatus(OrderStatus.PENDING);
        when(orderService.getOrderByUuid("order-uuid")).thenReturn(orderDTO);
        mockMvc.perform(get("/api/v1/orders/order-uuid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderUuid").value("order-uuid"));
    }

    @Test
    void testUpdateOrderStatus() throws Exception {
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setOrderUuid("order-uuid");
        request.setStatus("SHIPPED");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderUuid("order-uuid");
        orderDTO.setStatus(OrderStatus.SHIPPED);
        when(orderService.updateOrderStatus("order-uuid", "SHIPPED")).thenReturn(orderDTO);
        mockMvc.perform(put("/api/v1/orders/update-status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SHIPPED"));
    }

    @Test
    void testCancelOrder() throws Exception {
        mockMvc.perform(delete("/api/v1/orders/cancel/order-uuid"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetOrderHistory() throws Exception {
        User user = new User();
        user.setId("user-1");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderUuid("order-uuid");
        orderDTO.setStatus(OrderStatus.PENDING);
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(orderService.getOrderHistory(any(User.class))).thenReturn(orderDTO);
        mockMvc.perform(get("/api/v1/orders/history").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderUuid").value("order-uuid"));
    }
}
