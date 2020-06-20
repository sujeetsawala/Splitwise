package com.Splitwise.Controller;


import com.Splitwise.Pojos.Transaction;
import com.Splitwise.Pojos.TransactionRequest;
import com.Splitwise.Service.TransactionService;
import com.Splitwise.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    private TransactionService transactionService;
    private UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }
    @RequestMapping(value = "/createTransaction" , method = RequestMethod.POST)
    public void createTransaction(
            @RequestBody TransactionRequest transactionRequest
            ) {
        Transaction transaction = this.transactionService.createTransaction(transactionRequest);
        transaction.getUserAmountMap().forEach((k,v) -> this.userService.addTransactionToUser(k, transaction.getTransactionId()));
    }

    @RequestMapping(value = "/getTransaction" , method = RequestMethod.GET)
    public Transaction getTransaction(
            @RequestParam(value = "transactionId") String transactionId
    ) {
        return this.transactionService.getTransaction(transactionId);
    }

    @RequestMapping(value = "/updateTransaction" , method = RequestMethod.GET)
    public Boolean updateTransaction(
            @RequestParam(value = "transactionId") String transactionId,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "amount") Float amount
    ) {
        return this.transactionService.updateUserTransaction(userId, amount, transactionId);
    }


}
