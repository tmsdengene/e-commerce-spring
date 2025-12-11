package com.gibesystems.ecommerce.checkout;

import com.gibesystems.ecommerce.checkout.controller.CheckoutController;
import com.gibesystems.ecommerce.checkout.dto.CheckoutDTO;
import com.gibesystems.ecommerce.checkout.service.CheckoutService;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.entity.Order;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CheckoutControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CheckoutService checkoutService;

    @InjectMocks
    private CheckoutController checkoutController;

    private User user;
    private Order order;
    private CheckoutDTO dto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(checkoutController).build();
        user = new User();
        user.setId("1");
        order = new Order();
        order.setId(10L);
        dto = new CheckoutDTO();
        dto.setId(100L);
        dto.setUserId("1");
        dto.setOrderId(10L);
        dto.setPaymentMethod("CARD");
        dto.setPaymentConfirmed(false);
    }

    @Test
    void testCheckoutEndpoint() throws Exception {
        when(checkoutService.checkout(any(User.class), any(Order.class), eq("CARD"))).thenReturn(dto);
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        mockMvc.perform(post("/api/checkout/10")
                .principal(authentication)
                .param("paymentMethod", "CARD")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(jsonPath("$.orderId").value(10L))
                .andExpect(jsonPath("$.paymentMethod").value("CARD"));
    }

    @Test
    void testGetCheckoutByOrderEndpoint() throws Exception {
        when(checkoutService.getCheckoutByOrder(any(Order.class))).thenReturn(dto);
        mockMvc.perform(get("/api/checkout/order/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L));
    }

    @Test
    void testGetLatestCheckoutForUserEndpoint() throws Exception {
        when(checkoutService.getLatestCheckoutForUser(any(User.class))).thenReturn(dto);
        mockMvc.perform(get("/api/checkout/user/1/latest")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L));
    }
}
