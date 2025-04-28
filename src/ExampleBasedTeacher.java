package com.example;

import de.learnlib.oracle.EquivalenceOracle.DFAEquivalenceOracle;
import de.learnlib.query.DefaultQuery;
import de.learnlib.query.Query;
import de.learnlib.oracle.MembershipOracle.DFAMembershipOracle;

import net.automatalib.automaton.fsa.DFA;
import net.automatalib.word.Word;

import java.util.*;

public class ExampleBasedTeacher implements DFAMembershipOracle<Character>, DFAEquivalenceOracle<Character> {

    private final Map<List<Character>, Boolean> sample;
    private final Set<Word<Character>> usedCounterExamples = new HashSet<>();

    public ExampleBasedTeacher(Map<List<Character>, Boolean> sample) {
        this.sample = sample;
    }

    @Override
    public void processQueries(Collection<? extends Query<Character, Boolean>> queries) {
        for (Query<Character, Boolean> q : queries) {
            List<Character> input = new ArrayList<>(q.getInput().asList());

            //If  in sample -> return that label, if not -> always false (reject)
            if (sample.containsKey(input)) {
                q.answer(sample.get(input));
            } else {
                q.answer(false); // case : Always NO 
                System.out.println("[MQ] Unseen input → default answer: false → " + input);
            }
        }
    }

    @Override
    public DefaultQuery<Character, Boolean> findCounterExample(DFA<?, Character> hypothesis, Collection<? extends Character> inputs) {
        for (Map.Entry<List<Character>, Boolean> entry : sample.entrySet()) {
            Word<Character> word = Word.fromList(entry.getKey());
            boolean expected = entry.getValue();
            boolean predicted = hypothesis.accepts(word);

            if (expected != predicted && !usedCounterExamples.contains(word)) {
                usedCounterExamples.add(word);
                return new DefaultQuery<>(word, expected);
            }
        }

        return null;
    }
}
