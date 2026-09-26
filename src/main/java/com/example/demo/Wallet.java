package com.example.demo;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long walletId;

    // ໃຫ້ໃຊ້ userId ເປັນ Key ຫຼັກໃນການຄົ້ນຫາແບບງ່າຍໆ
    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "currency")
    private String currency = "LAK";

    public Wallet() {}

    public Wallet(String userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    // Getters and Setters
    public Long getWalletId() { return walletId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
