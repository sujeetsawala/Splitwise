package com.Splitwise.Service;

import com.Splitwise.Pojos.Transaction;
import com.Splitwise.Pojos.TransactionRequest;
import com.Splitwise.Pojos.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionService {
    private Map<String, Transaction> transactionMap = new HashMap<>();

    public Transaction createTransaction(@RequestBody TransactionRequest transactionRequest) {
        String transactionId = Integer.toString(transactionMap.size() + 1);
        Map<String, Float> userAmountMap = this.getUserAmountMap(transactionRequest);
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setTransactionAmount(transactionRequest.getTransactionAmount());
        transaction.setTransactionCreatorId(transactionRequest.getTransactionCreatorId());
        transaction.setTransactionName(transactionRequest.getTransactionName());
        transaction.setTransactionParticipants(transactionRequest.getTransactionParticipants());
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setUserAmountMap(userAmountMap);
        transactionMap.put(transactionId, transaction);
        return transaction;
    }

    public Transaction getTransaction(String transactionId) {
        if(transactionMap.containsKey(transactionId))
            return transactionMap.get(transactionId);
        return null;
    }

    public Boolean updateUserTransaction(String userId, Float amount, String transactionId) {
        if(transactionMap.containsKey(transactionId)) {
            Map<String, Float> userAmountMap = transactionMap.get(transactionId).getUserAmountMap();
            if(userAmountMap.containsKey(userId)) {
                Float value = userAmountMap.get(userId) - amount;
                userAmountMap.put(userId, value);
                return true;
            } else
                return false;
        }
        return false;
    }

    private Map<String, Float> getUserAmountMap(TransactionRequest transactionRequest) {
        Map<String, Float> userAmountMap = new HashMap<>();
        if(transactionRequest.getTransactionType() == TransactionType.EQUAL) {
            Float value = transactionRequest.getTransactionAmount() / transactionRequest.getTransactionParticipants();
            for(String userId: transactionRequest.getUserIds()) {
                userAmountMap.put(userId, value);
            }
        } else if(transactionRequest.getTransactionType() == TransactionType.PERCENTAGE) {
            Map<String, Float> userPercentage = (Map<String, Float>)transactionRequest.getMetaData();
            for(String userId: transactionRequest.getUserIds()) {
                Float value = transactionRequest.getTransactionAmount() * userPercentage.get(userId)/ 100;
                userAmountMap.put(userId, value);
            }
        } else {
            Map<String, Float> userShare = (Map<String, Float>)transactionRequest.getMetaData();
            for(String userId: transactionRequest.getUserIds()) {
                Float value = userShare.get(userId);
                userAmountMap.put(userId, value);
            }
        }
        return userAmountMap;
    }
}
