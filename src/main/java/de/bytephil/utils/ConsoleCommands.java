package de.bytephil.utils;

import de.bytephil.enums.MessageType;
import de.bytephil.enums.Rank;
import de.bytephil.users.User;
import de.bytephil.users.UserService;

public class ConsoleCommands {

    public void handleCommand(String command) {
        String[] commandargs = command.split("\\s+");

        if (command.toLowerCase().equalsIgnoreCase("help")) {
            Console.printout("You typed in help", MessageType.INFO);
        } else if (commandargs[0].toLowerCase().equalsIgnoreCase("user")) {
            if (commandargs.length == 4) {
                if (commandargs[1].toLowerCase().equalsIgnoreCase("setrank")) {
                    changeRank(commandargs);
                } else {
                    Console.printout("Usage: user setrank [name] [rank]", MessageType.WARNING);
                }
            } else {
                Console.printout("Usage: user setrank [name] [rank]", MessageType.WARNING);
            }
        } else {
            Console.printout("Unknown command! Type \"help\" for help!", MessageType.INFO);
        }


    }

    private void changeRank(String[] commandargs) {
        User user = null;
        Rank rank = null;
        try {
            user = new UserService().getUserByName(commandargs[2]);
            rank = Rank.valueOf(commandargs[3].toUpperCase());
        } catch (Exception e1) {
            return;
        }
        user.setRank(rank);
        Console.printout("Changed user " + user.getName() + "'s rank to " + rank, MessageType.INFO);
    }
}
