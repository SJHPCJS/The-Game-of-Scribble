package game;

import java.io.Serializable;

/**
 * Player:
 * A class to package all the information of a player and methods to implement what players can do
 */
public class Player implements Serializable {
    private final String id;
    private int score;
    private Tile[] currentTiles;

    public Player(String id) {
        this.id = id;
    }

    // Getter and setter
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Tile[] getCurrentTiles() {
        return currentTiles;
    }

    public void setCurrentTiles(Tile[] currentTiles) {
        this.currentTiles = currentTiles;
    }

    // Supply tiles for a player to keep 7 tiles in his or her hand
    public void supplyTiles() {
        Tile[] newTiles = new Tile[7];
        for (int i = 0; i < newTiles.length; i++) {
            if (getCurrentTiles()[i] == null) {
                newTiles[i] = Tile.randomTile();
            } else {
                newTiles[i] = this.getCurrentTiles()[i];
            }
        }
        this.setCurrentTiles(newTiles);
    }

    @Override
    public String toString() {
        return this.id;
    }
}
