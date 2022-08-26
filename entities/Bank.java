package entities;

import java.util.HashMap;
import java.util.LinkedList;

import entities.users.*;
import entities.account.Account;

public class Bank {
    private Admin admin;
    private HashMap<Integer, Customer> customers;
    private HashMap<Integer, BranchManager> managers;
    private HashMap<String, Account> accounts;
    private HashMap<String, Branch> branches;

    private HashMap<Integer, Transaction> transactions;
    private LinkedList<Integer> transactionIds;

    public Bank(Admin admin) {
        this.admin = admin;
        this.customers = new HashMap<Integer, Customer>();
        this.managers = new HashMap<Integer, BranchManager>();
        this.accounts = new HashMap<String, Account>();
        this.branches = new HashMap<String, Branch>();
        this.transactions = new HashMap<Integer, Transaction>();
        this.transactionIds = new LinkedList<Integer>();
    }

    public Admin getAdmin() {
        return this.admin;
    }

    public Customer getCustomer(int customerId) {
        return this.customers.get(customerId);
    }

    public BranchManager getManager(int managerId) {
        return this.managers.get(managerId);
    }

    public Account getAccount(String accountNo) {
        return this.accounts.get(accountNo);
    }

    public Branch getBranch(String IFSC) {
        return this.branches.get(IFSC);
    }

    public Transaction getTransaction(int transactionId) {
        return this.transactions.get(transactionId);
    }

    // Adds entities to bank.
    public void addCustomer(Customer customer) {
        this.customers.put(customer.getId(), customer);
    }

    public void addManager(BranchManager manager) {
        this.managers.put(manager.getId(), manager);
    }

    public void addBranch(Branch branch) {
        this.branches.put(branch.getIFSC(), branch);
    }

    public void addAccount(Account account) {
        this.accounts.put(account.getAccountNo(), account);
    }

    public void addTransaction(Transaction transaction) {
        this.transactionIds.addFirst(transaction.getId());
        this.transactions.put(transaction.getId(), transaction);
    }
}