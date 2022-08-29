import entities.Bank;
import entities.users.Admin;
import entities.users.BranchManager;
import entities.users.Customer;
import entities.Branch;

public class App {
    App() {
        Admin admin = new Admin("admin-user", "899879879");

        Bank.setAdmin(admin);

        Branch branch = new Branch("Poonamallee");
        Bank.addBranch(branch);

        BranchManager manager = new BranchManager("Jason", "998757334", branch.getIFSC());
        Bank.addManager(manager);
        manager.addToBranch(branch.getIFSC());

        Customer customer = new Customer("Test 1", "937957495", "savings", branch.getIFSC(), "ADPA94757", "489571973264");
        Bank.addAccount(customer.getAccount());
        branch.addAccount(customer.getAccount().getAccountNo());
        Bank.addCustomer(customer);

        Customer customer2 = new Customer("Test 2", "21235874", "current", branch.getIFSC(), "QWT937432", "832935712347");
        Bank.addAccount(customer2.getAccount());
        branch.addAccount(customer2.getAccount().getAccountNo());
        Bank.addCustomer(customer2);

        // System.out.println(branch);
        // System.out.println(customer);
        // System.out.println(customer.getAccount());
        // System.out.println(customer2);
        // System.out.println(customer2.getAccount());
    }
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        System.out.println("Welcome to 'YOUR' bank");
        System.out.println("----------------------\n");
    }
}