import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import entities.Bank;
import entities.users.Admin;
import entities.users.BranchManager;
import entities.users.Customer;
import entities.users.User;
import pages.CustomerPage;
import entities.Branch;
import entities.Transaction;
import entities.account.Account;

public class App {
    App() {
        Admin admin = new Admin("admin-user", "899879879");
        admin.setPassword("admin");
        Bank.setAdmin(admin);

        Branch branch = new Branch("Poonamallee");
        Bank.addBranch(branch);

        BranchManager manager = new BranchManager("Jason", "998757334", branch.getIFSC());
        manager.setPassword("manager1");
        Bank.addManager(manager);
        manager.addToBranch(branch.getIFSC());

        Customer customer = new Customer("Test 1", "937957495", "savings", branch.getIFSC(), "ADPA94757", "489571973264");
        customer.setLoginPassword("test");
        customer.setTransPassword("test@trans");

        Bank.addAccount(customer.getAccount());
        branch.addAccount(customer.getAccount().getAccountNo());
        Bank.addCustomer(customer);

        Customer customer2 = new Customer("Test 2", "21235874", "current", branch.getIFSC(), "QWT937432", "832935712347");
        customer2.setLoginPassword("test");
        customer2.setTransPassword("test@trans");

        Bank.addAccount(customer2.getAccount());
        branch.addAccount(customer2.getAccount().getAccountNo());
        Bank.addCustomer(customer2);

        // Sample transaction data.
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd:MM:yyyy");
        Transaction t1 = new Transaction(customer.getAccount(), customer2.getAccount(), 100.00F, true, LocalDate.parse("30:06:2022", fmt), LocalTime.now());
        Transaction t2 = new Transaction(customer2.getAccount(), customer.getAccount(), 200.00F, true, LocalDate.parse("10:07:2022", fmt), LocalTime.now());
        Transaction t3 = new Transaction(customer.getAccount(), customer2.getAccount(), 150.00F, true, LocalDate.parse("15:07:2022", fmt), LocalTime.now());
        Transaction t4 = new Transaction(customer.getAccount(), customer2.getAccount(), 100.00F, true, LocalDate.parse("20:07:2022", fmt), LocalTime.now());

        Account account = customer.getAccount();
        Account account2 = customer2.getAccount();

        Bank.addTransaction(t1);
        branch.addTransactionId(t1);
        account.addTransaction(t1);
        account2.addTransaction(t1);

        Bank.addTransaction(t2);
        branch.addTransactionId(t2);
        account.addTransaction(t2);
        account2.addTransaction(t2);
        
        Bank.addTransaction(t3);
        branch.addTransactionId(t3);
        account.addTransaction(t3);
        account2.addTransaction(t3);
        
        Bank.addTransaction(t4);
        branch.addTransactionId(t4);
        account.addTransaction(t4);
        account2.addTransaction(t4);
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println("\nWelcome to 'YOUR' bank");
        System.out.println("----------------------\n");
        app.login();
    }

    
    public void login() {
        int choice = 0;
        int userId;
        String password;
        User user = null;        
        Scanner sc = new Scanner(System.in);

        while(choice != 4) {
            System.out.println("\nMenu's:");
            System.out.println("-------------------");
            System.out.println("1. Admin Login");
            System.out.println("2. Manager Login");
            System.out.println("3. Customer Login");
            System.out.println("4. Exit");
            System.out.print("\nEnter choice: ");
            choice = sc.nextInt();

            if(choice == 4)
                continue;

            if(choice < 1 || choice > 3) {
                System.out.println("Invalid choice");
                return;
            }

            System.out.print("\nEnter user id: ");
            userId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter password: ");
            password = sc.nextLine();

            switch(choice) {
                case 1: user = (User) Bank.getAdmin(); break;
                case 2: user = (User) Bank.getManager(userId); break;
                case 3: user = (User) Bank.getCustomer(userId); break;
            }

            // Additional id check is done since Admin reference is always retrieved.
            // Fixes the issue of being able to log in as admin by only knowing 
            // the password.
            if(user != null && user.getId() == userId && user.isPasswordEqual(password)) {
                System.out.println("\nLogin successfull");
                System.out.print("\nPress enter to continue ... ");
                System.console().readLine();

                switch(choice) {
                    case 3: CustomerPage customerPage = new CustomerPage(user.getId());
                            customerPage.dashboard(); break;
                }
            } else {
                System.out.println("Invalid user id or password !!!");
                System.out.print("\nPress enter to continue ... ");
                System.console().readLine();
            }
        }
    }
}