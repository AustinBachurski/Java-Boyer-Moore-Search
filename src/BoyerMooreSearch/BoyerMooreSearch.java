package BoyerMooreSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoyerMooreSearch {
    // No state to store, no point in having an object.
    private BoyerMooreSearch() {}

    // Finds the index of all matches of the given pattern in the given text using the Boyer-More algorithm.
    public static ArrayList<Integer> findAllMatches(char[] text, String pattern) {
        // Preprocessing of the input pattern.
        Map<Character, Integer> badCharacters = preParseBadCharacters(pattern);

        // Store results in an array since the assignment is for ALL matches.
        ArrayList<Integer> results = new ArrayList<>();

        // Offset into the searchable text that effectively places the pattern comparison in the right location.
        int textOffset = 0;

        // Begin looping until the pattern falls off the end fo the searchable text.
        while (textOffset <= text.length - pattern.length()) {
            // Start the search at the last element in the pattern.
            int patternIndex = pattern.length() - 1;

            // Iterate over characters from back to front, if they match, decrement the search index and loop again.
            while (patternIndex >= 0 && pattern.charAt(patternIndex) == text[textOffset + patternIndex]) {
                --patternIndex;
            }

            if(patternIndex == -1) {
                // Pattern matched, add the index to the results list and increment textOffset to continue searching.
                results.add(textOffset);
                ++textOffset;
            } else {
                // Mismatch, shift textOffset by the bad character amount.
                textOffset += patternIndex - badCharacters.getOrDefault(pattern.charAt(patternIndex), -1);
            }
        }

        // The loop exits when the end of the searchable text is reached, return the results.
        return results;
    }

    // Generates a table to shift the pattern by when a mismatched character is encountered.
    private static Map<Character, Integer> preParseBadCharacters(String pattern) {
        Map<Character, Integer> badCharacters = new HashMap<>();

        // Shift the pattern by the last position of each character in the pattern.
        for (int i = 0; i < pattern.length(); ++i) {
            badCharacters.put(pattern.charAt(i), i);
        }

        return badCharacters;
    }
}

