package com.Splitwise.Pojos;

public enum TransactionType {
    EXACT("EXACT"),
    EQUAL("EQUAL"),
    PERCENTAGE("PERCENTAGE");

    String stringValue;
    TransactionType(String transactionType) {
        this.stringValue = transactionType;
    }
}
