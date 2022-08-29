package entities;

public class Beneficiary {
    private String accountNo;
    private String IFSC;
    private String name;

    public Beneficiary(String account, String IFSC, String name) {
        this.accountNo = account;
        this.IFSC = IFSC;
        this.name = name;
    }

    public String toString() {
        String repr = "";
        repr += "Name           : " + this.getName() + "\n";
        repr += "Account No     : " + this.getAccountNo() + "\n";
        repr += "IFSC Code      : " + this.getIFSC() + "\n";
        return repr;
    }

    // Getters
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