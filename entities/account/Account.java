package entities.account;

import java.util.LinkedList;
import java.util.ArrayList;

import entities.Beneficiary;
import entities.Transaction;

public abstract class Account {
    private final String accountNo;
    private final int customerId;
    private int branchId;
    private float balance;
    private float dailyLimit;
    private String transPassword;
    private ArrayList<Beneficiary> beneficiaries;
    private LinkedList<String> transactionIds;


    Account(int customerId, int branchId, float dailyLimit) {
        this.accountNo = genAccountNo();
        this.customerId = customerId;
        this.branchId = branchId;
        this.balance = 0.0F;
        this.dailyLimit = dailyLimit;
        this.transPassword = genTransPassword();
        this.beneficiaries = new ArrayList<Beneficiary>();
        this.transactionIds = new LinkedList<String>();
    }


    private String genAccountNo() {
        return "111";
    }

    private String genTransPassword() {
        return "222";
    }

    public void addBeneficiary(Beneficiary beneficiary) {}

    public void removeBeneficiary(Beneficiary beneficiary) {}

    public void addTransaction(Transaction transaction) {}

    public boolean isTransPasswordEqual(String password) {
        return this.transPassword.equals(password);
    }

    public String toString() {
        String repr = "";
        repr += "\nAccount Details:\n";
        repr += "Account No: " + this.getAccountNo() + "\n";
        repr += "IFSC Code: " + "\n";
        repr += "Balance: " + this.getBalance() + "\n";
        return repr;
    }


    // Getters
    public String getAccountNo() {
        return this.accountNo;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public float getBalance() {
        return this.balance;
    }

    public int getBranchId() {
        return this.branchId;
    }

    public float getDailyLimit() {
        return this.dailyLimit;
    }

    public ArrayList<Beneficiary> getBeneficiaries() {
        return this.beneficiaries;
    }

    public LinkedList<String> getTransactionHistories() {
        return this.transactionIds;
    }

    // Setters
    public void setDailyLimit(float limit) {
        this.dailyLimit = limit;
    }

    public void setTransPassword(String pass) {
        this.transPassword = pass;
    }
}