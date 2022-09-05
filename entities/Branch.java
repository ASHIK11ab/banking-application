package entities;

import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.time.LocalDate;


public class Branch {
    private static int _counter;
    private final String IFSC;
    private String name;
    private int managerId;
    private LinkedHashSet<String> accounts;
    private HashMap<LocalDate, LinkedList<Long>> transactionIds;

    public Branch(String name) {
        Branch._counter++;
        this.name = name;
        this.IFSC = genBranchIFSC();
        this.managerId = -1;
        this.accounts = new LinkedHashSet<String>();

        // Transactions are indexed by transaction date, with the list of 
        // transaction id's as its value.
        this.transactionIds = new HashMap<LocalDate, LinkedList<Long>>();
    }


    // Adds a transaction id to this branch's transaction id's.
    public void addTransactionId(Transaction transaction) {
        LinkedList<Long> transactionIds;
        
        // First transaction of today.
        if(!this.transactionIds.containsKey(transaction.date)) {
            transactionIds = new LinkedList<Long>();
            transactionIds.addLast(transaction.id);
            this.transactionIds.put(transaction.date, transactionIds);
        } else {
            transactionIds = this.transactionIds.get(transaction.date);
            transactionIds.addLast(transaction.id);
        }
    }


    // Returns the list of transactions made on a given date.
    public LinkedList<Long> getTransactionIds(LocalDate date) {
        return this.transactionIds.get(date);
    }


    private String genBranchIFSC() {
        return String.format("YESB0%06d", _counter);
    }

    public void addAccount(String accountNo) {
        this.accounts.add(accountNo);
    }

    public void removeManager() {
        this.managerId = -1;
    }

    // Getters
    public String getIFSC() {
        return this.IFSC;
    }

    public String getName() {
        return this.name;
    }

    public int getManagerId() {
        return this.managerId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setManager(int id) {
        this.managerId = id;
    }
}