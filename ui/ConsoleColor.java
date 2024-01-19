package ui;

/**
 * ConsoleColor:
 * An enum class to define some color to be used in the menu
 *
 */
public enum ConsoleColor {
    ANSI_RESET("\u001B[0m"),ANSI_RED("\u001B[31m"),ANSI_GREEN("\u001B[32m"),ANSI_B_YELLOW("\u001B[43m"),ANSI_B_GREEN("\u001B[42m"),ANSI_B_BLUE("\u001B[44m"),ANSI_B_RED("\u001B[41m");

    private final String ansi;
    ConsoleColor(String ansi) {
        this.ansi = ansi;
    }

    /**
     * @return The value of enum color object
     */
    @Override
    public String toString() {
        return this.ansi;
    }
}
