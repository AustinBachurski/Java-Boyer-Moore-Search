package BoyerMooreSearch.Demo;

import BoyerMooreSearch.BoyerMooreSearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BoyerMooreSearchDemo {
    public BoyerMooreSearchDemo(String filePath) {
        searchableText = readFile(filePath);
    }

    // Scanner to read from the console.
    private final Scanner stdin = new Scanner(System.in);

    // Input text.
    private char[] searchableText = {};

// PUBLIC METHODS

    // Begins the main loop of the demo.
    public void start() {
        clearScreen();
        System.out.println("Welcome to the interactive demo for my Boyer-Moore Search implementation.");
        printMenu();

        while (true)
        {
            switch (readNumberFromConsole("Please make a numeric selection: "))
            {
                // Display the text.
                case 1:
                    displayText();
                    break;

                // Search.
                case 2:
                    search();
                    break;

                // Exit program.
                case 3:
                    return;

                // Request a valid number and read input again.
                default:
                    System.out.println("Please make a valid selection, try again.");
                    continue;
            }
            printMenu();
        }
    }

// PRIVATE METHODS

    // Clear the console.
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void displayText() {
        clearScreen();
        System.out.println("Text is as follows:");
        System.out.println(searchableText);
        pressEnter();
    }

    private void pressEnter() {
        System.out.print("Press enter to continue...");
        String _ = stdin.nextLine();
        clearScreen();
    }

    private void printMenu() {
        System.out.println("1. Display the text");
        System.out.println("2. Search");
        System.out.println("3. Exit program");
    }

    private char[] readFile(String filePath) {
        File file = new File(filePath);

        try {
            Scanner fileReader = new Scanner(file);

            StringBuilder builder = new StringBuilder();

            while (fileReader.hasNextLine()) {
                builder.append(fileReader.nextLine());
                builder.append('\n');
            }

            fileReader.close();
            return builder.toString().toCharArray();

        } catch (Exception e) {
            System.out.println("Failed to open file: " + e);
            return new char[]{};
        }
    }

    // Read a valid number from the console and return said number.
    private int readNumberFromConsole(String prompt) {
        System.out.print(prompt);

        while (true) {
            String input = stdin.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Integers only please, enter a number: ");
            }
        }
    }

    // Read a string from the console.
    private String readStringFromConsole(String prompt) {
        System.out.print(prompt);
        return stdin.nextLine();
    }

    private void search() {
        clearScreen();
        String pattern = readStringFromConsole("Enter the text you wish to find (case sensitive): ");
        ArrayList<Integer> results = BoyerMooreSearch.findAllMatches(searchableText, pattern);

        if (results.isEmpty()) {
            System.out.println("The pattern \""
                + pattern
                + "\" was not found in the text.\n");
        } else if (results.size() == 1) {
            System.out.println("The pattern \""
                + pattern
                + "\" was found at index "
                + results.getFirst()
                + ".\n");
        } else {
            StringBuilder builder = new StringBuilder("The pattern \"");
            builder.append(pattern);
            builder.append("\" was found at indices ");

            for (int i = 0; i < results.size() - 1; ++i) {
                builder.append(results.get(i));
                builder.append(", ");
            }

            builder.append("and ");
            builder.append(results.getLast());
            builder.append(".\n");

            System.out.println(builder);
        }
        pressEnter();
    }
}

