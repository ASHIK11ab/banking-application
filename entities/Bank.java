package entities;

import java.util.HashMap;
import java.util.LinkedList;

import entities.users.*;
import entities.account.Account;

public class Bank {
    private static Admin admin;
    private static HashMap<Integer, Customer> customers = new HashMap<Integer, Customer>();
    private static HashMap<Integer, BranchManager> managers = new HashMap<Integer, BranchManager>();
    private static HashMap<String, Account> accounts = new HashMap<String, Account>();
    private static HashMap<String, Branch> branches = new HashMap<String, Branch>();

    private static HashMap<Long, Transaction> transactions = new HashMap<Long, Transaction>();
    private static LinkedList<Long> transactionIds = new LinkedList<Long>();

    // Getters
    public static Admin getAdmin() {
        return Bank.admin;
    }

    public static Customer getCustomer(int customerId) {
        return Bank.customers.get(customerId);
    }

    public static BranchManager getManager(int managerId) {
        return Bank.managers.get(managerId);
    }

    public static Account getAccount(String accountNo) {
        return Bank.accounts.get(accountNo);
    }

    public static Branch getBranch(String IFSC) {
        return Bank.branches.get(IFSC);
    }

    public static Transaction getTransaction(Long transactionId) {
        return Bank.transactions.get(transactionId);
    }

    public static void setAdmin(Admin admin) {
        Bank.admin = admin;
    }

    // Adds entities to bank.
    public static void addCustomer(Customer customer) {
        Bank.customers.put(customer.getId(), customer);
    }

    public static void addManager(BranchManager manager) {
        Bank.managers.put(manager.getId(), manager);
    }

    public static void addBranch(Branch branch) {
        Bank.branches.put(branch.getIFSC(), branch);
    }

    public static void addAccount(Account account) {
        Bank.accounts.put(account.getAccountNo(), account);
    }

    public static void addTransaction(Transaction transaction) {
        Bank.transactionIds.addFirst(transaction.id);
        Bank.transactions.put(transaction.id, transaction);
    }
}