package de.bytephil.utils;

import de.bytephil.enums.MessageType;

public class ConsoleCommands {

    public static void handleCommand(String command) {
        if (command.toLowerCase().equalsIgnoreCase("help")) {
            Console.printout("You typed in help", MessageType.INFO);
        } else {
            Console.printout("Unknown command! Type \"help\" for help!", MessageType.INFO);
        }
    }
}
