package com.example;

import de.learnlib.oracle.MembershipOracle;
import de.learnlib.query.DefaultQuery;
import de.learnlib.oracle.EquivalenceOracle;
import de.learnlib.algorithm.lstar.dfa.ClassicLStarDFA;
import de.learnlib.algorithm.ttt.dfa.TTTLearnerDFA;
import net.automatalib.automaton.fsa.DFA;
import net.automatalib.alphabet.Alphabet;
import net.automatalib.word.*;
//import net.automatalib.word.impl.SimpleAlphabet;
import java.io.*;
import java.util.*;

public class DFATeacher {
    // MQ , EQ for Teacher
   
    //generated randomly
    public static Set<String> generateRandomData(int size, int maxLength, Random rand) {
        Set<String> data = new HashSet<>();
        char[] chars = {'a', 'b', 'c'};
        while (data.size() < size) {
            int length = rand.nextInt(maxLength) + 1;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append(chars[rand.nextInt(chars.length)]);
            }
            data.add(sb.toString());
        }
        return data;
    }

  //road abbadingo dataset
  
    public static Set<String> loadDataFromFile(String filePath) throws IOException {
        Set<String> data = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.trim());
            }
        }
        return data;
    }

    public static void main(String[] args) throws IOException {
        Random rand = new Random();
        
        // First Dataset : randomly generated dataset
        Set<String> positiveRandom = generateRandomData(50, 5, rand);
        Set<String> negativeRandom = generateRandomData(50, 5, rand);
        Alphabet<Character> alphabet = new SimpleAlphabet<>(Arrays.asList('a', 'b'));
        DFATeacher teacherRandom = new DFATeacher(positiveRandom, negativeRandom, alphabet);
        
        // Second Datset: manuell downloaded datasets from abbadingo
        String positiveFilePath = "data1.txt";
        String negativeFilePath = "data2.txt";
      
        Set<String> positiveFile = loadDataFromFile(positiveFilePath);
        Set<String> negativeFile = loadDataFromFile(negativeFilePath);
        DFATeacher teacherFile = new DFATeacher(positiveFile, negativeFile, alphabet);
        
        // L star algorithm (For randomly generated dataset)
        ClassicLStarDFA<Character> lstarRandom = new ClassicLStarDFA<>(alphabet, teacherRandom);
        lstarRandom.startLearning();
        System.out.println("L* DFA learned (Random Data): " + lstarRandom.getHypothesisModel());
        
        // TTT algoirthm (FOr ramdomly generated dataset)
        TTTLearnerDFA<Character> tttRandom = new TTTLearnerDFA<>(alphabet, teacherRandom);
        tttRandom.startLearning();
        System.out.println("TTT DFA learned (Random Data): " + tttRandom.getHypothesisModel());
        
        // L star algorithm (Abbadingo)
        ClassicLStarDFA<Character> lstarFile = new ClassicLStarDFA<>(alphabet, teacherFile);
        lstarFile.startLearning();
        System.out.println("L* DFA learned (File Data): " + lstarFile.getHypothesisModel());
        
        // TTT algorithm  (Abbadingo)
        TTTLearnerDFA<Character> tttFile = new TTTLearnerDFA<>(alphabet, teacherFile);
        tttFile.startLearning();
        System.out.println("TTT DFA learned (File Data): " + tttFile.getHypothesisModel());
    }
}
