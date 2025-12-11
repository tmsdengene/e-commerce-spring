package com.gibesystems.ecommerce.payment;

import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.order.entity.Order;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private String status; // e.g., PENDING, SUCCESS, FAILED

    @Column(nullable = false)
    private LocalDateTime paymentTime;
}
