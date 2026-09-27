package com.example.demo; // 🌟 ຕ້ອງມີແຖວນີ້ຢູ່ເທິງສຸດສະເໝີ!

public class TransferRequest {
    private String fromUserId;
    private String toUserId;
    private double amount;

    // 1. Constructor ວ່າງເປົ່າ (ສຳລັບ HTML Form)
    public TransferRequest() {
    }

    // 2. Constructor ທີ່ມີ Parameters
    public TransferRequest(String fromUserId, String toUserId, double amount) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
    }

    // Getter ແລະ Setter
    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
