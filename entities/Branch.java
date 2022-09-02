package entities;

import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.time.LocalDate;

import ds.Pair;

public class Branch {
    private static int _counter;
    private final String IFSC;
    private String name;
    private int managerId;
    private LocalDate recentTransactionDate;
    private LinkedHashSet<String> accounts;
    public HashMap<LocalDate, Pair<LinkedList<Long>, LocalDate>> transactionIds;

    public Branch(String name) {
        Branch._counter++;
        this.name = name;
        this.IFSC = genBranchIFSC();
        this.managerId = -1;
        this.recentTransactionDate = null;
        this.accounts = new LinkedHashSet<String>();

        // Transactions are indexed by transaction date, its value is a 'Pair'.
        // The pair contains list of transaction ids on a date and the date of the
        // transaction prior to the current date's transaction.
        // Previous date is used for efficiently iterating transactions over a
        // given date range.
        this.transactionIds = 
            new HashMap<LocalDate, Pair<LinkedList<Long>, LocalDate>>();
    }

    public void addTransactionId(Transaction transaction) {
        LinkedList<Long> todayTransactionIds;
        
        // First transaction of today.
        if(!this.transactionIds.containsKey(transaction.date)) {
            todayTransactionIds = new LinkedList<Long>();
            todayTransactionIds.addLast(transaction.id);

            Pair<LinkedList<Long>, LocalDate> transactionPair = 
                new Pair<LinkedList<Long>, LocalDate>();

            transactionPair.setFirst(todayTransactionIds);
            transactionPair.setSecond(this.recentTransactionDate);

            this.transactionIds.put(transaction.date, transactionPair);
            this.recentTransactionDate = transaction.date;
        } else {
            todayTransactionIds = this.transactionIds.get(transaction.date).getFirst();
            todayTransactionIds.addLast(transaction.id);
        }
    }

    private String genBranchIFSC() {
        return String.format("YESB0%06d", _counter);
    }

    public void addAccount(String accountNo) {
        this.accounts.add(accountNo);
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