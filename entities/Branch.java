package entities;

public class Branch {
    private final String IFSC;
    private String name;
    private int managerId;

    public Branch(String name) {
        this.name = name;
        this.IFSC = genBranchIFSC();
        this.managerId = -1;
    }

    public String genBranchIFSC() {
        return "IFSC";
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