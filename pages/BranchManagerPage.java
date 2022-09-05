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
                case 3: manageAccount("activate"); break;
                case 4: manageAccount("deactivate"); break;
                case 5: viewCustomer(); break;
                // case 2: removeCustomer(); break;
                case 7: BranchManagerView.displayProfile(this.manager); break;
                case 9: proceed = 'n'; break;
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


    // public void removeCustomer() {
    //     Branch branch = Bank.getBranch(this.manager.getBranchIFSC());
    //     Account account;
    //     Customer customer;
    //     String accountNo;
    //     char proceed = 'n';

    //     Scanner sc = new Scanner(System.in);

    //     System.out.println("\nRemove Customer:");
    //     System.out.println("----------------");
    //     System.out.print("Enter account no: ");
    //     accountNo = sc.nextLine();

    //     // Validate customer account with branch.
    //     account = Bank.getAccount(accountNo);

    //     if(account == null) {
    //         System.out.println("\nInvalid account no !!!");
    //         return;
    //     }

    //     // Ensure account belongs to the managers branch.
    //     if(!account.getBranchIFSC().equals(this.manager.getBranchIFSC())) {
    //         System.out.println("\nAccount does not belong to this branch !!!");
    //         return;
    //     }

    //     // Remove customer and his account.
    //     Bank.removeAccount(account.getAccountNo());
    //     branch.removeAccount(account.getAccountNo());
    //     Bank.removeCustomer(account.getCustomerId());
    // }


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

        System.out.println("\n-----------------");
        System.out.println("Customer Details:");
        System.out.println("-----------------");
        System.out.println(customer);
        System.out.println("\nAccount Details:");
        System.out.println(customer.getAccount());
    }
}