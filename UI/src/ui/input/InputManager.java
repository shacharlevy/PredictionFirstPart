package ui.input;

import java.util.Scanner;

public class InputManager {
    private final Scanner inputScanner = new Scanner(System.in);
    public int getInt() {
        String input = inputScanner.nextLine();
        while (!input.matches("\\d+")) {
            System.out.println("Invalid choice, please try again: ");
            input = inputScanner.nextLine();
        }
        return Integer.parseInt(input);
    }

    public int getIntInRange(int min, int max) {
        String input = inputScanner.nextLine();
        while (!input.matches("\\d+") || Integer.parseInt(input) < min || Integer.parseInt(input) > max) {
            System.out.println("Invalid choice, please try again: ");
            input = inputScanner.nextLine();
        }
        return Integer.parseInt(input);
    }
}
