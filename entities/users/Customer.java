package entities.users;

import entities.account.*;

public class Customer extends User {
    private Account account;
    private String PAN;
    private String ADHAAR;

    public Customer(String name, String phone, String accountType, 
                    String IFSC, String PAN, String ADHAAR) {

        super(name, phone);
        if(accountType.equals("savings"))
            this.account = new SavingsAccount(this.getId(), IFSC);
        else
            this.account = new CurrentAccount(this.getId(), IFSC);

        this.PAN = PAN;
        this.ADHAAR = ADHAAR;
    }

    public String toString() {
        String repr = "";
        repr += "Name: " + this.getName() + "\n";
        repr += "Id: " + this.getId() + "\n";
        repr += "Phone: " + this.getPhone() + "\n";
        repr += "DOB: " + this.getDOB() + "\n";
        repr += "\nPAN No: " + this.getPAN() + "\n";
        repr += "ADHAAR No: " + this.getADHAAR() + "\n";
        repr += this.account;
        return repr;
    }

    // Getters
    // Use account reference to access account's attributes.
    public Account getAccount() {
        return this.account;
    }

    public String getPAN() {
        return this.PAN;
    }

    public String getADHAAR() {
        return this.ADHAAR;
    }

    // Setters
    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public void setADHAAR(String ADHAAR) {
        this.ADHAAR = ADHAAR;
    }

    public void setLoginPassword(String password) {
        this.setPassword(password);
    }

    public void setTransPassword(String password) {
        this.getAccount().setTransPassword(password);
    }
}