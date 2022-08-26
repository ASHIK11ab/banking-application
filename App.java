import entities.Bank;
import entities.users.Admin;
import entities.users.BranchManager;
import entities.users.Customer;
import entities.Branch;

public class App {
    private Bank bank;

    App() {
        Admin admin = new Admin("admin-user", "899879879");

        Bank bank = new Bank(admin);
        Branch branch = new Branch("Poonamallee");
        bank.addBranch(branch);

        BranchManager manager = new BranchManager("Jason", "998757334", branch.getIFSC());
        bank.addManager(manager);
        manager.addToBranch(branch.getIFSC());

        Customer customer = new Customer("Test 1", "937957495", "savings", branch.getIFSC(), "ADPA94757", "489571973264");

        bank.addAccount(customer.getAccount());
        branch.addAccount(customer.getAccount().getAccountNo());
        bank.addCustomer(customer);

        this.bank = bank;
    }
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        System.out.println("Welcome to 'YOUR' bank");
        System.out.println("----------------------\n");

        System.out.println(this.bank);
    }
}