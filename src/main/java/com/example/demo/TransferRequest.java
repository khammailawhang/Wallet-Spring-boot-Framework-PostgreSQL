package com.example.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferRequest {
    private String fromUserId;
    private String toUserId;
    private double amount;

    // ບັງຄັບໃຫ້ Jackson 3 ໃຊ້ Constructor ໂຕນີ້ໃນການແປງ JSON (ຊົວຣ໌ 100%)
    @JsonCreator
    public TransferRequest(
            @JsonProperty("fromUserId") String fromUserId,
            @JsonProperty("toUserId") String toUserId,
            @JsonProperty("amount") double amount) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
    }

    // Getters
    public String getFromUserId() { return fromUserId; }
    public String getToUserId() { return toUserId; }
    public double getAmount() { return amount; }
}
