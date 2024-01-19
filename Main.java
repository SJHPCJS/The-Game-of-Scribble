import game.Control;
import game.Game;
import ui.Menu;

/**
 * Main:
 * The entrance to the whole program
 */
public class Main {
    public static void main(String[] args) {
        Game game = Menu.mainMenu();
        Control control = new Control(game);
        control.startGame();
    }
}
