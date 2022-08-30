package entities;

import java.time.LocalDate;
import java.time.LocalTime;

import entities.account.Account;

public class Transaction {
    private static long _counter = 100000000000000L;
    public final long id;
    public final String payerAccountNo;
    public final float payerBeforeBalance;
    public final String payeeAccountNo;
    public final float payeeBeforeBalance;
    public final float amount;
    public final boolean isSuccessfull;
    public final LocalDate date;
    public final LocalTime time;

    public Transaction(Account payerAccount, Account payeeAccount, 
                        float amount, boolean isSuccessfull) {
        Transaction._counter++;
        this.id = Transaction._counter;
        this.payerAccountNo = payerAccount.getAccountNo();
        this.payerBeforeBalance = payerAccount.getBalance();
        this.payeeAccountNo = payeeAccount.getAccountNo();
        this.payeeBeforeBalance = payeeAccount.getBalance();
        this.isSuccessfull = isSuccessfull;
        this.amount = amount;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public String toString() {
        return this.id + " : " + this.payerAccountNo + " : " + this.amount;
    }
}