package de.bytephil.users;


import de.bytephil.enums.MessageType;
import de.bytephil.utils.Console;
import org.json.JSONObject;

import javax.annotation.processing.FilerException;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserService {

    private final List<User> users = new ArrayList<>();

    public void loadAllUsers(File space) {
        Arrays.stream(Objects.requireNonNull(space.listFiles())).forEach(file -> {
            final JSONObject jsonUser = load(file);
            final JSONObject jsonAccesses = jsonUser.getJSONObject("accesses");

            final String name = file.getName().replaceFirst(".json", "");

            if (name.contains("%") || name.contains("#") || name.contains("'")) {
                Console.printout("Error whilst trying to read users!", MessageType.ERROR);
            } else {
                final User user = new User(name, jsonUser.getString("name"), jsonUser.getString("password"));

                if (!user.getName().contains("%") && !user.getName().contains("#") && !user.getName().contains("'"))
                    users.add(user);
                else
                    Console.printout("Error at UserService!", MessageType.ERROR);
            }
        });
    }

    public JSONObject load(File file) {
        try {
            FileReader reader = new FileReader(file);
            StringBuilder builder = new StringBuilder();

            int read;

            while ((read = reader.read()) != -1) {
                builder.append((char) read);
            }

            reader.close();

            return new JSONObject(builder.toString());
        } catch (Exception ex) {
            return null;
        }
    }

}

