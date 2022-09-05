package pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

import entities.Bank;
import entities.Branch;
import entities.Transaction;
import entities.account.Account;
import entities.users.BranchManager;
import entities.users.Customer;
import views.BranchManagerView;

public class BranchManagerPage {
    private BranchManager manager;

    public BranchManagerPage(int managerId) {
        this.manager = Bank.getManager(managerId);
    }


    public void dashboard() {
        System.out.println("\nHello " + this.manager.getName());
        menu();
    }


    public void menu() {
        int choice;
        char proceed = 'y';

        while(proceed == 'y') {
            choice = BranchManagerView.displayActions();

            switch(choice) {
                case 1: addCustomer(); break;
                case 2: removeCustomer(); break;
                case 3: manageAccount("activate"); break;
                case 4: manageAccount("deactivate"); break;
                case 5: viewCustomer(); break;
                // case 2: removeCustomer(); break;
                case 7: BranchManagerView.displayProfile(this.manager); break;
                case 8: transactionHistory(); break;
                case 9: transferToOtherBranch(); break;
                case 10: displayPendingAccountTransfer(); break;
                case 11: approveAccountTransfer(); break;
                case 12: proceed = 'n'; break;
                default: System.out.println("\nInvalid choiced !!!");
            }

            System.out.print("\nPress enter to continue ...");
            System.console().readLine();
        }
    }


    public void addCustomer() {
        Branch branch = Bank.getBranch(this.manager.getBranchIFSC());
        Customer customer;
        String name;
        String phone;
        String PAN;
        String ADHAAR;
        String accountType;
        char proceed = 'n';

        Scanner sc = new Scanner(System.in);

        System.out.println("\nAdd Customer:");
        System.out.println("-------------");
        System.out.print("\nCustomer name: ");
        name = sc.nextLine();
        System.out.print("\nCustomer phone: ");
        phone = sc.nextLine();
        System.out.print("\nCustomer ADHAAR: ");
        ADHAAR = sc.nextLine();
        System.out.print("\nCustomer PAN: ");
        PAN = sc.nextLine();
        System.out.print("\nAccount Type (Savings / Current): ");
        accountType = sc.nextLine().toLowerCase();

        if(!accountType.equals("savings") && !accountType.equals("current")) {
            System.out.println("\nInvalid account type !!!");
            return;
        }

        customer = new Customer(name, phone, accountType, manager.getBranchIFSC(),
                                PAN, ADHAAR);

        System.out.println("\n-----------------");
        System.out.println("Customer Details:");
        System.out.println("-----------------");
        System.out.println(customer);
        System.out.println("\nAccount Details:");
        System.out.println("----------------");
        System.out.println(customer.getAccount());

        System.out.print("\nProceed to add customer (y/n): ");
        proceed = sc.next().toLowerCase().charAt(0);
        sc.nextLine();

        if(proceed == 'n')
            return;

        // Add customer, account to bank and branch.
        Bank.addAccount(customer.getAccount());
        Bank.addCustomer(customer);
        branch.addAccount(customer.getAccount().getAccountNo());

        System.out.println("\nCustomer added successfully");
        System.out.println("\nCustomer Credentials:");
        System.out.println("---------------------");
        System.out.println("Customer Id          : " + customer.getId());
        System.out.println("Login password       : " + customer.getPassword());
        System.out.println("Transaction password : " + customer.getAccount().getTransPassword());
    }


    public void removeCustomer() {
        Branch branch = Bank.getBranch(this.manager.getBranchIFSC());
        Account account;
        String accountNo;
        char proceed = 'n';

        Scanner sc = new Scanner(System.in);

        System.out.println("\nRemove Customer:");
        System.out.println("----------------");
        System.out.print("Enter account no: ");
        accountNo = sc.nextLine();

        // Validate customer account with branch.
        account = Bank.getAccount(accountNo);

        if(account == null) {
            System.out.println("\nInvalid account no !!!");
            return;
        }

        // Ensure account belongs to the managers branch.
        if(!account.getBranchIFSC().equals(this.manager.getBranchIFSC())) {
            System.out.println("\nAccount does not belong to this branch !!!");
            return;
        }

        System.out.println("\nConfirm details:");
        System.out.println("----------------");
        System.out.println("Account holder name : " + Bank.getCustomer(account.getCustomerId()).getName());
        System.out.println("Account No          : " + account.getAccountNo());
        System.out.println("IFSC                : " + account.getBranchIFSC());
        System.out.println("\nProceed to confirm (y/n): ");
        proceed = sc.next().toLowerCase().charAt(0);

        if(proceed == 'n')
            return;

        // Remove customer and his account.
        Bank.removeAccount(account.getAccountNo());
        branch.removeAccount(account.getAccountNo());
        Bank.removeCustomer(account.getCustomerId());

        System.out.println("\nCustomer removed successfully");
    }


    public void manageAccount(String type) {
        Account account;
        String accountNo;
        char proceed = 'n';

        Scanner sc = new Scanner(System.in);

        System.out.println("\nManage Account:" );
        System.out.println("---------------");
        System.out.print("Enter account no: ");
        accountNo = sc.nextLine();

        account = Bank.getAccount(accountNo);

        if(account == null) {
            System.out.println("\nInvalid account no !!!");
            return;
        }

        // Ensure account belongs to the managers branch.
        if(!account.getBranchIFSC().equals(this.manager.getBranchIFSC())) {
            System.out.println("\nAccount does not belong to this branch !!!");
            return;
        }

        System.out.println("\nConfirm details:");
        System.out.println("----------------");
        System.out.println("Account holder name : " + Bank.getCustomer(account.getCustomerId()).getName());
        System.out.println("Account No          : " + account.getAccountNo());
        System.out.println("\nProceed to confirm (y/n): ");
        proceed = sc.next().toLowerCase().charAt(0);
        sc.nextLine();

        if(proceed == 'n')
            return;

        if(type.equals("activate")) {
            if(account.isActive()) {
                System.out.println("\nAccount is aldready activated !!!");
                return;
            }

            account.activate();
            System.out.println("\nAccount activated successfully");
        } else {
            if(!account.isActive()) {
                System.out.println("\nAccount is aldready deactivated !!!");
                return;
            }

            account.deactivate();
            System.out.println("\nAccount deactivated successfully");    
        }
    }


    public void transactionHistory() {
        Branch branch = Bank.getBranch(this.manager.getBranchIFSC());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LinkedList<Long> transactionIds;
        Transaction transaction;
        LocalDate fromDate;
        LocalDate toDate;
        LocalDate date;
        String status;
        int cnt = 0;

        Scanner sc = new Scanner(System.in);
        
        System.out.println("\nTransaction History:");
        System.out.println("--------------------");
        System.out.print("\nEnter from date (dd/mm/yyyy) : ");
        fromDate = LocalDate.parse(sc.nextLine(), formatter);
        System.out.print("Enter to date (dd/mm/yyyy)   : ");
        toDate = LocalDate.parse(sc.nextLine(), formatter);

        if(toDate.isBefore(fromDate)) {
            System.out.println("\nInvalid date range !!!");
            return;
        }

        System.out.println("\n\nTransactions (" + formatter.format(fromDate) + ")" + " to (" + formatter.format(toDate) + "):");
        System.out.println("------------------------------------------");
        
        date = fromDate;

        while(date.isBefore(toDate) || date.isEqual(toDate)) {
            // Move to next date if no transactions were made on a date.
            if(branch.getTransactionIds(date) == null) {
                date = date.plusDays(1);
                continue;
            }

            transactionIds = branch.getTransactionIds(date);

            for(Long transId : transactionIds) {
                transaction = Bank.getTransaction(transId);
                cnt++;
                System.out.println("\n" + transaction);
                status = (transaction.isSuccessfull) ? "Successfull" : "Failed";
                System.out.println("Status         : " + status);
            }

            date = date.plusDays(1);
        }

        if(cnt == 0)
            System.out.println("\nNo transactions in given range.");
    }


    public void transferToOtherBranch() {
        Branch currentBranch = Bank.getBranch(this.manager.getBranchIFSC());
        Branch targetBranch;
        Account account;
        String accountNo;
        String targetBranchIFSC;
        char proceed = 'n';

        Scanner sc = new Scanner(System.in);

        System.out.println("\nTransfer account to other branch:" );
        System.out.println("---------------------------------");
        System.out.print("Enter account no: ");
        accountNo = sc.nextLine();
        System.out.print("Enter IFSC of the branch to transfer: ");
        targetBranchIFSC = sc.nextLine();

        account = Bank.getAccount(accountNo);
        if(account == null) {
            System.out.println("\nInvalid account no !!!");
            return;
        }

        // Ensure account belongs to the managers branch.
        if(!account.getBranchIFSC().equals(this.manager.getBranchIFSC())) {
            System.out.println("\nAccount does not belong to this branch !!!");
            return;
        }

        // Prevent transferring account to own branch.
        if(currentBranch.getIFSC().equals(targetBranchIFSC)) {
            System.out.println("\nCannot transfer account to self branch !!!");
            return;
        }

        targetBranch = Bank.getBranch(targetBranchIFSC);
        if(targetBranch == null) {
            System.out.println("\nInvalid IFSC !!!");
            return;
        }

        System.out.println("\nConfirm details:");
        System.out.println("----------------");
        System.out.println("Account holder name : " + Bank.getCustomer(account.getCustomerId()).getName());
        System.out.println("Account No          : " + account.getAccountNo());
        System.out.println("\nProceed to confirm (y/n): ");
        proceed = sc.next().toLowerCase().charAt(0);
        sc.nextLine();

        if(proceed == 'n')
            return;

        // Transfer account to other branch.
        currentBranch.removeAccount(account.getAccountNo());
        targetBranch.addAccountToPendingTransfer(accountNo);
        account.deactivate();

        System.out.println("\nAccount removed from this branch");
        System.out.println("Account transfer request sent to other branch");
    }


    public void displayPendingAccountTransfer() {
        Branch branch = Bank.getBranch(this.manager.getBranchIFSC());
        LinkedList<String> pendingTransferAccountNos = branch.getPendingAccountTransfer();
        Account account;
        int cnt = pendingTransferAccountNos.size();

        if(cnt == 0) {
            System.out.println("\nNo pending accounts for approval");
            return;
        }

        System.out.println("\nPending accounts for approval (" + cnt + "):");
        System.out.println("------------------------------------");

        for(String accountNo : pendingTransferAccountNos) {
            account = Bank.getAccount(accountNo);
            System.out.println("\n" + account);
        }
    }


    public void approveAccountTransfer() {
        Branch branch = Bank.getBranch(this.manager.getBranchIFSC());
        Customer customer;
        Account account;
        LinkedList<String> pendingAccountTransferIds;
        String accountNo;
        char proceed = 'n';

        pendingAccountTransferIds = branch.getPendingAccountTransfer();
        if(pendingAccountTransferIds.size() == 0) {
            System.out.println("\nNo pending accounts for approval");
            return;
        }

        System.out.println("\nApprove account transfer:");
        System.out.println("-------------------------");

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter account no: ");
        accountNo = sc.nextLine();

        if(!pendingAccountTransferIds.contains(accountNo)) {
            System.out.println("Invalid account no !!!");
            return;
        }

        account = Bank.getAccount(accountNo);
        customer = Bank.getCustomer(account.getCustomerId());

        System.out.println("\n-----------------");
        System.out.println("Account Details:");
        System.out.println("-----------------");
        System.out.println(account);
        System.out.println("\n-----------------");
        System.out.println("Customer Details:");
        System.out.println("-----------------");
        System.out.println(customer);

        System.out.println("\nProceed to confirm account transfer (y/n): ");
        proceed = sc.next().toLowerCase().charAt(0);
        sc.nextLine();

        if(proceed == 'n')
            return;

        // Tranfer approved.
        account.setIFSC(branch.getIFSC());
        account.activate();
        branch.addAccount(account.getAccountNo());
        branch.removeAccountFromPendingTransfer(account.getAccountNo());

        System.out.println("\nAccount Transfer approved successfully");
    }


    public void viewCustomer() {
        Customer customer;
        Account account;
        String accountNo;

        Scanner sc = new Scanner(System.in);

        System.out.println("\nView Customer:" );
        System.out.println("---------------");
        System.out.print("Enter account no: ");
        accountNo = sc.nextLine();
        
        account = Bank.getAccount(accountNo);
        if(account == null) {
            System.out.println("\nInvalid account no !!!");
            return;
        }

        customer = Bank.getCustomer(account.getCustomerId());

        // Display only restricted information of a customer if the customer
        // does not belong to this manager's branch.
        if(!customer.getAccount().getBranchIFSC().equals(this.manager.getBranchIFSC())) {
            System.out.println("\n-----------------");
            System.out.println("Customer Details:");
            System.out.println("-----------------");
            System.out.println("Name   : " + customer.getName());
            System.out.println("A/C No : " + customer.getAccount().getAccountNo()); 
            System.out.println("IFSC   : " + customer.getAccount().getBranchIFSC());
            return;
        }

        System.out.println("\n-----------------");
        System.out.println("Customer Details:");
        System.out.println("-----------------");
        System.out.println(customer);
        System.out.println("\nAccount Details:");
        System.out.println("----------------");
        System.out.println(customer.getAccount());
    }
}