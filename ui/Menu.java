package ui;

import game.Board;
import game.Game;
import game.Player;
import game.Setting;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static ui.Input.safeInputInt;
import static ui.Input.safeInputString;

/**
 * Menu:
 * A class containing menu methods show menus, read inputs and process it.
 */
public class Menu {

    /**
     * Print the main menu, read the player's input, process it and return a game object.
     * @return The game will be bound to the control object later
     */
    public static Game mainMenu() {
        String logo = """
                                       ___           ___           ___                       ___           ___           ___       ___    \s
                                      /\\  \\         /\\  \\         /\\  \\          ___        /\\  \\         /\\  \\         /\\__\\     /\\  \\   \s
                                     /::\\  \\       /::\\  \\       /::\\  \\        /\\  \\      /::\\  \\       /::\\  \\       /:/  /    /::\\  \\  \s
                                    /:/\\ \\  \\     /:/\\:\\  \\     /:/\\:\\  \\       \\:\\  \\    /:/\\:\\  \\     /:/\\:\\  \\     /:/  /    /:/\\:\\  \\ \s
                                   _\\:\\~\\ \\  \\   /:/  \\:\\  \\   /::\\~\\:\\  \\      /::\\__\\  /::\\~\\:\\__\\   /::\\~\\:\\__\\   /:/  /    /::\\~\\:\\  \\\s
                                  /\\ \\:\\ \\ \\__\\ /:/__/ \\:\\__\\ /:/\\:\\ \\:\\__\\  __/:/\\/__/ /:/\\:\\ \\:|__| /:/\\:\\ \\:|__| /:/__/    /:/\\:\\ \\:\\__\\
                                  \\:\\ \\:\\ \\/__/ \\:\\  \\  \\/__/ \\/_|::\\/:/  / /\\/:/  /    \\:\\~\\:\\/:/  / \\:\\~\\:\\/:/  / \\:\\  \\    \\:\\~\\:\\ \\/__/
                                   \\:\\ \\:\\__\\    \\:\\  \\          |:|::/  /  \\::/__/      \\:\\ \\::/  /   \\:\\ \\::/  /   \\:\\  \\    \\:\\ \\:\\__\\ \s
                                    \\:\\/:/  /     \\:\\  \\         |:|\\/__/    \\:\\__\\       \\:\\/:/  /     \\:\\/:/  /     \\:\\  \\    \\:\\ \\/__/ \s
                                     \\::/  /       \\:\\__\\        |:|  |       \\/__/        \\::/__/       \\::/__/       \\:\\__\\    \\:\\__\\   \s
                                      \\/__/         \\/__/         \\|__|                     ~~            ~~            \\/__/     \\/__/   \s
                             
                \n""";
        System.out.print(logo);
        System.out.println("---------------------------------------------");
        System.out.println("                  Main Menu                  ");
        System.out.println("---------------------------------------------");
        System.out.println("1. New Game");
        System.out.println("2. Load a Game");
        System.out.println("3. Exit");
        System.out.println("---------------------------------------------");
        System.out.print("Please input a number as a command ");
        int option = Input.safeInputInt(new int[]{1,2,3});
        switch (option) {
            case 1 -> {
                return settingMenu();
            }
            case 2 -> {
                try {
                    return loadGameMenu();
                } catch (IOException | ClassNotFoundException e) {
                    AdvancedPrinter.printErrLn("No saving to load or Can not load files, restart or reinstall the game.");
                    return mainMenu();
                }
            }
            case 3 -> {
                System.out.println("Saving data...");
                System.out.println("Closing the game...");
                System.out.println("Bye-bye!");
                return null;
            }
        }
        return null;
    }

    /**
     * Print the setting menu, read the player's input, process it and return a setting object.
     * @return The customized setting
     */
    public static Game settingMenu() {
        Setting setting = new Setting(60, 180, true, 2, 200);
        String changeOrNot = "y";
        while (changeOrNot.equalsIgnoreCase("y")) {
            System.out.println("---------------------------------------------");
            System.out.println("              New Game Settings              ");
            System.out.println("---------------------------------------------");
            System.out.println("1. The number of players is " + setting.getNumOfPlayer() + ".");
            if(setting.getTimeLimitForGame()==-1) System.out.println("2. The time limitation of games is NO LIMIT.");
            else System.out.println("2. The time limitation of games is " + setting.getTimeLimitForGame() + " minutes.");
            System.out.println("3. The time limitation of a turn is "+ setting.getTimeLimitForATurn() + " seconds.");
            System.out.println("4. If this game contains special places: " + setting.getIfContainsSpecialPlace() + ".");
            System.out.println("5. The target score of the game is " + setting.getTargetScore() + "." );
            System.out.println("---------------------------------------------");
            System.out.println("Do you want to make some changes? (y/n)");
            changeOrNot = safeInputString(new String[]{"y","n"});
            if (changeOrNot.equalsIgnoreCase("y")) {
                System.out.println("Enter the number of the setting you want to change.");
                int choice = safeInputInt(1,5);
                switch (choice) {
                    case 1 -> {
                        System.out.println("Enter the new number of players (2~4)");
                        setting.setNumOfPlayer(safeInputInt(2, 4));
                    }
                    case 2 -> {
                        System.out.println("Enter the new time limitation of games (in minutes)");
                        AdvancedPrinter.printHintLn("Range: 1 ~ 10000");
                        setting.setTimeLimitForGame(safeInputInt(1,10000));
                    }
                    case 3 -> {
                        System.out.println("Enter the new time limitation of a turn (in seconds)");
                        AdvancedPrinter.printHintLn("Range: 1 ~ 10000");
                        setting.setTimeLimitForATurn(safeInputInt(1,10000));
                    }
                    case 4 -> {
                        System.out.println("Enter t(true)/f(false) if this game contains special places");
                        setting.setIfContainsSpecialPlace(Boolean.parseBoolean(safeInputString(new String[]{"t", "f"})));
                    }
                    case 5 -> {
                        System.out.println("Enter the new target score of the game");
                        AdvancedPrinter.printHintLn("range: 100 ~ 10000");
                        setting.setTargetScore(safeInputInt(100,10000));
                    }
                    default -> AdvancedPrinter.printErrLn("Invalid choice. No changes made.");
                }
            }
        }
        Board board = new Board(setting.getIfContainsSpecialPlace());
        Player[] players = new Player[setting.getNumOfPlayer()];
        for (int i = 0; i < setting.getNumOfPlayer(); i++) {
            boolean ifrepeat = true;
            while (ifrepeat) {
                System.out.println("Enter name for Player " + (i + 1) + ":");
                String name = safeInputString();
                boolean nameExists = false;
                for (int j = 0; j < i; j++) {
                    if (players[j] != null && players[j].toString().equals(name)) {
                        nameExists = true;
                        break;
                    }
                }
                if (nameExists) {
                    AdvancedPrinter.printErrLn("This name is already used, please try another name.");
                } else {
                    players[i] = new Player(name);
                    ifrepeat = false;
                }
            }
        }


        return new Game(setting, players, board);

    }

    /**
     * Print the menu to load a game, read the player's input process it and return the selected game object.
     * @return The selected game
     */
    public static Game loadGameMenu() throws IOException, ClassNotFoundException {
        File[] savings = Game.loadGamesFromDirectory();
        System.out.println("---------------------------------------------");
        System.out.println("                   Savings                   ");
        System.out.println("---------------------------------------------");
        if (savings.length == 0) {
            System.out.println("No saving available.");
            throw new IOException();
        } else {
            for (int i = 0; i < savings.length; i++) {
                System.out.println((i + 1) + " " + savings[i].getName());
            }
            System.out.println("---------------------------------------------");
            System.out.println("Choose a saving to load (By the order)");
            File saving = savings[safeInputInt(1,savings.length)-1];
            return Game.loadGame(saving);
        }

    }
}
