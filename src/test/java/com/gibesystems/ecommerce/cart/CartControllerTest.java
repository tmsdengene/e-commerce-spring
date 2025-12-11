
package com.gibesystems.ecommerce.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.cart.CartController;
import com.gibesystems.ecommerce.cart.dto.AddCartItemRequest;
import com.gibesystems.ecommerce.cart.dto.CartDTO;
import com.gibesystems.ecommerce.cart.dto.RemoveCartItemRequest;
import com.gibesystems.ecommerce.cart.dto.UpdateCartItemRequest;
import com.gibesystems.ecommerce.cart.service.CartService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddItem() throws Exception {
        User user = new User();
        user.setId("user-1");
        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(1L);
        request.setQuantity(2);
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(1L);
        cartDTO.setTotalPrice(200);
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(cartService.addItemToCart(any(User.class), any(AddCartItemRequest.class))).thenReturn(cartDTO);
        mockMvc.perform(post("/api/v1/cart/add")
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(200));
    }

    @Test
    void testGetCart() throws Exception {
        User user = new User();
        user.setId("user-1");
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(1L);
        cartDTO.setTotalPrice(100);
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(cartService.getCartByUser(any(User.class))).thenReturn(cartDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/v1/cart")
                .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(100));
    }

    @Test
    void testUpdateItem() throws Exception {
        User user = new User();
        user.setId("user-1");
        UpdateCartItemRequest request = new UpdateCartItemRequest();
        request.setCartItemId(10L);
        request.setQuantity(5);
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(1L);
        cartDTO.setTotalPrice(500);
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(cartService.updateCartItem(any(User.class), any(UpdateCartItemRequest.class))).thenReturn(cartDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/v1/cart/update")
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(500));
    }

    @Test
    void testRemoveItem() throws Exception {
        User user = new User();
        user.setId("user-1");
        RemoveCartItemRequest request = new RemoveCartItemRequest();
        request.setCartItemId(10L);
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(1L);
        cartDTO.setTotalPrice(0);
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(cartService.removeCartItem(any(User.class), any(RemoveCartItemRequest.class))).thenReturn(cartDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/v1/cart/remove")
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(0));
    }

    @Test
    void testClearCart() throws Exception {
        User user = new User();
        user.setId("user-1");
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/v1/cart/clear")
                .principal(authentication))
                .andExpect(status().isNoContent());
    }
}
