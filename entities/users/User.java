package entities.users;

public class User {
    private final int id;
    private String name;
    private String DOB;
    private String password;
    private String phone;

    public User(String name, String phone) {
        this.id = genUserId();
        this.name = name;
        this.password = genUserPassword();
        this.phone = phone;
    }

    private int genUserId() {
        return 1;
    }

    private String genUserPassword() {
        return "pass";
    }

    public boolean isPasswordEqual(String pass) {
        return this.password.equals(pass);
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public String getDOB() {
        return this.DOB;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }
}