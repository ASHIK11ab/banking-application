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
    private String branchIFSC;
    private String type;
    private float balance;
    private float dailyLimit;
    private String transPassword;
    private ArrayList<Beneficiary> beneficiaries;
    // Stores account numbers of the added beneficiaries.
    private HashSet<String> beneficiaryAccounts;
    private HashMap<LocalDate, Pair<LinkedList<Transaction>, LocalDate>> transactions;


    Account(int customerId, String IFSC, String type, float dailyLimit) {
        Account._counter++;
        this.accountNo = genAccountNo(IFSC, type);
        this.customerId = customerId;
        this.branchIFSC = IFSC;
        this.balance = 1000.0F;
        this.type = type;
        this.dailyLimit = dailyLimit;
        this.transPassword = genPassword();
        this.beneficiaries = new ArrayList<Beneficiary>();
        this.beneficiaryAccounts = new HashSet<String>();

        // Transactions are indexed by transaction date, its value is a 'Pair'.
        // The pair contains list of transactions on a date and the date of the
        // transaction prior to the current date's transaction.
        // Previous date is used for efficiently iterating transactions over a
        // given date range.
        this.transactions = 
            new HashMap<LocalDate, Pair<LinkedList<Transaction>, LocalDate>>();
    }


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

    
    // Adds a beneficiary to this account.
    public void addBeneficiary(Beneficiary beneficiary) {
        this.beneficiaries.add(beneficiary);
        this.beneficiaryAccounts.add(beneficiary.getAccountNo());
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


    public void addTransaction(Transaction transaction) {}

    public boolean isTransPasswordEqual(String password) {
        return this.transPassword.equals(password);
    }

    public String toString() {
        String repr = "";
        repr += "Account No   : " + this.getAccountNo() + "\n";
        repr += "IFSC Code    : " + this.getBranchIFSC() + "\n";
        repr += "Account type : " + this.getType() + "\n";
        repr += "Balance      : " + this.getBalance() + "\n";
        return repr;
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

    public float getDailyLimit() {
        return this.dailyLimit;
    }

    public ArrayList<Beneficiary> getBeneficiaries() {
        return this.beneficiaries;
    }

    public HashMap<LocalDate, Pair<LinkedList<Transaction>, LocalDate>> getTransactionHistories() {
        return this.transactions;
    }

    // Setters
    public void setDailyLimit(float limit) {
        this.dailyLimit = limit;
    }

    public void setTransPassword(String pass) {
        this.transPassword = pass;
    }
}