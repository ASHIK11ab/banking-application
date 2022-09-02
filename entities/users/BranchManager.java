package entities.users;

public class BranchManager extends User {
    private String branchIFSC;

    public BranchManager(String name, String phone, String IFSC) {
        super(name, phone);
        this.branchIFSC = IFSC;
    }

    // Getter
    public String getBranchIFSC() {
        return this.branchIFSC;
    }

    // Setter
    public void removeFromBranch() {
        this.branchIFSC = null;
    }

    public void addToBranch(String IFSC) {
        this.branchIFSC = IFSC;
    }

    public String toString() {
        String repr = "";
        repr += "Name        : " + this.getName() + "\n";
        repr += "Role        : Branch Manager" + "\n";
        repr += "Branch IFSC : " + this.getBranchIFSC() + "\n";
        repr += "Id          : " + this.getId() + "\n";
        repr += "Phone       : " + this.getPhone() + "\n";
        return repr;
    }
}