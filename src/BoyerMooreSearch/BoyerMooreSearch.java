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
        int[] goodSuffixes = preParseGoodSuffixes(pattern);

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
                // Mismatch, shift textOffset by the greater of the bad character shift or the good suffix shift.
                int badCharacterOffset = patternIndex - badCharacters.getOrDefault(pattern.charAt(patternIndex), -1);
                textOffset += Math.max(badCharacterOffset, goodSuffixes[patternIndex + 1]);
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

    private static int[] preParseGoodSuffixes(String pattern) {
        // Array to store the number of places to shift for a good suffix.
        int[] shift = new int[pattern.length() + 1];

        // Array to store the index of the border for a suffix at borderPosition[i].
        int[] borderPosition = new int[pattern.length() + 1];

        // Head and tail of the suffix as the pattern is walked.
        int head = pattern.length();
        int tail = pattern.length() + 1;

        // The border for a possible suffix at the end of the pattern is outside the bounds of the pattern.
        borderPosition[head] = tail;

        // Work from the end of the pattern to the beginning.
        while (head > 0) {
            // This loop isn't entered on the first pass.  Once it is, compare each pair of characters.
            while (tail <= pattern.length() && pattern.charAt(head -1) != pattern.charAt(tail - 1)) {
                // If characters do not match the end of this border has been reached.
                if (shift[tail] == 0) {
                    // If shift[tail] is zero, this is the first time this index was seen, so update the shift amount.
                    // Suffix matched up until this point, set the valid distance to shift for this index.
                    shift[tail] = tail - head;
                }
                // Move to the next smallest border.
                tail = borderPosition[tail];
            }
            // If characters match:
            // - Decrement head and tail to continue walking the pattern.
            // - Update the starting position of the right border.
            --head;
            --tail;
            borderPosition[head] = tail;
        }

        // Set tail to the widest border of the entire pattern.
        tail = borderPosition[0];

        // Check for unpopulated shift indices.
        for (head = 0; head <= pattern.length(); ++head) {
            // If index was not set, use the valid shift distance from borderPosition.
            if (shift[head] == 0) {
                shift[head] = tail;
            }

            // When the edge of the current border is reached, move tail to the next smallest border.
            if (head == tail) {
                tail = borderPosition[tail];
            }
        }

        // Return the populated shift array.
        return shift;
    }
}

