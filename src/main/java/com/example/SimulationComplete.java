
package com.example;

import de.learnlib.acex.AcexAnalyzers;
import de.learnlib.algorithm.lstar.dfa.ClassicLStarDFA;
import de.learnlib.algorithm.lstar.dfa.ClassicLStarDFABuilder;
import de.learnlib.algorithm.ttt.dfa.TTTLearnerDFA;
import de.learnlib.algorithm.ttt.dfa.TTTLearnerDFABuilder;
import de.learnlib.oracle.MembershipOracle;
import de.learnlib.query.DefaultQuery;
import de.learnlib.query.Query;
import net.automatalib.alphabet.Alphabet;
import net.automatalib.alphabet.impl.Alphabets;
import net.automatalib.automaton.fsa.DFA;
import net.automatalib.automaton.fsa.impl.CompactDFA;
import net.automatalib.util.automaton.Automata;
import net.automatalib.word.Word;
import java.util.*;

public class SimulationComplete{

    // S_log tracker
    static class LoggingOracle implements MembershipOracle<Character, Boolean> {
        private final DFA<?, Character> target;
        private final Set<Word<Character>> sLog = new LinkedHashSet<>();

        public LoggingOracle(DFA<?, Character> target) {
            this.target = target;
        }

        @Override
        public void processQueries(Collection<? extends Query<Character, Boolean>> queries) {
            for (Query<Character, Boolean> q : queries) {
                Word<Character> input = q.getInput();
                q.answer(target.accepts(input));
                sLog.add(input);
            }
        }

        public int getSampleSize() {
            return sLog.size();
        }

        public int getSymbolCount() {
            return sLog.stream().mapToInt(Word::length).sum();
        }
    }

    // Generate a random DFA
    public static CompactDFA<Character> generateRandomDFA(Alphabet<Character> alphabet, Random rand) {
    int numStates = rand.nextInt(31) + 30; // state interval 30~60
    CompactDFA<Character> dfa = new CompactDFA<>(alphabet);
    List<Integer> states = new ArrayList<>();

    // state (randomly)
    for (int i = 0; i < numStates; i++) {
        boolean isAccepting = rand.nextDouble() < 0.5; // with 50% : final state
        states.add(dfa.addState(isAccepting));
    }

    // init
    dfa.setInitialState(states.get(rand.nextInt(states.size())));

    // transition function
    for (int state : states) {
        for (Character sym : alphabet) {
            int target = states.get(rand.nextInt(states.size()));
            dfa.addTransition(state, sym, target);
        }
    }

    return dfa;
}

    public static void runExperiment(DFA<?, Character> target, Alphabet<Character> alphabet, String algName) {
        LoggingOracle oracle = new LoggingOracle(target);

        if (algName.equals("LSTAR")) {
            ClassicLStarDFA<Character> learner =
                new ClassicLStarDFABuilder<Character>().withAlphabet(alphabet) // input alphabet
                                                       .withOracle(oracle) // membership oracle
                                                       .create();
            learner.startLearning();
            while (true) {
                DFA<?, Character> hypothesis = learner.getHypothesisModel();
                Word<Character> ce = Automata.findSeparatingWord(target, hypothesis, alphabet);
                if (ce == null) break;

                DefaultQuery<Character, Boolean> ceq = new DefaultQuery<>(ce, target.accepts(ce));
                learner.refineHypothesis(ceq);
            }
        } else if (algName.equals("TTT")) {
            TTTLearnerDFA<Character> learner = new TTTLearnerDFABuilder<Character>().withAlphabet(alphabet)
                                                                                    .withOracle(oracle)
                                                                                    .withAnalyzer(AcexAnalyzers.LINEAR_FWD)
                                                                                    .create();
            learner.startLearning();
            while (true) {
                DFA<?, Character> hypothesis = learner.getHypothesisModel();
                Word<Character> ce = Automata.findSeparatingWord(target, hypothesis, alphabet);
                if (ce == null) break;
                DefaultQuery<Character, Boolean> ceq = new DefaultQuery<>(ce, target.accepts(ce));
                learner.refineHypothesis(ceq);
            }
        }

        // Output result
        System.out.printf("%-6s | Words: %3d | Symbols: %4d\n",
                algName, oracle.getSampleSize(), oracle.getSymbolCount());
    }

    public static void main(String[] args) {
        Alphabet<Character> alphabet = Alphabets.characters('a', 'b');
        

        Random rand = new Random();

        for (int i = 1; i <= 5; i++) {
            System.out.println("\n--- DFA " + i + " ---");
            CompactDFA<Character> target = generateRandomDFA(alphabet, rand);
            runExperiment(target, alphabet, "LSTAR");
            runExperiment(target, alphabet, "TTT");
        }
    }
}
