package entities.account;

public class CurrentAccount extends Account {
    public CurrentAccount(int customerId, String IFSC) {
        super(customerId, IFSC, "current", 300000.0F);
    }
}