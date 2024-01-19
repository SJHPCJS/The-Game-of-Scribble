package game;

import java.io.Serializable;

/**
 * Setting:
 * A class to save settings about a game
 */
public class Setting implements Serializable {
    private int timeLimitForGame;
    private int timeLimitForATurn;
    private boolean ifContainsSpecialPlace;
    private int numOfPlayer;
    private int targetScore;

    private int maxContinuouslyShuffleNum;

    public Setting(int timeLimitForGame, int timeLimitForATurn, boolean ifContainsSpecialPlace, int numOfPlayer, int targetScore) {
        this.timeLimitForGame = timeLimitForGame;
        this.timeLimitForATurn = timeLimitForATurn;
        this.ifContainsSpecialPlace = ifContainsSpecialPlace;
        this.numOfPlayer = numOfPlayer;
        this.targetScore = targetScore;
        this.maxContinuouslyShuffleNum = this.numOfPlayer * 2;
    }

    // Getter and setter
    public int getTimeLimitForGame() {
        return timeLimitForGame;
    }

    public void setTimeLimitForGame(int timeLimitForGame) {
        this.timeLimitForGame = timeLimitForGame;
    }

    public int getTimeLimitForATurn() {
        return timeLimitForATurn;
    }

    public void setTimeLimitForATurn(int timeLimitForATurn) {
        this.timeLimitForATurn = timeLimitForATurn;
    }

    public boolean getIfContainsSpecialPlace() {
        return ifContainsSpecialPlace;
    }

    public void setIfContainsSpecialPlace(boolean ifContainsSpecialPlace) {
        this.ifContainsSpecialPlace = ifContainsSpecialPlace;
    }

    public int getNumOfPlayer() {
        return numOfPlayer;
    }

    public void setNumOfPlayer(int numOfPlayer) {
        this.numOfPlayer = numOfPlayer;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public int getMaxContinuouslyShuffleNum() {return this.maxContinuouslyShuffleNum; }
}
