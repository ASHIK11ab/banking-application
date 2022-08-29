package pages;

import java.util.ArrayList;
import java.util.Scanner;

import entities.Bank;
import entities.Beneficiary;
import entities.account.Account;
import entities.users.Customer;


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
            System.out.println("\nActions:");
            System.out.println("-------------------");
            System.out.println("1. Fund Transfer");
            System.out.println("2. Balance enquiry");
            System.out.println("3. Add beneficiary");
            System.out.println("4. Remove beneficiary");
            System.out.println("5. All beneficiaries");
            System.out.println("6. Your profile");
            System.out.println("7. Account details");
            System.out.println("8. History of transactions");
            System.out.println("9. Logout");
            System.out.print("\nEnter choice: ");
            choice = sc.nextInt();

            switch(choice) {
                case 2: this.displayBalance(); break;
                case 3: this.addBeneficiary(); break;
                case 4: this.removeBeneficiary(); break;
                case 5: this.displayBeneficiaries(); break;
                case 6: this.displayProfile(); break;
                case 7: this.displayAccount(); break;
                case 9: proceed = 'n'; break;
            }

            System.out.print("\nPress enter to continue ... ");
            System.console().readLine();
        }
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
        System.out.print("\nBeneficiary name: ");
        name = System.console().readLine();

        if(!accountNo.equals(confirmAccountNo)) {
            System.out.println("\nAccount no does not match !!!");
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

        this.displayBeneficiaries();
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


    public void displayBeneficiaries() {
        ArrayList<Beneficiary> beneficiaries = this.customer.getAccount().getBeneficiaries();
        
        if(beneficiaries.size() == 0) {
            System.out.println("\nNo beneficiaries added !!!");
            return;
        }

        int counter = 1;
        System.out.println("\nBeneficiaries:");
        System.out.println("--------------");
        for(Beneficiary beneficiary : beneficiaries) {
            System.out.println("Beneficiary No : " + counter);
            System.out.println(beneficiary);
            counter++;
        }
    }


    public void displayProfile() {
        System.out.println("\nProfile:");
        System.out.println("-------------");
        System.out.println(this.customer);
    }


    public void displayAccount() {
        System.out.println("\nAccount Details:");
        System.out.println("---------------------");
        System.out.println(this.customer.getAccount());
    }

    
    public void displayBalance() {
        System.out.println("\nBalance: ");
        System.out.println("--------"); 
        System.out.println(this.customer.getAccount().getBalance());
    }
}