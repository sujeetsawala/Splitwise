package com.Splitwise.Pojos;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Transaction {
    private String transactionId;
    private String transactionName;
    private String transactionCreatorId;
    private Float transactionAmount;
    private Integer transactionParticipants;
    private TransactionType transactionType;
    private Map<String, Float> userAmountMap = new HashMap<>();
}
