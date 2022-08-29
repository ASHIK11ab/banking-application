import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import entities.Bank;
import entities.users.Admin;
import entities.users.BranchManager;
import entities.users.Customer;
import entities.users.User;
import pages.CustomerPage;
import entities.Branch;

public class App {
    App() {
        Admin admin = new Admin("admin-user", "899879879");
        admin._setUserId(111);
        admin.setPassword("admin");

        Bank.setAdmin(admin);

        Branch branch = new Branch("Poonamallee");
        Bank.addBranch(branch);

        BranchManager manager = new BranchManager("Jason", "998757334", branch.getIFSC());
        manager._setUserId(3);
        manager.setPassword("manager1");
        Bank.addManager(manager);
        manager.addToBranch(branch.getIFSC());

        Customer customer = new Customer("Test 1", "937957495", "savings", branch.getIFSC(), "ADPA94757", "489571973264");
        customer._setUserId(1);
        customer.setLoginPassword("test1");
        customer.setTransPassword("test1@trans");

        Bank.addAccount(customer.getAccount());
        branch.addAccount(customer.getAccount().getAccountNo());
        Bank.addCustomer(customer);

        Customer customer2 = new Customer("Test 2", "21235874", "current", branch.getIFSC(), "QWT937432", "832935712347");
        customer2._setUserId(2);
        customer2.setLoginPassword("test2");
        customer2.setTransPassword("test2@trans");

        Bank.addAccount(customer2.getAccount());
        branch.addAccount(customer2.getAccount().getAccountNo());
        Bank.addCustomer(customer2);

        // System.out.println(branch);
        // System.out.println(customer);
        // System.out.println(customer.getAccount());
        // System.out.println(customer2);
        // System.out.println(customer2.getAccount());
    }

    public static void main(String[] args) throws IOException {
        App app = new App();
        System.out.println("\nWelcome to 'YOUR' bank");
        System.out.println("----------------------\n");
        app.login();
    }

    
    public void login() throws IOException {
        int choice = 0;
        int userId;
        String password;
        User user = null;        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(choice != 4) {
            System.out.println("\nMenu's:");
            System.out.println("-------------------");
            System.out.println("1. Admin Login");
            System.out.println("2. Manager Login");
            System.out.println("3. Customer Login");
            System.out.println("4. Exit");
            System.out.print("\nEnter choice: ");
            choice = Integer.parseInt(reader.readLine());

            if(choice == 4)
                continue;

            if(choice < 1 || choice > 3) {
                System.out.println("Invalid choice");
                return;
            }

            System.out.print("\nEnter user id: ");
            userId = Integer.parseInt(reader.readLine());

            System.out.print("Enter password: ");
            password = reader.readLine();

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
                reader.readLine();

                switch(choice) {
                    // case 1: page = new AdminPage(user.getId());
                    //         page.dashboard(); break;
                    // case 2: page = new ManagerPage(user.getId());
                    //         page.dashboard(); break;
                    case 3: CustomerPage customerPage = new CustomerPage(user.getId());
                            customerPage.dashboard(); break;
                }
            } else {
                System.out.println("Invalid user id or password !!!");
                System.out.print("\nPress enter to continue ... ");
                reader.readLine();
            }
        }
    }
}