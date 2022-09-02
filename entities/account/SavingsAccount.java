package entities.account;

import java.time.LocalDate;
import java.util.LinkedList;

import entities.Bank;
import entities.Transaction;

public class SavingsAccount extends Account {
    private float dailyLimit;

    public SavingsAccount(int customerId, String IFSC) {
        super(customerId, IFSC, "savings");
        this.dailyLimit = 1000.0F;
    }


    @Override
    public int isTransactionValid(float amount) {
        LocalDate today;
        float totalAmountDebited;
        LinkedList<Long> todayTransactionIds;
        Transaction transaction;
        
        // Insufficient balance.
        if(amount > this.getBalance())
            return 401;

        // Ensure transaction within allowed daily limit.
        today = LocalDate.now();
        todayTransactionIds = this.getTransactionIds(today);
        totalAmountDebited = 0;

        // Find the total amount transferred from this 
        // account (Debit transactions).
        for(Long transId : todayTransactionIds) {
            transaction = Bank.getTransaction(transId);

            // Successfull debit transaction
            if(transaction.payerAccountNo == this.getAccountNo() && transaction.isSuccessfull)
                totalAmountDebited += transaction.amount;
        }

        // Ensure maximum daily limit is not exceeded.
        if(totalAmountDebited + amount > this.getDailyLimit())
            return 402;

        // Transaction valid, can proceed.
        return 200;
    }


    // Getter
    public float getDailyLimit() {
        return this.dailyLimit;
    }

    public void setDailyLimit(float limit) {
        this.dailyLimit = limit;
    }
}