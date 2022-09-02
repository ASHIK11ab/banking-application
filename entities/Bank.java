package entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

import ds.Pair;
import entities.users.*;
import entities.account.Account;

public class Bank {
    private static Admin admin;
    private static HashMap<Integer, Customer> customers = new HashMap<Integer, Customer>();
    private static HashMap<Integer, BranchManager> managers = new HashMap<Integer, BranchManager>();
    private static HashMap<String, Account> accounts = new HashMap<String, Account>();
    private static HashMap<String, Branch> branches = new HashMap<String, Branch>();

    private static HashMap<Long, Transaction> transactions = new HashMap<Long, Transaction>();
    private static LocalDate recentTransactionDate = null;
    private static HashMap<LocalDate, Pair<LinkedList<Long>, LocalDate>> transactionIds
        = new HashMap<LocalDate, Pair<LinkedList<Long>, LocalDate>>();
    

    // Internally called by 'addTransaction()' method.
    private static void addTransactionId(Transaction transaction) {
        LinkedList<Long> todayTransactionIds;
        
        // First transaction of today.
        if(!Bank.transactionIds.containsKey(transaction.date)) {
            todayTransactionIds = new LinkedList<Long>();
            todayTransactionIds.addLast(transaction.id);

            Pair<LinkedList<Long>, LocalDate> transactionPair = 
                new Pair<LinkedList<Long>, LocalDate>();

            transactionPair.setFirst(todayTransactionIds);
            transactionPair.setSecond(Bank.recentTransactionDate);

            Bank.transactionIds.put(transaction.date, transactionPair);
            Bank.recentTransactionDate = transaction.date;
        } else {
            todayTransactionIds = Bank.transactionIds.get(transaction.date).getFirst();
            todayTransactionIds.addLast(transaction.id);
        }
    }

    // Get entities.
    public static Admin getAdmin() {
        return Bank.admin;
    }

    public static LocalDate getRecentTransactionDate() {
        return Bank.recentTransactionDate;
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

    // Returns all transactions of this account.
    public HashMap<LocalDate, Pair<LinkedList<Long>, LocalDate>> getTransactionIdMap() {
        return Bank.transactionIds;
    }

    
    // Returns all transaction ids of this account on a given date.
    public LinkedList<Long> getTransactionIds(LocalDate date) {
        if(!Bank.transactionIds.containsKey(date))
            return new LinkedList<Long>();
        else
            return Bank.transactionIds.get(date).getFirst();
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
        Bank.transactions.put(transaction.id, transaction);
        Bank.addTransactionId(transaction);
    }
}