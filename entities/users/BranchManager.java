package entities.users;

public class BranchManager extends User {
    private int branchId;

    public BranchManager(String name, String phone, int branchId) {
        super(name, phone);
        this.branchId = branchId;
    }

    public String toString() {
        String repr = "";
        repr += "Name: " + this.getName() + "\n";
        repr += "Role: Branch Manager" + "\n";
        repr += "Branch Id: " + this.getBranchId() + "\n";
        repr += "Id: " + this.getId() + "\n";
        repr += "Phone: " + this.getPhone() + "\n";
        repr += "DOB: " + this.getDOB() + "\n";
        return repr;
    }

    // Getter
    public int getBranchId() {
        return this.branchId;
    }

    // Setter
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}
