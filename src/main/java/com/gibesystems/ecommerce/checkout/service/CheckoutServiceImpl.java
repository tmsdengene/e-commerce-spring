package com.gibesystems.ecommerce.checkout.service;

import com.gibesystems.ecommerce.checkout.dto.CheckoutDTO;
import com.gibesystems.ecommerce.checkout.entity.Checkout;
import com.gibesystems.ecommerce.checkout.repository.CheckoutRepository;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private CheckoutRepository checkoutRepository;

    @Override
    @Transactional
    public CheckoutDTO checkout(User user, Order order, String paymentMethod) {
        Checkout checkout = new Checkout();
        checkout.setUser(user);
        checkout.setOrder(order);
        checkout.setCheckoutTime(LocalDateTime.now());
        checkout.setPaymentMethod(paymentMethod);
        checkout.setPaymentConfirmed(false); // Assume payment confirmation is handled elsewhere
        Checkout savedCheckout = checkoutRepository.save(checkout);
        return toCheckoutDTO(savedCheckout);
    }

    @Override
    public CheckoutDTO getCheckoutByOrder(Order order) {
        Checkout checkout = checkoutRepository.findByOrder(order).orElse(null);
        return checkout != null ? toCheckoutDTO(checkout) : null;
    }

    @Override
    public CheckoutDTO getLatestCheckoutForUser(User user) {
        List<Checkout> checkouts = checkoutRepository.findByUser(user);
        if (checkouts.isEmpty()) return null;
        Checkout latest = checkouts.stream().max((a, b) -> a.getCheckoutTime().compareTo(b.getCheckoutTime())).orElse(checkouts.get(0));
        return toCheckoutDTO(latest);
    }

    private CheckoutDTO toCheckoutDTO(Checkout checkout) {
        CheckoutDTO dto = new CheckoutDTO();
        dto.setId(checkout.getId());
        dto.setUserId(checkout.getUser().getId());
        dto.setOrderId(checkout.getOrder().getId());
        dto.setCheckoutTime(checkout.getCheckoutTime());
        dto.setPaymentMethod(checkout.getPaymentMethod());
        dto.setPaymentConfirmed(checkout.isPaymentConfirmed());
        return dto;
    }
}
