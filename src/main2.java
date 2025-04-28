package com.example;

import java.awt.Desktop;
import java.io.FileWriter;
import java.io.IOException;
import de.learnlib.algorithm.lstar.dfa.ClassicLStarDFA;

import de.learnlib.algorithm.lstar.dfa.ClassicLStarDFABuilder;
import de.learnlib.algorithm.ttt.dfa.TTTLearnerDFA;
import de.learnlib.datastructure.observationtable.OTUtils;
import de.learnlib.datastructure.observationtable.writer.ObservationTableASCIIWriter;
import de.learnlib.filter.statistic.oracle.DFACounterOracle;
import de.learnlib.oracle.MembershipOracle;
import de.learnlib.oracle.EquivalenceOracle.DFAEquivalenceOracle;
import de.learnlib.query.*;
import de.learnlib.oracle.MembershipOracle.DFAMembershipOracle;
import de.learnlib.oracle.equivalence.*;
import de.learnlib.oracle.equivalence.DFAWMethodEQOracle;
import de.learnlib.oracle.membership.DFASimulatorOracle;
import de.learnlib.oracle.membership.SimulatorOracle;
import de.learnlib.util.Experiment.DFAExperiment;

import net.automatalib.alphabet.Alphabet;
import net.automatalib.alphabet.impl.Alphabets;
import net.automatalib.automaton.fsa.DFA;
import net.automatalib.automaton.fsa.impl.CompactDFA;
import net.automatalib.serialization.dot.GraphDOT;
import net.automatalib.util.automaton.builder.AutomatonBuilders;
import net.automatalib.visualization.Visualization;
import java.util.*;

public class main2 {

    private static CompactDFA<Character> constructSUL() {
        Alphabet<Character> sigma = Alphabets.characters('0', '1');

        return AutomatonBuilders.newDFA(sigma)
                .withInitial("q0")
                .from("q0").on('0').to("q0").on('1').to("q1")
                .from("q1").on('0').to("q2").on('1').to("q0")
                .from("q2").on('0').to("q1").on('1').to("q2")
                .withAccepting("q0")
                .create();
    }

    public static void main(String[] args) throws Exception {

        // Target DFA 
        CompactDFA<Character> targetDFA = constructSUL();
        Alphabet<Character> alphabet = targetDFA.getInputAlphabet();
       
        // DatasetLoad
        
        Map<List<Character>, Boolean> dataset = DatasetLoader.load("data/train.1.gz"); // Abbadingo Dataset
        //Map<List<Character>, Boolean> dataset = RandomDatasetGenerator.generate(targetDFA, alphabet, 100, 6, 42L); //Random dataset
        Map<List<Character>, Boolean> testSet = DatasetLoader.load("data/test.1.gz"); //  Abbadingo testset


        // This is just a code to check how gz files are parsed 
        try (FileWriter writer = new FileWriter("dataset1_output.txt")) {
            for (Map.Entry<List<Character>, Boolean> entry : dataset.entrySet()) {
                List<Character> input = entry.getKey();
                Boolean output = entry.getValue();

                String inputStr = input.stream()
                        .map(String::valueOf)
                        .reduce((a, b) -> a + " " + b)
                        .orElse("");

                writer.write(inputStr + " -> " + (output ? "1" : "0") + "\n");
            }
            System.out.println("✅ Dataset 저장 완료: datase1t_output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("testset1_output.txt")) {
            for (Map.Entry<List<Character>, Boolean> entry : testSet.entrySet()) {
                List<Character> input = entry.getKey();
                Boolean output = entry.getValue();

                String inputStr = input.stream()
                        .map(String::valueOf)
                        .reduce((a, b) -> a + " " + b)
                        .orElse("");

                writer.write(inputStr + " -> " + (output ? "1" : "0") + "\n");
            }
            System.out.println("✅ Dataset 저장 완료: testset1_output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Teacher
        ExampleBasedTeacher teacher = new ExampleBasedTeacher(dataset);
        

        // Membership Oracle
        DFACounterOracle<Character> mqOracle = new DFACounterOracle<>(teacher);

        // Lstar and TTT 
        ClassicLStarDFA<Character> learner = Algorithms.createLStar(alphabet, mqOracle);
        //TTTLearnerDFA<Character> learner = Algorithms.createTTT(alphabet, mqOracle);

        // Experiement
        DFAExperiment<Character> experiment = new DFAExperiment<>(learner, teacher, alphabet);
        experiment.setProfile(true);
        experiment.run();
       


        // Print Result
        DFA<?, Character> hypothesis = experiment.getFinalHypothesis();

        System.out.println("States: " + hypothesis.size());
        System.out.println("Sigma: " + alphabet.size());

        // GraphDOT.write(hypothesis, alphabet, System.out);
        // Visualization.visualize(hypothesis, alphabet);


        //In our experiment, roundCount is closely related to the number of equivalence queries (EQs), as each round typically follows an EQ that reveals a counterexample.

        double acc = Evaluator.evaluateAccuracy(
                hypothesis,
                testSet,
                "LStar", // or "TTT"
                (int) experiment.getRounds().getCount(),
                
                
             // save file
                "results.csv"
        );

        System.out.println("Accuracy: " + acc + "%");
        System.out.println("MQ:" + mqOracle.getStatisticalData().getSummary()); 
        System.out.println("EQ : " + experiment.getRounds().getCount());
        
    }

   
}
