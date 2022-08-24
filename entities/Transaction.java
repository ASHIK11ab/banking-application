package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private final int id;
    private final String payerAccountNo;
    private final String payeeAccountNo;

    private final float amount;
    private final boolean isSuccessfull;
    private final LocalDate date;
    private final LocalTime time;

    public Transaction(String payerAccountNo, String payeeAccountNo, float amount,
                        boolean isSuccessfull, LocalDate date, LocalTime time) {
        this.id = genTransId();
        this.payerAccountNo = payerAccountNo;
        this.payeeAccountNo = payeeAccountNo;
        this.amount = amount;
        this.isSuccessfull = isSuccessfull;
        this.date = date;
        this.time = time;
    }

    public int genTransId() {
        return 2;
    }

    public boolean isSuccessfull() {
        return this.isSuccessfull;
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public String getPayerAccountNo() {
        return this.payerAccountNo;
    }

    public String getPayeeAccountNo() {
        return this.payeeAccountNo;
    }

    public float getAmount() {
        return this.amount;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTime() {
        return this.time;
    }
}