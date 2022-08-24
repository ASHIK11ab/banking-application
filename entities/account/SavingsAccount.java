package entities.account;

public class SavingsAccount extends Account {
    private static float depositIntrestRate;

    public SavingsAccount(int customerId, int branchId) {
        super(customerId, branchId, 40000.0F);
        SavingsAccount.depositIntrestRate = 0.035F;
    }

    // Getter
    public float getIntrestRate() {
        return SavingsAccount.depositIntrestRate;
    }

    // Setter
    public void setIntrestRate(float intrestRate) {
        SavingsAccount.depositIntrestRate = intrestRate;
    }
}