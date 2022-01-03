package de.bytephil.users;

import java.util.List;

public interface UserService {

    void createUser(User user);

    void saveUser(User user);

    User getUserByIdentifier(String identifier);

    User getUserByName(String name);

    User getUser(String id);

    boolean existsUserByIdentifier(String identifier);

    boolean existsUserByName(String name);

    boolean existsUser(String id);

    void deleteUser(String id);

    List<User> getUsers();
}
