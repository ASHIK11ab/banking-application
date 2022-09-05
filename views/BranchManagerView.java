package views;

import java.util.Scanner;

import entities.users.BranchManager;

public class BranchManagerView {
    public static int displayActions() {
        int choice;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\nActions:");
        System.out.println("-------------------");
        System.out.println("1. Add customer");
        System.out.println("2. Remove customer");
        System.out.println("3. Activate account");
        System.out.println("4. Deactivate account");
        System.out.println("5. View customer details");
        System.out.println("7. Your Profile");
        System.out.println("8. Transaction history");
        System.out.println("9. Logout");
        System.out.print("\nEnter choice: ");
        choice = sc.nextInt();
        return choice;
    }


    public static void displayProfile(BranchManager manager) {
        System.out.println("\nProfile:");
        System.out.println("-------------");
        System.out.println(manager);
    }
}