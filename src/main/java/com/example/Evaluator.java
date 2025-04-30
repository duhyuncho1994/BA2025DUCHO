package com.example;

import net.automatalib.automaton.fsa.DFA;
import net.automatalib.word.*;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Evaluator {

    /**
     * calculate accuracy
     */
    public static double evaluateAccuracy(DFA<?, Character> dfa,
                                          Map<List<Character>, Boolean> testSet,
                                          String algorithmName,
                                          int roundCount,
                                          
                                
                                          
                                          String outputPath) throws IOException {

        int correct = 0;
        int total = 0;

        for (Map.Entry<List<Character>, Boolean> entry : testSet.entrySet()) {
            Word<Character> word = Word.fromList(entry.getKey());
            boolean predicted = dfa.accepts(word);
            boolean actual = entry.getValue();

            if (predicted == actual) {
                correct++;
            }
            total++;
        }

        double accuracy = total == 0 ? 0.0 : (100.0 * correct / total);

        // save result
        try (FileWriter fw = new FileWriter(outputPath, true)) {
            fw.write(String.format("%s,%d,%d,%.2f%%\n", algorithmName, dfa.size(), roundCount ,accuracy));
        }

        return accuracy;
    }
}