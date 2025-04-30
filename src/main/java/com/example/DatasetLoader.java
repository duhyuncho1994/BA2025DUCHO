package com.example;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class DatasetLoader {

    public static Map<List<Character>, Boolean> load(String filename) throws IOException {
        Map<List<Character>, Boolean> dataset = new LinkedHashMap<>(); 
    
        try (BufferedReader reader = getReader(filename)) {
            reader.readLine(); // FIrst line skip (ex: 3478 2)
            String line;
    
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                boolean label = tokens[0].equals("1");
                int length = Integer.parseInt(tokens[1]);
    
                List<Character> input = new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    input.add(tokens[2 + i].charAt(0)); // '0' or '1'
                }
    
                dataset.put(input, label); 
            }
        }
    
        return dataset;
    }

    private static BufferedReader getReader(String filename) throws IOException {
        if (filename.endsWith(".gz")) {
            return new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(filename))));
        } else {
            return new BufferedReader(new FileReader(filename));
        }
    }
}