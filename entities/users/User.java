package entities.users;

import java.util.Random;

public class User {
    private static int _counter = 10000000;
    private final int id;
    private String name;
    private String password;
    private String phone;

    public User(String name, String phone) {
        User._counter++;
        this.id = User._counter;
        this.name = name;
        this.password = genPassword();
        this.phone = phone;
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

    public String getPassword() {
        return this.password;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }
}