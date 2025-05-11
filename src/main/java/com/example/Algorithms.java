package com.example;

import net.automatalib.alphabet.Alphabet;

import de.learnlib.oracle.MembershipOracle.DFAMembershipOracle;
import de.learnlib.acex.AcexAnalyzers;
import de.learnlib.algorithm.lstar.dfa.ClassicLStarDFA;
import de.learnlib.algorithm.lstar.dfa.ClassicLStarDFABuilder;
import de.learnlib.algorithm.ttt.dfa.TTTLearnerDFA;
import de.learnlib.algorithm.ttt.dfa.TTTLearnerDFABuilder;




public class Algorithms {

    // Lstar Setting
    public static ClassicLStarDFA<Character> createLStar(Alphabet<Character> alphabet, DFAMembershipOracle<Character> oracle) {
        return new ClassicLStarDFABuilder<Character>()
                .withAlphabet(alphabet)
                .withOracle(oracle)
                .create();
    }

    // TTT Setting
    public static TTTLearnerDFA<Character> createTTT(Alphabet<Character> alphabet, DFAMembershipOracle<Character> oracle) {
        return new TTTLearnerDFABuilder<Character>()
                .withAlphabet(alphabet)
                .withOracle(oracle)
                .withAnalyzer(AcexAnalyzers.LINEAR_FWD)
                .create();
    }
    
}