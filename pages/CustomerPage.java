package pages;

import java.util.ArrayList;
import java.util.Scanner;

import entities.Bank;
import entities.Beneficiary;
import entities.Transaction;
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
                case 1: this.fundTransfer(); break;
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
            System.out.println("Add beneficiaries to transer funds !!!");
            return;
        }

        System.out.println("\nFund Transfer:");
        System.out.println("--------------");
        System.out.print("Enter beneficiary no ('0' to display added beneficiaries): ");
        beneficiaryNo = sc.nextInt();

        if(beneficiaryNo == 0) {
            this.displayBeneficiaries();
            System.out.print("\nEnter beneficiary no: ");
            beneficiaryNo = sc.nextInt();
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
        
        // Validate beneficiary account.
        if(!(beneficiaryNo > 0 && 
                beneficiaryNo <= customerAccount.getBeneficiaries().size()) ) {
            System.out.println("\n\nInvalid Beneficiary no !!!");
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
        transaction = new Transaction(customerAccount, beneficiaryAccount, 
                                        amount, isSuccessfull);
        Bank.addTransaction(transaction);

        // Add transaction record to the involving branches.
        // Branch payerBranch = Bank.getBranch(customerAccount.getBranchIFSC());
        // Branch payeeBranch = Bank.getBranch(beneficiaryAccount.getBranchIFSC());
        // if(payerBranch == payeeBranch)
        //     payerBranch.addTransaction(transaction);
        // else {
        //     payerBranch.addTransaction(transaction);
        //     payeeBranch.addTransaction(transaction);
        // }

        customerAccount.addTransaction(transaction);
        beneficiaryAccount.addTransaction(transaction);
        System.out.println(customerAccount.getTransactions());
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