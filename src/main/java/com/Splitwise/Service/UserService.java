package com.Splitwise.Service;

import com.Splitwise.Pojos.Transaction;
import com.Splitwise.Pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private Map<String, List<String>>  userTransactionMap = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private TransactionService transactionService;

    @Autowired
    public UserService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void createUser(User user) {
        if(!users.containsKey(user)) {
            users.put(user.getUserId(), user);
        }
    }

    public void addTransactionToUser(String userId, String transactionId) {
        if(!users.containsKey(userId))
            return;
        if(userTransactionMap.containsKey(userId)) {
            userTransactionMap.get(userId).add(transactionId);
        } else {
            List<String> transactionIds = new ArrayList<>();
            transactionIds.add(transactionId);
            userTransactionMap.put(userId, transactionIds);
        }
    }

    public Map<String, Float> getUserBalance(String userId) {
        if(!users.containsKey(userId))
            return null;

        if(!userTransactionMap.containsKey(userId))
            return null;

        List<String> transactionIds = userTransactionMap.get(userId);
        Map<String, Float> userBalanceMap = new HashMap<>();
        for(String transactionId: transactionIds) {
            Transaction transaction = this.transactionService.getTransaction(transactionId);
            if(transaction.getTransactionCreatorId().equalsIgnoreCase(userId)) {
                transaction.getUserAmountMap().forEach( (userId1, amount) -> {
                    if(!userId1.equalsIgnoreCase(userId) && userBalanceMap.containsKey(userId1)) {
                        Float value = userBalanceMap.get(userId1);
                        userBalanceMap.put(userId1, amount + value);
                    } else if(!userId1.equalsIgnoreCase(userId)) {
                        userBalanceMap.put(userId1, amount);
                    }
                });
            } else {
                if (userBalanceMap.containsKey(transaction.getTransactionCreatorId())) {
                    Float value = userBalanceMap.get(transaction.getTransactionCreatorId());
                    userBalanceMap.put(transaction.getTransactionCreatorId(), value - transaction.getUserAmountMap().get(userId));
                } else {
                    userBalanceMap.put(transaction.getTransactionCreatorId(), -transaction.getUserAmountMap().get(userId));
                }
            }
        }
        return userBalanceMap;
    }

    public Float getUserExpenses(String userId) {
        Float expenses = 0f;
        if(!users.containsKey(userId))
            return null;

        List<String> transactionIds = userTransactionMap.get(userId);
        for(String transactionId :  transactionIds) {
            Transaction transaction = this.transactionService.getTransaction(transactionId);
            if(transaction.getTransactionCreatorId().equalsIgnoreCase(userId))
                expenses += transaction.getTransactionAmount();
        }

        return expenses;
    }
}
