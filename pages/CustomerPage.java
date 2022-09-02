package pages;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import entities.Bank;
import entities.Branch;
import entities.Beneficiary;
import entities.Transaction;
import entities.account.Account;
import entities.users.Customer;

import views.CustomerView;

public class CustomerPage {
    private final Customer customer;

    public CustomerPage(int userId) {
        this.customer = Bank.getCustomer(userId);
    }


    public void dashboard() {
        System.out.println("\nHello " + customer.getName());
        this.menu();
    }


    public void menu() {
        int choice;
        char proceed = 'y';
        Scanner sc = new Scanner(System.in);

        while(proceed == 'y') {
            choice = CustomerView.displayActions();

            switch(choice) {
                case 1: this.fundTransfer(); break;
                case 2: this.addBeneficiary(); break;
                case 3: this.removeBeneficiary(); break;
                case 4: CustomerView.displayBalance(this.customer.getAccount());
                        break;
                case 5: CustomerView.displayBeneficiaries(this.customer.getAccount().getBeneficiaries()); 
                        break;
                case 6: CustomerView.displayProfile(this.customer);
                        break;
                case 7: CustomerView.displayAccount(this.customer.getAccount());
                        break;
                case 8: this.transactionHistory(); break;
                case 9: this.miniStatement(); break;
                case 10: proceed = 'n'; break;
            }

            System.out.print("\nPress enter to continue ... ");
            System.console().readLine();
        }
    }


    public void fundTransfer() {
        // Account of the customer.
        Account customerAccount = this.customer.getAccount();
        Account beneficiaryAccount;
        Transaction transaction;
        String beneficiaryAccountNo;
        String transPassword;
        String msg = "";
        float amount;
        int beneficiaryNo;
        int transactionStatusCode;
        boolean isSuccessfull = false;

        Scanner sc = new Scanner(System.in);

        if(customerAccount.getBeneficiaries().size() == 0) {
            System.out.println("\nNo beneficiaries added !!!");
            System.out.println("Add beneficiaries to transfer funds !!!");
            return;
        }

        System.out.println("\nFund Transfer:");
        System.out.println("--------------");
        System.out.print("Enter beneficiary no ('0' to display added beneficiaries): ");
        beneficiaryNo = sc.nextInt();

        if(beneficiaryNo == 0) {
            CustomerView.displayBeneficiaries(this.customer.getAccount().getBeneficiaries());
            System.out.print("\nEnter beneficiary no: ");
            beneficiaryNo = sc.nextInt();
        }

        // Validate beneficiary account.
        if(!(beneficiaryNo > 0 && 
                beneficiaryNo <= customerAccount.getBeneficiaries().size()) ) {
            System.out.println("\n\nInvalid Beneficiary no !!!");
            return;
        }

        System.out.print("\nEnter amount to transfer: ");
        amount = sc.nextFloat();
        System.out.print("\nEnter transaction password: ");
        transPassword = System.console().readLine();

        // Validate transaction password.
        if(!customerAccount.isTransPasswordEqual(transPassword)) {
            System.out.println("\n\nIncorrect transaction password");
            return;
        }

        beneficiaryAccountNo = customerAccount.getBeneficiary(beneficiaryNo - 1).getAccountNo();
        beneficiaryAccount = Bank.getAccount(beneficiaryAccountNo);

        if(!beneficiaryAccount.isActive()) {
            System.out.println("\n\nCannot transfer amount !!!");
            return;
        }

        transactionStatusCode = customerAccount.isTransactionValid(amount);
        switch(transactionStatusCode) {
            case 200: isSuccessfull = true;
                      msg = "\n\nTransaction Successfull";
                      break;
            case 401: isSuccessfull = false;
                      msg = "\n\nTransaction failed !!!\nInsufficient balance !!!";
                      break;
            case 402: isSuccessfull = false;
                      msg = "\n\nTransaction failed !!!\nMaximum daily limit reached !!!";
                      break;
        }

        System.out.println(msg);

        if(transactionStatusCode == 200) {
           // Proceed with transaction.
            customerAccount.debit(amount);
            beneficiaryAccount.credit(amount); 
        }
        
        // Create transaction record.
        transaction = new Transaction(customerAccount, beneficiaryAccount, amount,
                                        isSuccessfull, LocalDate.now(), LocalTime.now());
        Bank.addTransaction(transaction);

        // Add transaction record to the involving branches.
        Branch payerBranch = Bank.getBranch(customerAccount.getBranchIFSC());
        Branch payeeBranch = Bank.getBranch(beneficiaryAccount.getBranchIFSC());
        if(payerBranch == payeeBranch)
            payerBranch.addTransactionId(transaction);
        else {
            payerBranch.addTransactionId(transaction);
            payeeBranch.addTransactionId(transaction);
        }

        customerAccount.addTransaction(transaction);
        beneficiaryAccount.addTransaction(transaction);
    }


    public void addBeneficiary() {
        String accountNo;
        String confirmAccountNo;
        String name;
        char beneficiaryConfirmation = 'n';
        
        System.out.println("\nAdd Beneficiary:");
        System.out.println("----------------");
        System.out.print("\nEnter account no: ");
        accountNo = System.console().readLine();
        System.out.print("Confirm account no: ");
        confirmAccountNo = System.console().readLine();
        System.out.print("Beneficiary name: ");
        name = System.console().readLine();

        if(!accountNo.equals(confirmAccountNo)) {
            System.out.println("\nAccount no does not match !!!");
            return;
        }

        if(accountNo.equals(this.customer.getAccount().getAccountNo())) {
            System.out.println("\nCannot add yourself as beneficiary !!!");
            return;
        }

        // Verify account details.
        Account account = Bank.getAccount(accountNo);
        if(account == null) {
            System.out.println("\nInvalid account details !!!");
            return;
        }

        // Avoid duplicate beneficiaries.
        if(this.customer.getAccount().isBeneficiaryExists(accountNo)) {
            System.out.println("\nBeneficiary aldready exists !!!");
            return;
        }
        
        // Beneficiary confirmation.
        System.out.println("\nBeneficiary details:");
        System.out.println("--------------------");
        System.out.println("Account No   : " + account.getAccountNo());
        System.out.println("IFSC Code    : " + account.getBranchIFSC());
        System.out.print("\nProceed to adding beneficiary (y/n): ");
        beneficiaryConfirmation = System.console().readLine().toLowerCase().charAt(0);

        if(beneficiaryConfirmation == 'n')
            return;

        Beneficiary beneficiary = new Beneficiary(account.getAccountNo(),
                                                  account.getBranchIFSC(),
                                                  name);
        this.customer.getAccount().addBeneficiary(beneficiary);
        System.out.println("\nBeneficiary added successfully !!!");
    }


    public void removeBeneficiary() {
        Account account = this.customer.getAccount();
        int beneficiariesCnt = account.getBeneficiaries().size();
        int index;

        if(beneficiariesCnt == 0) {
            System.out.println("\nNo beneficiaries added !!!");
            System.out.println("Cannot remove beneficiary !!!");
            return;
        }

        Scanner sc = new Scanner(System.in);

        CustomerView.displayBeneficiaries(this.customer.getAccount().getBeneficiaries());

        System.out.println();
        System.out.print("Enter beneficiary number to remove: ");
        index = sc.nextInt();

        if(index >= 1 && index <= beneficiariesCnt) {
            account.removeBeneficiary(index-1);
            System.out.println("\nBeneficiary removed successfully !!!");
        } else {
            System.out.println("\nInvalid beneficiary number !!!");
        }
    }


    public void transactionHistory() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Account account = this.customer.getAccount();
        LinkedList<Long> transactionIds;
        Transaction transaction;
        LocalDate fromDate;
        LocalDate toDate;
        LocalDate date;
        String status;
        int cnt = 0;
        
        System.out.println("\nTransaction History:");
        System.out.println("--------------------");
        System.out.print("\nEnter from date (dd/mm/yyyy) : ");
        fromDate = LocalDate.parse(System.console().readLine(), formatter);
        System.out.print("Enter to date (dd/mm/yyyy)   : ");
        toDate = LocalDate.parse(System.console().readLine(), formatter);

        System.out.println("\n\nTransactions (" + formatter.format(fromDate) + ")" + " to (" + formatter.format(toDate) + "):");
        System.out.println("------------------------------------------");
        
        date = fromDate;

        while(date.isBefore(toDate) || date.isEqual(toDate)) {

            // Move to next date if no transactions were made on a date.
            if(!account.getTransactionIdMap().containsKey(date)) {
                date = date.plusDays(1);
                continue;
            }

            transactionIds = account.getTransactionIds(date);

            for(Long transId : transactionIds) {
                transaction = Bank.getTransaction(transId);
                cnt++;
                System.out.println("\n" + transaction);

                if(transaction.payerAccountNo.equals(account.getAccountNo()))
                    System.out.println("Type           : Debit");
                else
                    System.out.println("Type           : Credit");

                status = (transaction.isSuccessfull) ? "Successfull" : "Failed";

                System.out.println("Status         : " + status);
            }

            date = date.plusDays(1);
        }

        if(cnt == 0)
            System.out.println("\nNo transactions in given range.");
    }


    public void miniStatement() {
        Account account = this.customer.getAccount();
        LinkedList<Long> transactionIds;
        Transaction transaction;
        ListIterator<Long> it;
        LocalDate date;
        String nameToDisplay = "";
        String type = "";
        String transDate;
        int cnt = 0;

        System.out.println("\n---------------");
        System.out.println("Mini Statement:");
        System.out.println("---------------");
        System.out.println("\nA/C No: " + account.getAccountNo());

        date = account.getRecentTransactionDate();

        outerLoop:
        while(cnt < 10 && date != null) {
            transactionIds = account.getTransactionIds(date);
            it = transactionIds.listIterator(transactionIds.size());

            while(it.hasPrevious()) {
                transaction = Bank.getTransaction(it.previous());

                // Skip failed transactions
                if(!transaction.isSuccessfull)
                    continue;
                
                if(cnt == 10)
                    break outerLoop;

                cnt++;
                // Display reciever name for debit and sender name for credit transactions.
                if(transaction.payerAccountNo.equals(account.getAccountNo())) {
                    type = "Debit";
                    nameToDisplay = Bank.getCustomer(Bank.getAccount(transaction.payeeAccountNo).getCustomerId()).getName();
                }
                else {
                    type = "Credit";
                    nameToDisplay = Bank.getCustomer(Bank.getAccount(transaction.payerAccountNo).getCustomerId()).getName();
                }

                transDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(transaction.date);
                System.out.println(String.format("\n%d ) %s %s %6s %.2f", cnt, transDate, nameToDisplay, type, transaction.amount));
            }

            // Next recent transaction date.
            date = account.getTransactionIdMap().get(date).getSecond();
        }

        System.out.println("\nAvailable Balance: " + account.getBalance());
    }
}