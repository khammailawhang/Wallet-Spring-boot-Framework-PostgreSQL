package com.example.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DepositRequest {
    private String userId;
    private double amount;

    @JsonCreator
    public DepositRequest(
            @JsonProperty("userId") String userId,
            @JsonProperty("amount") double amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public String getUserId() { return userId; }
    public double getAmount() { return amount; }
}
