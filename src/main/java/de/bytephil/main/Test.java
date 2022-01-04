package de.bytephil.main;

import de.bytephil.users.Application;
import de.bytephil.users.ApplicationService;
import de.bytephil.users.User;
import de.bytephil.users.UserService;

public class Test {

    public void testMethod() {

        final Application application = new Application("BytePhil", "phitho@gmail.com", "Dev", "Ich bin der Philipp und will mich bewerben!");
        new ApplicationService().createApplication(application);

        Application test = new ApplicationService().getApplicationByName("BytePhil");
        System.out.println("Application: " + test.getName() + ", " + test.getEmail() + ", " + test.getJob() + ", " + test.getApplication());
    }
}
