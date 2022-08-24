package entities.account;

public class CurrentAccount extends Account {
    public CurrentAccount(int branchId) {
        super(branchId, 300000.0F);
    }
}