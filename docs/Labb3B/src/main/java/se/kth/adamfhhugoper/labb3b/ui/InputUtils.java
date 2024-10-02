
package se.kth.adamfhhugoper.labb3b.ui;

import java.util.Scanner;


/**
 * Input utilities for the user interface.
 */

public class InputUtils {

    /**
     * Return the first character, converted to upper case,
     * or, if length is zero, return ' '.
     *
     * @param str the string to extract the first character from
     * @return the first character, in upper case, or ' '
     * @throws IllegalArgumentException if str is null
     */

    public static char getFirstChar(String str) {
        if (str == null) throw new IllegalArgumentException("str is null");
        return str.length() == 0 ? ' ' : Character.toUpperCase(str.charAt(0));
    }

    /**
     * Read a line from the provide Scanner and return the first character,
     * converted to upper case, or, if the string length is zero, return ' '.
     *
     * @param scanner the Scanner to read from
     * @return the first character, in upper case, or ' '
     * @throws IllegalArgumentException if scanner is null
     */

    public static char scanAndReturnFirstChar(Scanner scanner) {
        if (scanner == null) throw new IllegalArgumentException("scanner is null");
        String str = scanner.nextLine();
        return getFirstChar(str);
    }
}

