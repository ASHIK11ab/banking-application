package entities.users;

public class Admin extends User {
    public Admin(String name, String phone) {
        super(name, phone);
    }

    public String toString() {
        String repr = "";
        repr += "Name        : " + this.getName() + "\n";
        repr += "Role        : Bank Admin" + "\n";
        repr += "Id          : " + this.getId() + "\n";
        repr += "Phone       : " + this.getPhone() + "\n";
        return repr;
    }
}