package game;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Game:
 * A class to package all the information about a game
 */
public class Game implements Serializable{
    private final Setting settings;
    private final Player[] players;
    private final Board gameBoard;
    private int activePlayerNum;
    private int turnTimeCount;
    private int gameTimeCount;
    private int continuouslyShuffleCount;
    public Game(Setting settings, Player[] players, Board gameBoard) {
        this.settings = settings;
        this.players = players;
        this.gameBoard = gameBoard;
        this.activePlayerNum = 0;
        this.turnTimeCount = 0;
        this.gameTimeCount = 0;
        this.continuouslyShuffleCount = 0;
    }

    // Getter and setter
    public int getTurnTimeCount() { return turnTimeCount; }

    public void setTurnTimeCount(int turnTimeCount) { this.turnTimeCount = turnTimeCount; }

    public int getGameTimeCount() { return gameTimeCount; }

    public void setGameTimeCount(int gameTimeCount) { this.gameTimeCount = gameTimeCount; }

    public int getContinuouslyShuffleCount() {return this.continuouslyShuffleCount; }

    public void setContinuouslyShuffleCount(int i) {this.continuouslyShuffleCount = i; }

    public int getActivePlayerNum() {
        return activePlayerNum;
    }

    public void setActivePlayerNum(int activePlayerNum) { this.activePlayerNum = activePlayerNum; }

    public Setting getSettings() {
        return settings;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    // Change the active player
    public void nextActivePlayer() {
        int nextNum = (activePlayerNum + 1) % settings.getNumOfPlayer();
        setActivePlayerNum(nextNum);
    }

    // Reset the time count for a turn
    public void resetTurnTimeCount() {
        setTurnTimeCount(0);
    }

    // Get the current active player
    public Player getActivePlayer() {
        return players[activePlayerNum];
    }

    // Write all the information about this game into a file
    public void save(String fileName) throws IOException {
        File directory = new File("savings");
        if(!directory.exists()) directory.mkdir();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String currentTime = simpleDateFormat.format(new Date());
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savings/" + fileName + "_" + currentTime + ".scribble"));
        oos.writeObject(this);
        oos.close();
    }

    // Load a game file and return it as a game object.
    public static Game loadGame(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savings/"+file.getName()));
        return (Game) ois.readObject();
    }

    // Load games from game file directory and return a file array.
    public static File[] loadGamesFromDirectory() {
        File directory = new File("savings");
        if(!directory.exists()) directory.mkdir();

        return directory.listFiles();
    }
}
