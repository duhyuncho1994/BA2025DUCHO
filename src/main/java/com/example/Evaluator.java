package com.example;

import net.automatalib.automaton.fsa.DFA;
import net.automatalib.word.Word;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Evaluator {

    /**
     * Evaluates the DFA on a test set and logs the predicted results
     */

    public static void evaluateAndLog(DFA<?, Character> dfa,
                                      List<List<Character>> testSet,
                                      String algorithmName,
                                      int roundCount,
                                      String mqCount,
                                      long runtimeMillis,
                                      String outputPath) throws IOException {




        int total = testSet.size();
        int accepted = 0;

        // Save predictions separately
        try (FileWriter predictionWriter = new FileWriter("test_predictions.txt")) {
            for (List<Character> input : testSet) {
                Word<Character> word = Word.fromList(input);
                boolean prediction = dfa.accepts(word);

                String inputStr = input.stream().map(String::valueOf).reduce((a, b) -> a + " " + b).orElse("");
                predictionWriter.write(inputStr + " -> " + (prediction ? "1" : "0") + "\n");

                if (prediction) accepted++;
            }
        }


        int rejected = total - accepted;
        double acceptanceRate = total == 0 ? 0.0 : ((double) accepted / total);

        
        try (FileWriter fw = new FileWriter(outputPath, true)) {

            fw.write(String.format("%s,%d,%d,%s,%d,%d,%.4f,%d\n", 
                    algorithmName, dfa.size(), roundCount, mqCount, accepted, rejected, acceptanceRate, runtimeMillis));


            

           

        }

        System.out.printf("Evaluated %d samples. Accepted: %d, Rejected: %d, Acceptance Rate: %.2f%%%n",
                total, accepted, rejected, 100.0 * acceptanceRate);
    }
}
