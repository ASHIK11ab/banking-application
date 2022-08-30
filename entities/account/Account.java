package entities.account;

import java.time.LocalDate;
import java.util.Random;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;

import entities.Beneficiary;
import entities.Transaction;
import ds.Pair;

public abstract class Account {
    private static int _counter = 100000;
    private final String accountNo;
    private final int customerId;
    private boolean isActive;
    private String branchIFSC;
    private String type;
    private float balance;
    private String transPassword;
    private ArrayList<Beneficiary> beneficiaries;
    // Stores account numbers of the added beneficiaries.
    private HashSet<String> beneficiaryAccounts;
    private LocalDate recentTransactionDate;
    private HashMap<LocalDate, Pair<LinkedList<Transaction>, LocalDate>> transactions;


    Account(int customerId, String IFSC, String type) {
        Account._counter++;
        this.accountNo = genAccountNo(IFSC, type);
        this.customerId = customerId;
        this.isActive = true;
        this.branchIFSC = IFSC;
        this.balance = 1000.0F;
        this.type = type;
        this.transPassword = genPassword();
        this.beneficiaries = new ArrayList<Beneficiary>();
        this.beneficiaryAccounts = new HashSet<String>();
        this.recentTransactionDate = null;

        // Transactions are indexed by transaction date, its value is a 'Pair'.
        // The pair contains list of transactions on a date and the date of the
        // transaction prior to the current date's transaction.
        // Previous date is used for efficiently iterating transactions over a
        // given date range.
        this.transactions = 
            new HashMap<LocalDate, Pair<LinkedList<Transaction>, LocalDate>>();
    }

    public abstract int isTransactionValid(float amount);


    private String genAccountNo(String IFSC, String type) {
        String accountNo = "";
        accountNo += IFSC.substring(IFSC.length() - 4, IFSC.length());

        switch(type) {
            case "savings": accountNo += "11"; break;
            case "current": accountNo += "21"; break;
        }

        accountNo += Account._counter;
        return accountNo;
    }


    private String genPassword() {
        String pass = "";
        Random random = new Random();

        for(byte i = 0; i < 4; ++i)
            pass += (char) ('a' + random.nextInt(26));

        for(byte i = 0; i < 4; ++i)
            pass += random.nextInt(10);

        return pass;
    }


    public void activate() {
        this.isActive = true;
    }


    public void deactivate() {
        this.isActive = false;
    }


    public void credit(float amount) {
        this.balance += amount;
    }


    public void debit(float amount) {
        this.balance -= amount;
    }

    
    // Adds a beneficiary to this account.
    public void addBeneficiary(Beneficiary beneficiary) {
        this.beneficiaryAccounts.add(beneficiary.getAccountNo());
        this.beneficiaries.add(beneficiary);
    }


    public boolean isBeneficiaryExists(String accountNo) {
        return this.beneficiaryAccounts.contains(accountNo);
    }


    // Removes a beneficiary.
    public void removeBeneficiary(int index) {
        String removedAccountNo = this.beneficiaries.get(index).getAccountNo();
        this.beneficiaryAccounts.remove(removedAccountNo);
        this.beneficiaries.remove(index);
    }


    public void addTransaction(Transaction transaction) {
        LocalDate today = LocalDate.now();
        LinkedList<Transaction> todayTransactions;
        
        // First transaction of today.
        if(!this.transactions.containsKey(today)) {
            todayTransactions = new LinkedList<Transaction>();
            todayTransactions.addFirst(transaction);

            Pair<LinkedList<Transaction>, LocalDate> transactionPair = 
                new Pair<LinkedList<Transaction>, LocalDate>();
            transactionPair.setFirst(todayTransactions);
            transactionPair.setSecond(this.recentTransactionDate);

            this.transactions.put(today, transactionPair);
            this.recentTransactionDate = today;
        } else {
            todayTransactions = this.transactions.get(today).getFirst();
            todayTransactions.addFirst(transaction);
        }
    }


    public boolean isTransPasswordEqual(String password) {
        return this.transPassword.equals(password);
    }


    public boolean isActive() {
        return this.isActive;
    }


    // Getters
    public String getAccountNo() {
        return this.accountNo;
    }


    public String getType() {
        return this.type;
    }


    public int getCustomerId() {
        return this.customerId;
    }


    public float getBalance() {
        return this.balance;
    }


    public String getBranchIFSC() {
        return this.branchIFSC;
    }


    public ArrayList<Beneficiary> getBeneficiaries() {
        return this.beneficiaries;
    }


    // Returns a added beneficiary.
    public Beneficiary getBeneficiary(int index) {
        return this.beneficiaries.get(index);
    }


    // Returns all transactions of this account.
    public HashMap<LocalDate, Pair<LinkedList<Transaction>, LocalDate>> getTransactions() {
        return this.transactions;
    }


    // Returns all transactions of this account on a given date.
    public LinkedList<Transaction> getTransactions(LocalDate date) {
        if(this.transactions.get(date) == null)
            return new LinkedList<Transaction>();
        else
            return this.transactions.get(date).getFirst();
    }
    

    public void setTransPassword(String pass) {
        this.transPassword = pass;
    }


    public String toString() {
        String repr = "";
        repr += "Account No   : " + this.getAccountNo() + "\n";
        repr += "IFSC Code    : " + this.getBranchIFSC() + "\n";
        repr += "Account type : " + this.getType() + "\n";
        repr += "Balance      : " + this.getBalance() + "\n";
        return repr;
    }
}