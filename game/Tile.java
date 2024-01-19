package game;

import java.io.Serializable;
import java.util.Random;

/**
 * Tile:
 * An enum class to store all the data of tiles.
 */
public enum Tile implements Serializable {
    A('A',1),E('E',1),I('I',1),O('O',1),U('U',1),L('L',1),N('N',1),S('S',1),T('T',1),R('R',1),
    D('D',2),G('G',2),
    B('B',3),C('C',3),M('M',3),P('P',3),
    F('F',4),H('H',4),V('V',4),W('W',4),Y('F',4),
    K('K',5),
    J('J',8),X('X',8),
    Q('Q',10),Z('Z',10);

    private final char letter;
    private final int score;

    Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    // Getter and setter
    public char getLetter() {
        return letter;
    }

    public int getScore() {
        return score;
    }

    // Get the score of the given letter
    public static int getScoreByLetter(char letter) {
        for(int i=0;i< Tile.values().length;i++) {
            if(Tile.values()[i].getLetter()==letter) {
                return Tile.values()[i].getScore();
            }
        }
        return -1;
    }

    // Return a random Tile
     public static Tile randomTile() {
         Random r = new Random();
         int n = r.nextInt(1, 100);
         int pb1 = (int) (Tile.possibility(Tile.values()[0]) * 100);
         int pb2 = (int) (Tile.possibility(Tile.values()[10]) * 100);
         int pb3 = (int) (Tile.possibility(Tile.values()[12]) * 100);
         int pb4 = (int) (Tile.possibility(Tile.values()[16]) * 100);
         int pb5 = (int) (Tile.possibility(Tile.values()[21]) * 100);
         int pb6 = (int) (Tile.possibility(Tile.values()[22]) * 100);
         int pb7 = (int) (Tile.possibility(Tile.values()[24]) * 100);
         Tile tile;
         if (n <= pb1){
             tile = Tile.values()[r.nextInt(24,26)];
         } else if (n <= pb1 + pb2) {
             tile = Tile.values()[r.nextInt(22,24)];
         } else if (n <= pb1 + pb2 + pb3) {
             tile = Tile.values()[21];
         } else if (n <= pb1 + pb2 + pb3 + pb4) {
             tile = Tile.values()[r.nextInt(16,21)];
         } else if (n <= pb1 + pb2 + pb3 + pb4 + pb5) {
             tile = Tile.values()[r.nextInt(12,16)];
         } else if (n <= pb1 + pb2 + pb3 + pb4 + pb5 + pb6) {
             tile = Tile.values()[r.nextInt(10,12)];
         } else {
             tile = Tile.values()[r.nextInt(0,10)];
         }
         return tile;
     }

    private static float possibility(Tile tile){
        int p = 5;
        float tan = 0.5F;
        return tile.getScore() / 33F;
     }

}
