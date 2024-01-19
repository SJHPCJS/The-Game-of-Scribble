package ui;

import java.util.Scanner;

/**
 * Input:
 * A class to receive player's input and check if it is valid
 */
public class Input {
    /**
     * Receive and check the user input, if invalid it will pop an error and require for a re-input.
     *
     * @return the valid integer user input.
     */
    public static int safeInputInt() {
        while (true) {
            System.out.print(">> ");
            Scanner scan = new Scanner(System.in);
            try {
                return Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                if (e instanceof NumberFormatException)
                    AdvancedPrinter.printErrLn("Invalid data type. Please input a integer.");
                else AdvancedPrinter.printErrLn("Unacknowledged exception occurred. Please input again.");
            }
        }
    }

    /**
     * Receive and check the user input, if less than the infimum or greater than the supremum or invalid it will pop an error and require for a re-input.
     *
     * @param inf the infimum of the scope of the integer you want user to input.
     * @param sup the supremum of the scope of the integer you want user to input.
     * @return the valid integer user input.
     */
    public static int safeInputInt(int inf, int sup) {
        while (true) {
            System.out.print(">> ");
            Scanner scan = new Scanner(System.in);
            try {
                int num = Integer.parseInt(scan.nextLine());
                if (inf <= num && num <= sup) return num;
                else AdvancedPrinter.printErrLn("Input is out of range. Please input a integer.");
            } catch (Exception e) {
                if (e instanceof NumberFormatException)
                    AdvancedPrinter.printErrLn("Invalid data type. Please input a integer.");
                else AdvancedPrinter.printErrLn("Unacknowledged exception occurred. Please input again.");
            }
        }
    }
    /**
     * Receive and check the user input, if invalid it will pop an error and require for a re-input.
     *
     * @param validValue the valid integers array which includes all the integer you want user to input.
     * @return the valid integer user input.
     */
    public static int safeInputInt(int[] validValue) {
        while (true) {
            System.out.print(">> ");
            Scanner scan = new Scanner(System.in);
            try {
                int num = Integer.parseInt(scan.nextLine());
                for (int i : validValue)
                    if (i == num)
                        return num;
                AdvancedPrinter.printErrLn("Invalid value. Please input a integer.");
            } catch (Exception e) {
                if (e instanceof NumberFormatException)
                    AdvancedPrinter.printErrLn("Invalid data type. Please input a integer.");
                else AdvancedPrinter.printErrLn("Unacknowledged exception occurred. Please input again.");
            }
        }
    }

    /**
     * Receive and check the string user input, if invalid it will pop an error and require for a re-input.
     *
     * @return the valid string user input.
     */
    public static String safeInputString() {
        while (true) {
            System.out.print(">> ");
            Scanner scan = new Scanner(System.in);
            try {
                String str = scan.nextLine();
                if (!str.equals("")) return str;
                else AdvancedPrinter.printErrLn("Empty. Please input again.");
            } catch (Exception e) {
                AdvancedPrinter.printErrLn("Unacknowledged exception occurred. Please input again.");
            }
        }
    }


    /**
     * Receive and check the string user input, if invalid it will pop an error and require for a re-input.
     *
     * @param validValue the valid strings array which includes all the string you want user to input.
     * @return the valid string user input.
     */
    public static String safeInputString(String[] validValue) {
        while (true) {
            System.out.print(">> ");
            Scanner scan = new Scanner(System.in);
            try {
                String str = scan.nextLine();
                for (String s : validValue)
                    if (s.equals(str))
                        return str;
                AdvancedPrinter.printErrLn("Invalid value. Please input again.");
            } catch (Exception e) {
                AdvancedPrinter.printErrLn("Unacknowledged exception occurred. Please input again.");
            }
        }
    }

}
