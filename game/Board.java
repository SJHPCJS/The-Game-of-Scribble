package game;

import ui.AdvancedPrinter;
import ui.ConsoleColor;

import java.io.Serializable;

/**
 * Board:
 * A class to store all the information about a board
 */
public class Board implements Serializable {
    private char[][] tilesOnBoard = new char[15][15];
    private final int[][] letterMultiply;
    private final int[][] wordMultiply;

    public Board(boolean ifContainSpecialPlace) {
        if (ifContainSpecialPlace) {
            this.letterMultiply = new int[][]{
                    {1,1,1,2,1,1,1,1,1,1,1,2,1,1,1},
                    {1,1,1,1,1,3,1,1,1,3,1,1,1,1,1},
                    {1,1,1,1,1,1,2,1,2,1,1,1,1,1,1},
                    {2,1,1,1,1,1,1,3,1,1,1,1,1,1,2},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,3,1,1,1,1,2,1,2,1,1,1,1,3,1},
                    {1,1,2,1,1,2,1,1,1,2,1,1,2,1,1},
                    {1,1,1,3,1,1,1,1,1,1,1,3,1,1,1},
                    {1,1,2,1,1,2,1,1,1,2,1,1,2,1,1},
                    {1,3,1,1,1,1,2,1,2,1,1,1,1,3,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {2,1,1,1,1,1,1,3,1,1,1,1,1,1,2},
                    {1,1,1,1,1,1,2,1,2,1,1,1,1,1,1},
                    {1,1,1,1,1,3,1,1,1,3,1,1,1,1,1},
                    {1,1,1,2,1,1,1,1,1,1,1,2,1,1,1}
            };
            this.wordMultiply = new int[][]{
                    {3,1,1,1,1,1,1,3,1,1,1,1,1,1,3},
                    {1,2,1,1,1,1,1,1,1,1,1,1,1,2,1},
                    {1,1,2,1,1,1,1,1,1,1,1,1,2,1,1},
                    {1,1,1,2,1,1,1,1,1,1,1,2,1,1,1},
                    {1,1,1,1,2,1,1,1,1,1,2,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {3,1,1,1,1,1,1,1,1,1,1,1,1,1,3},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,2,1,1,1,1,1,2,1,1,1,1},
                    {1,1,1,2,1,1,1,1,1,1,1,2,1,1,1},
                    {1,1,2,1,1,1,1,1,1,1,1,1,2,1,1},
                    {1,2,1,1,1,1,1,1,1,1,1,1,1,2,1},
                    {3,1,1,1,1,1,1,3,1,1,1,1,1,1,3}

            };
        } else {
            this.letterMultiply = new int[15][15];
            this.wordMultiply = new int[15][15];
            for (int i=0; i<15; i++) {
                for (int j=0; j<15; j++) {
                    this.letterMultiply[i][j] = 1;
                    this.wordMultiply[i][j] = 1;
                }
            }
        }
    }

    public char[][] getTilesOnBoard() {
        return tilesOnBoard;
    }
    public int[][] getLetterMultiply() {
        return letterMultiply;
    }
    public int[][] getWordMultiply() {
        return wordMultiply;
    }

    // Display the game board according the tiles on board
    public void displayBoard() {
        System.out.print("   ");
        for (int col = 0; col < 15; col++) {
            System.out.printf("%3d ", col + 1);
        }
        System.out.println();
        System.out.print("   +");
        for (int h = 0; h < 15; h++) {
            System.out.print("---+");
        }
        System.out.println();
        for (int i = 0; i < 15; i++) {
            System.out.printf("%2d |", i + 1);
            for (int j = 0; j < 15; j++) {
                char tile = tilesOnBoard[i][j];
                if (tile == '\0') {
                    if(letterMultiply[i][j]==2) {
                        AdvancedPrinter.backgroundDL("   ");
                    } else if (letterMultiply[i][j]==3) {
                        AdvancedPrinter.backgroundTL("   ");
                    } else if (wordMultiply[i][j]==2) {
                        AdvancedPrinter.backgroundDW("   ");
                    } else if (wordMultiply[i][j]==3) {
                        AdvancedPrinter.backgroundTW("   ");
                    }else System.out.print("   ");


                } else {
                    if(letterMultiply[i][j]==2) {
                        AdvancedPrinter.backgroundDL(" " + tile + " ");
                    } else if (letterMultiply[i][j]==3) {
                        AdvancedPrinter.backgroundTL(" " + tile + " ");
                    } else if (wordMultiply[i][j]==2) {
                        AdvancedPrinter.backgroundDW(" " + tile + " ");
                    } else if (wordMultiply[i][j]==3) {
                        AdvancedPrinter.backgroundTW(" " + tile + " ");
                    }else System.out.printf(" %s ", tile);


                }
                System.out.print("|");
            }
            System.out.println();
            System.out.print("   +");
            for (int h = 0; h < 15; h++) {
                System.out.print("---+");
            }
            System.out.println();
        }
        if (wordMultiply[0][0]==3) {
            System.out.println(ConsoleColor.ANSI_B_YELLOW+ "   " +ConsoleColor.ANSI_RESET+" = Double Letter                          "
            + ConsoleColor.ANSI_B_GREEN + "   " +ConsoleColor.ANSI_RESET+" = Triple Letter");
            System.out.println(ConsoleColor.ANSI_B_BLUE + "   "+ConsoleColor.ANSI_RESET+ " = Double Word                            "
                    + ConsoleColor.ANSI_B_RED + "   "+ ConsoleColor.ANSI_RESET+ " = Triple Word");
        }
    }

}
