package entities;

import java.util.LinkedHashSet;

public class Branch {
    private static int _counter;
    private final String IFSC;
    private String name;
    private int managerId;
    private LinkedHashSet<String> accounts;

    public Branch(String name) {
        Branch._counter++;
        this.name = name;
        this.IFSC = genBranchIFSC();
        this.managerId = -1;
        this.accounts = new LinkedHashSet<String>();
    }

    private String genBranchIFSC() {
        return String.format("YESB0%06d", _counter);
    }

    public void addAccount(String accountNo) {
        this.accounts.add(accountNo);
    }

    // Getters
    public String getIFSC() {
        return this.IFSC;
    }

    public String getName() {
        return this.name;
    }

    public int getManagerId() {
        return this.managerId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setManager(int id) {
        this.managerId = id;
    }
}