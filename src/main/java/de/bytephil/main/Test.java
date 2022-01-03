package de.bytephil.main;

import de.bytephil.users.User;
import de.bytephil.users.UserService;
import de.bytephil.users.UserServiceImpl;

public class Test {

    public void testMethod() {
        final User user = new User("BytePhil", "passwort!");
        new UserServiceImpl().createUser(user);
    }
}
