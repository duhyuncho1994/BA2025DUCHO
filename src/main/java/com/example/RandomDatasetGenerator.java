package com.example;

import net.automatalib.automaton.fsa.DFA;
import net.automatalib.word.*;
import net.automatalib.alphabet.Alphabet;

import java.util.*;

public class RandomDatasetGenerator {


    /**
     * For dataset when random dataset generated 
     */

    public static Map<List<Character>, Boolean> generate(DFA<?, Character> targetDFA,
                                                         Alphabet<Character> alphabet,
                                                         int numSamples,
                                                         int minLength,
                                                         int maxLength) {

        Map<List<Character>, Boolean> dataset = new LinkedHashMap<>();
        Random random = new Random();

        for (int i = 0; i < numSamples; i++) {
            int length = random.nextInt((maxLength - minLength) + 1) + minLength;
            List<Character> input = new ArrayList<>();
            for (int j = 0; j < length; j++) {
                input.add(alphabet.getSymbol(random.nextInt(alphabet.size())));
            }

            Word<Character> word = Word.fromList(input);
            boolean accepted = targetDFA.accepts(word);

            dataset.put(input, accepted);
        }

        return dataset;
    }

    /**
     * For testset when random dataset generated 
     */
    public static List<List<Character>> generateInputs(Alphabet<Character> alphabet,
                                                       int numSamples,
                                                       int minLength,
                                                       int maxLength) {

        List<List<Character>> inputList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numSamples; i++) {
            int length = random.nextInt((maxLength - minLength) + 1) + minLength;
            List<Character> input = new ArrayList<>();
            for (int j = 0; j < length; j++) {
                input.add(alphabet.getSymbol(random.nextInt(alphabet.size())));
            }
            inputList.add(input);
        }

        return inputList;
    }
}