package de.bytephil.main;

import de.bytephil.users.User;
import de.bytephil.users.UserService;

public class Test {

    public void testMethod() {
        final User user = new User("BytePhil", "passwort!", "phitho@gmail.com");
        new UserService().createUser(user);
        User test = new UserService().getUserByName("BytePhil");
        System.out.println(test.getName() + " PW: " + test.getPassword());
    }
}
