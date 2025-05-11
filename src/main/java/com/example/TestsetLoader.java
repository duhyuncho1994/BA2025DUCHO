package com.example;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class TestsetLoader {

    /**
     * Loads testset where labels are always -1 (unknown).
     */
    public static List<List<Character>> load(String filename) throws IOException {
        List<List<Character>> testInputs = new ArrayList<>();

        try (BufferedReader reader = getReader(filename)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");

                // Skip invalid or empty lines
                if (tokens.length < 2) {
                    continue;
                }

                int length;
                try {
                    length = Integer.parseInt(tokens[1]);
                } catch (NumberFormatException e) {
                    // Skip malformed lines
                    continue;
                }

                // Check if we have enough tokens for the declared length
                if (tokens.length < 2 + length) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }

                List<Character> input = new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    input.add(tokens[2 + i].charAt(0));
                }

                testInputs.add(input);
            }
        }

        return testInputs;
    }

    private static BufferedReader getReader(String filename) throws IOException {
        if (filename.endsWith(".gz")) {
            return new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(filename))));
        } else {
            return new BufferedReader(new FileReader(filename));
        }
    }
}