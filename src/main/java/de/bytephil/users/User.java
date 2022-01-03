package de.bytephil.users;

import de.bytephil.utils.PasswordGenerator;

public class User {

    private String id;
    private String name;
    private String password;

    public User(String name, String password) {
        this.id = PasswordGenerator.generateID(10);
        this.name = name;
        this.password = password;
    }

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }
}
