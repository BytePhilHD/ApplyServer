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
            if (commandargs.length >= 2) {
                if (commandargs[1].toLowerCase().equalsIgnoreCase("setrank")) {
                    if (commandargs.length == 4) {
                        changeRank(commandargs);
                    } else {
                        Console.printout("Usage: user setrank [name] [rank]", MessageType.WARNING);
                    }
                } else if (commandargs[1].toLowerCase().equalsIgnoreCase("getrank")) {
                    if (commandargs.length == 3) {
                        getRank(commandargs);
                    } else {
                        Console.printout("Usage: user getrank [name]", MessageType.WARNING);
                    }
                } else if (commandargs[1].toLowerCase().equalsIgnoreCase("create")) {
                    if (commandargs.length == 5) {
                        String pw = BCrypt.hashpw(commandargs[3], BCrypt.gensalt(12));
                        new UserService().createUser(new User(commandargs[2], pw, commandargs[4], Rank.USER));
                        Console.printout("Succesfully added new user " + commandargs[3] + "!", MessageType.INFO);
                    } else {
                        Console.printout("Usage: user create [name] [passwort] [email]", MessageType.WARNING);
                    }
                } else {
                    Console.printout("Usage: user [setrank/getrank/create]", MessageType.WARNING);
                }
            } else {
                Console.printout("Usage: user [setrank/getrank/create]", MessageType.WARNING);
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
        new UserService().saveUser(user);

        Console.printout("Changed user " + user.getName() + "'s rank to " + user.getRank(), MessageType.INFO);
    }

    private void getRank(String[] commandargs) {
        User user = null;
        try {
            user = new UserService().getUserByName(commandargs[2]);
        } catch (Exception e1) {
            return;
        }
        Console.printout("User " + user.getName() + "'s rank is currently " + user.getRank(), MessageType.INFO);
    }
}
