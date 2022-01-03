package de.bytephil.main;

import de.bytephil.users.User;

import java.io.IOException;
import java.net.URISyntaxException;

public class Boot {
    public static void main(String[] args) throws IOException, URISyntaxException {
        new Main().startUp();

        //
        // TEST
        //
        //new Test().testMethod();
    }
}
