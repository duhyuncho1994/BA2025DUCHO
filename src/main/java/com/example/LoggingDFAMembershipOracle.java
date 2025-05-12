package com.example;

import de.learnlib.query.DefaultQuery;
import de.learnlib.query.Query;
import de.learnlib.oracle.MembershipOracle.DFAMembershipOracle;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LoggingDFAMembershipOracle implements DFAMembershipOracle<Character> {

    private final DFAMembershipOracle<Character> delegate;
    private final Map<List<Character>, Boolean> log;

    public LoggingDFAMembershipOracle(DFAMembershipOracle<Character> delegate, Map<List<Character>, Boolean> log) {
        this.delegate = delegate;
        this.log = log;
    }

    @Override
    public void processQueries(Collection<? extends Query<Character, Boolean>> queries) {
        //Delegate to actual membership oracle for processing
        delegate.processQueries(queries);

        // Save each query result to log (Map)
        for (Query<Character, Boolean> query : queries) {
            if (query instanceof DefaultQuery) {
                DefaultQuery<Character, Boolean> dq = (DefaultQuery<Character, Boolean>) query;
                List<Character> input = dq.getInput().asList();
                Boolean output = dq.getOutput();
                log.put(input, output);
            } else {
                throw new IllegalStateException("Query is not a DefaultQuery");
            }
        }
    }
}
