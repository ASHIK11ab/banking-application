package entities.account;

public class CurrentAccount extends Account {
    public CurrentAccount(int customerId, int branchId) {
        super(customerId, branchId, 300000.0F);
    }
}