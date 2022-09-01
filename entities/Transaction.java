package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public Transaction(Account payerAccount, Account payeeAccount, float amount,
                        boolean isSuccessfull, LocalDate date, LocalTime time) {
        Transaction._counter++;
        this.id = Transaction._counter;
        this.payerAccountNo = payerAccount.getAccountNo();
        this.payerBeforeBalance = payerAccount.getBalance();
        this.payeeAccountNo = payeeAccount.getAccountNo();
        this.payeeBeforeBalance = payeeAccount.getBalance();
        this.isSuccessfull = isSuccessfull;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }


    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm a");
        LocalDateTime dateTime = LocalDateTime.parse(this.date.toString() + "T"+ this.time.toString());
        
        String repr = "";
        repr += "Transaction id : " + this.id + "\n";
        repr += "Payer A/C No   : " + this.payerAccountNo + "\n";
        repr += "Payee A/C No   : " + this.payeeAccountNo + "\n";
        repr += "Amount         : " + this.amount + "\n";
        repr += "Date & Time    : " + fmt.format(dateTime);
        return repr;
    }
}