package game;

import ui.AdvancedPrinter;
import ui.Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Control:
 * A class to control the game whole process and change data in the game object.
 */
public class Control {
    // The game bound with this control object
    public final Game currentGame;
    // The flag variable to show if the game is running
    private boolean running = true;
    // The number of tiles on board
    public int numOfTileOnBoard = 0;
    // The thread to countdown on a turn and the whole game
    private Timer timerThread;

    // Timer thread definition
    private class Timer extends Thread {
        private boolean isCounting = true;

        public void stopTimer() {
            if (isCounting) isCounting = false;
        }

        public void continueTimer() {
            if (!isCounting) isCounting = true;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(1000);
                    if (isCounting) {
                        currentGame.setTurnTimeCount(currentGame.getTurnTimeCount() + 1);
                        currentGame.setGameTimeCount(currentGame.getGameTimeCount() + 1);
                    }
                    if (currentGame.getGameTimeCount() == currentGame.getSettings().getTimeLimitForGame() * 60) {
                        running = false;
                        System.out.println("\n\nTime out! Game over.\n");
                        settleGame();
                    } else if (currentGame.getTurnTimeCount() == currentGame.getSettings().getTimeLimitForATurn()) {
                        currentGame.nextActivePlayer();
                        currentGame.setContinuouslyShuffleCount(0);
                        System.out.println("\n\nTime out! Change active player to " + currentGame.getActivePlayer() + ".\n");
                        printTurnUI();
                        AdvancedPrinter.printHintLn("If you want to choose option 1 ,please observe the following format for input: \"1 A(x,y), B(x,y), C(x,y)\"");
                        System.out.print("Input >> ");
                        currentGame.resetTurnTimeCount();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // Listener thread definition to give, read and process options in a player's turn
    private class Listener extends Thread {
        @Override
        public void run() {
            while (running) {
                printTurnUI();
                processCommand();
                currentGame.nextActivePlayer();
                currentGame.resetTurnTimeCount();
                if (ifReachGameCondition()) {
                    System.out.println("Game Over.");
                    running = false;
                    settleGame();

                }
            }
        }
    }

    // Judge if a game is to be settled
    public boolean ifReachGameCondition() {
        if (numOfTileOnBoard == 225){
            System.out.println("The board if full!");
            return true;
        }
        if (currentGame.getContinuouslyShuffleCount() == currentGame.getSettings().getMaxContinuouslyShuffleNum()) {
            System.out.println("Players have already shuffles the cards continuously for " + currentGame.getSettings().getMaxContinuouslyShuffleNum() + "times.");
            return true;
        }
        for (Player player : currentGame.getPlayers()) {
            if (player.getScore() >= currentGame.getSettings().getTargetScore()) {
                System.out.println(player + " has reach the target score.");
                return true;
            }
        }
        return false;
    }

    public Control(Game game) {
        this.currentGame = game;
    }

    // To process what the user has input in his or her turn.
    public void processCommand() {
        boolean bool1 = true;
        boolean ifOptionValid;
        int option = -1;
        String position = " ";
        while (bool1) {
            ifOptionValid = false;
            while (!ifOptionValid) {
                AdvancedPrinter.printHintLn("If you want to choose option 1 ,please observe the following format for input: \"1 A(x,y), B(x,y), C(x,y)\"");
                System.out.print("Input ");
                String commands = Input.safeInputString();
                try {
                    option = Character.getNumericValue(commands.charAt(0));
                } catch (Exception e) {
                    AdvancedPrinter.printErrLn("Invalid Input. Please input again.");
                    continue;
                }
                if (0 > option || option > 2) {
                    AdvancedPrinter.printErrLn("Input is out of range. Please input again.");
                } else {
                    ifOptionValid = true;
                    if (option == 1)
                        try {
                            position = commands.substring(2);
                        } catch (Exception e) {
                            AdvancedPrinter.printErrLn("Invalid Input. Please input again.");
                            ifOptionValid = false;
                        }
                }
            }
            switch (option) {
                // option 1
                case 1 -> {
                    String[] temps = position.split(", ");
                    if (temps.length < 2) {
                        AdvancedPrinter.printErrLn("Invalid input format. Please try again.");
                        break;
                    }
                    for (String temp : temps)
                        if (!temp.matches("^[A-Z]\\((1[0-5]|[1-9]),(1[0-5]|[1-9])\\)$")) {
                            AdvancedPrinter.printErrLn("Invalid input format. Please try again.");
                            break;
                        }
                    switch (placeTiles(position)) {
                        case 1 -> {
                            bool1 = false;
                            currentGame.setContinuouslyShuffleCount(0);
                        }
                        case -1 -> AdvancedPrinter.printErrLn("The places your gave is not empty, please try again.");
                        case -2 -> AdvancedPrinter.printErrLn("The placements your gave are not in row or column, please try again.");
                        case -3 -> AdvancedPrinter.printErrLn("The word you gave is incomplete, please try again.");
                        case -4 -> AdvancedPrinter.printErrLn("The word you gave is not correct word, please try again.");
                        case -5 -> AdvancedPrinter.printErrLn("The word you gave has invalid length, please try again.");
                        case -6 -> AdvancedPrinter.printErrLn("Some letters you gave are not in your hand, please try again");
                        case -7 -> {

                        }
                    }
                }
                // option 2
                case 2 -> {
                    Arrays.fill(currentGame.getPlayers()[currentGame.getActivePlayerNum()].getCurrentTiles(), null);
                    currentGame.getActivePlayer().supplyTiles();
                    currentGame.setContinuouslyShuffleCount(currentGame.getContinuouslyShuffleCount() + 1);
                    bool1 = false;
                }
                // option 0
                case 0 -> {
                    running = false;
                    timerThread.stopTimer();
                    System.out.println("The game was stopped successfully.");
                    bool1 = false;
                    boolean bool2 = false;
                    do {
                        System.out.println("Options:");
                        System.out.println("1. Continue");
                        System.out.println("2. Save this game");
                        System.out.println("3. Exit this game");
                        int command = Input.safeInputInt(1, 3);
                        switch (command) {
                            case 1 -> {
                                running = true;
                                timerThread.continueTimer();
                                bool2 = false;
                            }
                            case 2 -> {
                                System.out.println("Set a name for this game.");
                                String fileName = Input.safeInputString();
                                try {
                                    currentGame.save(fileName);
                                    System.out.println("Save successfully!");
                                } catch (IOException e) {
                                    AdvancedPrinter.printErrLn("Save unsuccessfully.");
                                } finally {
                                    bool2 = true;
                                }
                            }
                            case 3 -> {
                                System.out.println("\nSaving data...");
                                System.out.println("Closing the game...");
                                System.out.println("Bye-bye!");
                                System.exit(0);
                            }
                        }
                    } while (bool2);
                }
            }
        }
    }

    // To start a game(start ListenerThread and TimerThread, initialize variables)
    public void startGame() {
        timerThread = new Timer();
        Listener listenerThread = new Listener();
        Random r = new Random();
        currentGame.setActivePlayerNum(r.nextInt(currentGame.getSettings().getNumOfPlayer()));
        for (Player player : currentGame.getPlayers()) {
            player.setCurrentTiles(new Tile[7]);
            player.supplyTiles();
        }
        System.out.println("\nGAME START!");
        System.out.println(currentGame.getPlayers()[currentGame.getActivePlayerNum()] + " goes first.\n");
        timerThread.start();
        listenerThread.start();
    }

    // To stop a game, get the winner and show settlement board.
    public void settleGame() {
        Player[] players = currentGame.getPlayers();
        Arrays.sort(players, (p1,p2)->p2.getScore()-p1.getScore());
        System.out.println("\n\n------------Game Stats--------");
        System.out.print("winner: " + players[0]);
        for (int i = 0; i < players.length; i++)
            if (players[0].getScore() == players[i].getScore()) System.out.print(", " + players[i].getScore());
            else break;
        System.out.println("\n\nRank");
        for (int i = 0; i < players.length; i++)
            System.out.println((i + 1) + ". " + players[i] + ": " + players[i].getScore());
        System.out.println("\nSaving data...");
        System.out.println("Closing the game...");
        System.out.println("Bye-bye!");
        System.out.println("------------Game Stats--------");
        System.exit(0);
    }

    // To print a UI when a new turn starts
    public void printTurnUI() {
        Player activePlayer = currentGame.getActivePlayer();
        currentGame.getGameBoard().displayBoard();
        System.out.println("\nScore: ");
        for (int i = 0; i < currentGame.getPlayers().length; i++)
            System.out.println(currentGame.getPlayers()[i] + ": " + currentGame.getPlayers()[i].getScore());
        System.out.println("\n----------------" + activePlayer + "'s turn.----------------");
        System.out.print("Your tiles: ");
        for (Tile tile : activePlayer.getCurrentTiles()) System.out.print(tile.getLetter()+"("+tile.getScore()+")" + " ");
        System.out.println();
        System.out.println("\nPlease choose an option in " + currentGame.getSettings().getTimeLimitForATurn() + " second(s):");
        System.out.println("1. place tiles");
        System.out.println("2. shuffle your tiles.");
        System.out.println("0. stop");
        System.out.println("----------------" + activePlayer + "'s turn.----------------");
    }

    /**
     * To judge if a command is valid and follow the command to place tiles onto board.
     *
     * @param command The command to be judged.
     * @return 1 -> valid command
     * -1 -> Invalid Placement(Given position is not empty)
     * -2 -> Invalid Placement(Not in row or column)
     * -3 -> Incomplete Word
     * -4 -> Not a Word
     * -5 -> Invalid Length
     * -6 -> Invalid Letter
     */
    public int placeTiles(String command) {
        StringBuilder word = new StringBuilder(); // word
        boolean isInRow = false; // alignment
        int minPosition; // place from
        int maxPosition; // place to
        int unitsNum; // commandUnitsNum
        char[] chars;
        int[] XPositions;
        int[] YPositions;

        // Analyse command
        String[] charsString = getStringByRegex(command, "\\w(?=\\()");
        unitsNum = charsString.length;
        chars = new char[unitsNum];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = charsString[i].charAt(0);
        }
        XPositions = getPositionByRegex(command, "(?<=\\()\\d+", unitsNum);
        YPositions = getPositionByRegex(command, "\\d+(?=\\))", unitsNum);

        // Judge if the given letters are in the player's hand
        for (char aChar : chars) {
            if (!ifContainTilesInHand(currentGame.getPlayers()[currentGame.getActivePlayerNum()], aChar)) {
                return -6; // -6 -> some given letters are not in the player's hand
            }
        }

        // Judge if positions on board are empty
        for (int i = 0; i < unitsNum; i++) {
            if (currentGame.getGameBoard().getTilesOnBoard()[YPositions[i]][XPositions[i]] != '\0') {
                return -1; // -1 -> Invalid Placement(Given position is not empty)
            }
        }

        // Get and judge placement
        if (XPositions[0] == XPositions[1]) {
            minPosition = YPositions[0];
            maxPosition = YPositions[0];
            for (int i = 0; i < unitsNum; i++) {
                if (XPositions[i] != XPositions[0]) return -2; // -2 -> Invalid Placement(Not in row or column)
                if (YPositions[i] < minPosition) minPosition = YPositions[i];
                if (YPositions[i] > maxPosition) maxPosition = YPositions[i];
            }
        } else if (YPositions[0] == YPositions[1]) {
            minPosition = XPositions[0];
            maxPosition = XPositions[0];
            for (int i = 0; i < unitsNum; i++) {
                if (YPositions[i] != YPositions[0]) return -2;
                if (XPositions[i] < minPosition) minPosition = XPositions[i];
                if (XPositions[i] > maxPosition) maxPosition = XPositions[i];
            }
            isInRow = true;
        } else {
            return -2;
        }

        // Get word and judge if the word is complete
        if (isInRow) {
            while (maxPosition < 14 && currentGame.getGameBoard().getTilesOnBoard()[YPositions[0]][maxPosition + 1] != '\0') {
                maxPosition++;
            }

            while (minPosition > 0 && currentGame.getGameBoard().getTilesOnBoard()[YPositions[0]][minPosition - 1] != '\0') {
                minPosition--;
            }
            for (int j = minPosition; j <= maxPosition; j++) {
                if (find(XPositions, j) == -1) {
                    if (currentGame.getGameBoard().getTilesOnBoard()[YPositions[0]][j] == '\0') {
                        return -3; // -3 -> Incomplete Word
                    } else {
                        word.append(currentGame.getGameBoard().getTilesOnBoard()[YPositions[0]][j]);
                    }
                } else {
                    word.append(chars[find(XPositions, j)]);
                }
            }
        } else {
            while (maxPosition < 14 && currentGame.getGameBoard().getTilesOnBoard()[maxPosition + 1][XPositions[0]] != '\0') {
                maxPosition++;
            }

            while (minPosition > 0 && currentGame.getGameBoard().getTilesOnBoard()[minPosition - 1][XPositions[0]] != '\0') {
                minPosition--;
            }
            for (int j = minPosition; j <= maxPosition; j++) {
                if (find(YPositions, j) == -1) {
                    if (currentGame.getGameBoard().getTilesOnBoard()[XPositions[0]][j] == '\0') {
                        return -3;
                    } else {
                        word.append(currentGame.getGameBoard().getTilesOnBoard()[XPositions[0]][j]);
                    }
                } else {
                    word.append(chars[find(YPositions, j)]);
                }
            }
        }

        // Judge if the length of word is valid
        if (word.length() <= 2) {
            return -5; // -5 -> Invalid word length
        }

        // Judge if the word is valid
        try {
            if (!WordCheck.checkWord(word.toString())) {
                return -4; // -4 -> Not a Word
            }
        } catch (IOException e) {
            AdvancedPrinter.printErrLn("Having no connection with WordCheck server, all words will be considered correct by default.");
        }

        // Place tiles
        for (int i = 0; i < unitsNum; i++) {
            currentGame.getGameBoard().getTilesOnBoard()[YPositions[i]][XPositions[i]] = chars[i];
        }

        // Add the number of tiles on board
        numOfTileOnBoard += unitsNum;

        // Clean the used tiles, and supply tiles.
        int[] toBeCleaned = new int[unitsNum];
        for (int i = 0; i < unitsNum; i++) {
            for (int j = 0; j < 7; j++) {
                if (currentGame.getPlayers()[currentGame.getActivePlayerNum()].getCurrentTiles()[j].getLetter() == chars[i]) {
                    toBeCleaned[i] = j;
                }
            }

        }
        for (int k = 0; k < unitsNum; k++) {
            currentGame.getPlayers()[currentGame.getActivePlayerNum()].getCurrentTiles()[toBeCleaned[k]] = null;
        }
        currentGame.getPlayers()[currentGame.getActivePlayerNum()].supplyTiles();

        // Calculate the score
        int score = 0;
        Board board = currentGame.getGameBoard();
        if (isInRow) {
            for (int j = minPosition; j <= maxPosition; j++) {
                score += Tile.getScoreByLetter(board.getTilesOnBoard()[YPositions[0]][j]) * board.getLetterMultiply()[YPositions[0]][j];
            }
            for (int j = minPosition; j <= maxPosition; j++) {
                score *= board.getWordMultiply()[YPositions[0]][j];
            }
        } else {
            for (int j = minPosition; j <= maxPosition; j++) {
                score += Tile.getScoreByLetter(board.getTilesOnBoard()[j][XPositions[0]]) * board.getLetterMultiply()[j][XPositions[0]];
            }
            for (int j = minPosition; j <= maxPosition; j++) {
                score *= board.getWordMultiply()[j][XPositions[0]];
            }
        }
        Player activePlayer = currentGame.getPlayers()[currentGame.getActivePlayerNum()];
        activePlayer.setScore(activePlayer.getScore() + score);

        return 1;
    }

    // The followings are tools that will be used in the method of placeTiles()
    public boolean ifContainTilesInHand(Player p, char aChar) {
        for (Tile currentTile : p.getCurrentTiles()) {
            if (currentTile.getLetter() == aChar) {
                return true;
            }
        }
        return false;
    }

    public int[] getPositionByRegex(String s, String regex, int num) {
        ArrayList<String> results = new ArrayList<>();
        Matcher m = Pattern.compile(regex).matcher(s);
        while (m.find()) {
            results.add(m.group());
        }
        int[] ret = new int[num];
        int i = 0;
        for (String result : results)
            ret[i++] = Integer.parseInt(result) - 1;
        return ret;
    }

    public String[] getStringByRegex(String s, String regex) {
        ArrayList<String> results = new ArrayList<>();
        Matcher m = Pattern.compile(regex).matcher(s);
        int num = 0;
        while (m.find()) {
            num++;
            results.add(m.group());
        }
        return results.toArray(new String[num]);
    }

    public int find(int[] data, int e) {
        for (int i = 0; i < data.length; i++) {
            if (e == data[i]) return i;
        }
        return -1;
    }
}


