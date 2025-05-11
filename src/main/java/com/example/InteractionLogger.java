package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InteractionLogger {

    private final List<List<Character>> membershipQueries = new ArrayList<>();
    private final List<List<Character>> counterExamples = new ArrayList<>();

    public void logMembershipQuery(List<Character> query) {
        membershipQueries.add(query);
    }

    public void logCounterExample(List<Character> counterExample) {
        counterExamples.add(counterExample);
    }

    public List<List<Character>> getMembershipQueries() {
        return membershipQueries;
    }

    public List<List<Character>> getCounterExamples() {
        return counterExamples;
    }

    public void exportToFiles(String mqFile, String ceFile) {
        try (FileWriter mqWriter = new FileWriter(mqFile)) {
            for (List<Character> mq : membershipQueries) {
                for (Character c : mq) {
                    mqWriter.write(c);
                }
                mqWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter ceWriter = new FileWriter(ceFile)) {
            for (List<Character> ce : counterExamples) {
                for (Character c : ce) {
                    ceWriter.write(c);
                }
                ceWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
