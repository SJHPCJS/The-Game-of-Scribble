package ui;

/**
 * AdvancedPrinter:
 * A tool class to print something with color
 */

public class AdvancedPrinter {
    /**
     * Print a line in red.
     * @param s The string to be printed
     */
    public static void printErrLn(String s) {
        System.out.println(ConsoleColor.ANSI_RED + s + ConsoleColor.ANSI_RESET);
    }

    /**
     * Print a line in green
     * @param s The string to be printed
     */
    public static void printHintLn(String s) {
        System.out.println(ConsoleColor.ANSI_GREEN + s + ConsoleColor.ANSI_RESET);
    }

    // The followings are all used to print a string with a certain color background.
    public static void backgroundDL(String s) {
        System.out.print(ConsoleColor.ANSI_B_YELLOW + s + ConsoleColor.ANSI_RESET);
    }

    public static void backgroundTL(String s) {
        System.out.print(ConsoleColor.ANSI_B_GREEN + s + ConsoleColor.ANSI_RESET);
    }

    public static void backgroundDW(String s) {
        System.out.print(ConsoleColor.ANSI_B_BLUE + s + ConsoleColor.ANSI_RESET);
    }

    public static void backgroundTW(String s) {
        System.out.print(ConsoleColor.ANSI_B_RED + s + ConsoleColor.ANSI_RESET);
    }
}
