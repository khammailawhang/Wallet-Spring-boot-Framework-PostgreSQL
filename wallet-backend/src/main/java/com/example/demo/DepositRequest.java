package com.example.demo; // 🌟 ຕ້ອງມີແຖວນີ້ຢູ່ເທິງສຸດສະເໝີ!

public class DepositRequest {
    private String userId;
    private double amount;

    // 1. Constructor ວ່າງເປົ່າ (ສຳລັບ HTML Form)
    public DepositRequest() {
    }

    // 2. Constructor ທີ່ມີ Parameters
    public DepositRequest(String userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }

    // Getter ແລະ Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
