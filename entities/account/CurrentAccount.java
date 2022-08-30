package entities.account;

public class CurrentAccount extends Account {
    public CurrentAccount(int customerId, String IFSC) {
        super(customerId, IFSC, "current");
    }


    @Override
    public int isTransactionValid(float amount) {
        // Insufficient balance.
        if(amount > this.getBalance())
            return 401;

        return 200;
    }
}