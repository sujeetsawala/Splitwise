package com.Splitwise.Pojos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionRequest {
   private String transactionCreatorId;
   private String transactionName;
   private Float transactionAmount;
   private Integer transactionParticipants;
   private TransactionType transactionType;
   private List<String> userIds;
   private Object metaData;
}
