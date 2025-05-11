package com.example;

import de.learnlib.oracle.MembershipOracle;
import de.learnlib.oracle.EquivalenceOracle;
import de.learnlib.query.DefaultQuery;
import de.learnlib.query.Query;
import net.automatalib.word.*;
import net.automatalib.automaton.fsa.DFA;

import java.util.*;

public class SampleBasedTeacher<I> implements MembershipOracle<I, Boolean>, EquivalenceOracle<DFA<?, I>, I, Boolean> {

    private final Map<List<I>, Boolean> membershipData;
    private final List<List<I>> counterExamples;

    public SampleBasedTeacher(List<List<I>> mqSamples, List<List<I>> eqCounterexamples, MembershipOracle<I, Boolean> baseOracle) {
        this.membershipData = new HashMap<>();
        this.counterExamples = new ArrayList<>(eqCounterexamples);

        // Evaluate MQs using base oracle
        List<DefaultQuery<I, Boolean>> queries = new ArrayList<>();
        for (List<I> input : mqSamples) {
            Word<I> word = Word.fromList(input);
            queries.add(new DefaultQuery<>(word));
        }

        baseOracle.processQueries(queries);

        for (DefaultQuery<I, Boolean> query : queries) {
            membershipData.put(query.getInput().asList(), query.getOutput());
        }
    }

    @Override
    public void processQueries(Collection<? extends Query<I, Boolean>> queries) {
        for (Query<I, Boolean> query : queries) {
            List<I> input = query.getInput().asList();
            Boolean answer = membershipData.get(input);

            if (answer == null) {
                throw new IllegalStateException("No membership answer recorded for input: " + input);
            }

            query.answer(answer);
        }
    }

    @Override
    public DefaultQuery<I, Boolean> findCounterExample(DFA<?, I> hypothesis, Collection<? extends I> inputs) {
        for (List<I> ce : counterExamples) {
            Word<I> input = Word.fromList(ce);
            boolean hypOutput = hypothesis.accepts(input);
            Boolean trueOutput = membershipData.get(ce);

            if (trueOutput == null) continue;

            if (hypOutput != trueOutput) {
                return new DefaultQuery<>(input, trueOutput);
            }
        }

        return null;
    }
}
