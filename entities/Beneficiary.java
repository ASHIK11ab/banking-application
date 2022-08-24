package entities;

public class Beneficiary {
    private String accountNo;
    private String IFSC;
    private String bankName;
    private String name;

    public Beneficiary(String account, String IFSC, String bankName, String name) {
        this.accountNo = account;
        this.IFSC = IFSC;
        this.bankName = bankName;
        this.name = name;
    }

    // Getters
    public String getBankName() {
        return this.bankName;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public String getIFSC() {
        return this.IFSC;
    }

    public String getName() {
        return this.name;
    }

    // Setters
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setIFSC(String IFSC) {
        this.IFSC = IFSC;
    }

    public void setName(String name) {
        this.name = name;
    }
}