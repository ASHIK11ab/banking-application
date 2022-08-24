package entities.account;

public class SavingsAccount extends Account {
    private static float depositIntrestRate;

    public SavingsAccount(int branchId) {
        super(branchId, 40000.0F);
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