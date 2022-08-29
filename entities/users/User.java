package entities.users;

import java.util.Random;

public class User {
    private static int _counter = 10000000;
    // id is final, for testing purposes made as not final.
    private int id;
    private String name;
    private String DOB;
    private String password;
    private String phone;

    public User(String name, String phone) {
        User._counter++;
        this.id = genUserId();
        this.name = name;
        this.password = genPassword();
        this.phone = phone;
    }

    private int genUserId() {
        return User._counter;
    }

    private String genPassword() {
        String pass = "";
        Random random = new Random();
        for(byte i = 0; i < 4; ++i)
            pass += (char) ('a' + random.nextInt(26));

        for(byte i = 0; i < 4; ++i)
            pass += random.nextInt(10);

        return pass;
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
    public void _setUserId(int id) {
        this.id = id;
    }

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