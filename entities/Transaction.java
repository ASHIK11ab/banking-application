package entities;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public Transaction(String payerAccountNo, String payeeAccountNo, float amount,
                        boolean isSuccessfull, float payerBeforeBalance, float payeeBeforeBalance, 
                        LocalDate date, LocalTime time) {
        Transaction._counter++;
        this.id = Transaction._counter;
        this.payerAccountNo = payerAccountNo;
        this.payerBeforeBalance = payerBeforeBalance;
        this.payeeAccountNo = payeeAccountNo;
        this.payeeBeforeBalance = payeeBeforeBalance;
        this.amount = amount;
        this.isSuccessfull = isSuccessfull;
        this.date = date;
        this.time = time;
    }
}