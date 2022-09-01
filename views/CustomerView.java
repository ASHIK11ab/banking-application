package views;

import java.util.ArrayList;
import java.util.Scanner;

import entities.users.Customer;
import entities.account.Account;
import entities.Beneficiary;

public class CustomerView {
    public static int displayActions() {
        int choice;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\nActions:");
        System.out.println("-------------------");
        System.out.println("1. Fund Transfer");
        System.out.println("2. Add beneficiary");
        System.out.println("3. Remove beneficiary");
        System.out.println("4. Balance enquiry");
        System.out.println("5. All beneficiaries");
        System.out.println("6. Your profile");
        System.out.println("7. Account details");
        System.out.println("8. History of transactions");
        System.out.println("9. Mini statement");
        System.out.println("10. Logout");
        System.out.print("\nEnter choice: ");
        choice = sc.nextInt();
        return choice;
    }


    public static void displayBeneficiaries(ArrayList<Beneficiary> beneficiaries) {
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


    public static void displayProfile(Customer customer) {
        System.out.println("\nProfile:");
        System.out.println("-------------");
        System.out.println(customer);
    }


    public static void displayAccount(Account account) {
        System.out.println("\nAccount Details:");
        System.out.println("---------------------");
        System.out.println(account);
    }

    
    public static void displayBalance(Account account) {
        System.out.println("\nBalance: ");
        System.out.println("--------"); 
        System.out.println(account.getBalance());
    }
}