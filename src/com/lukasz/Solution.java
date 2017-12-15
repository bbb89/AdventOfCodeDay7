package com.lukasz;

import java.util.*;

public class Solution {
    private Set<Program> programs;
    private Set<Program> subprograms;
    private Program bottomProgram;

    public Solution(List<String> input) {
        programs = new HashSet<>();
        subprograms = new HashSet<>();

        convertInputToPrograms(input);
        findBottomProgram();
    }

    public int findBalancedWeight() {
        int weightOfCurrentProgram = 0;
        int unbalancedWeight = 0;
        int balancedWeight = 0;
        Program currentProgram = bottomProgram;

        int indexOfUnbalanced = 0;
        while(indexOfUnbalanced >= 0) {
            List<Integer> currentLevelWeight = findCurrentLevelWeights(currentProgram);
            indexOfUnbalanced = findIndexOfUnbalanced(currentLevelWeight);
            weightOfCurrentProgram = currentProgram.getWeight();
            if(indexOfUnbalanced == -1)
                break;
            unbalancedWeight = currentLevelWeight.get(indexOfUnbalanced);
            balancedWeight = findBalancedWeightForLevel(currentLevelWeight);
            Program nextProgram = convertToProgramWithWeight(currentProgram.getSubprograms().get(indexOfUnbalanced));
            currentProgram = nextProgram;
        }

        return weightOfCurrentProgram + (balancedWeight - unbalancedWeight);
    }

    private int findIndexOfUnbalanced(List<Integer> weights) {
        int shouldBe = findBalancedWeightForLevel(weights);

        for(int i = 0; i < weights.size(); i++) {
            if(weights.get(i) != shouldBe) {
                return i;
            }
        }

        return -1;
    }

    private int findBalancedWeightForLevel(List<Integer> weights) {
        for(int i = 1; i < weights.size(); i++) {
            if(weights.get(i) == weights.get(0)) {
                return weights.get(0);
            }
        }

        return weights.get(1);
    }

    private List<Integer> findCurrentLevelWeights(Program program) {
        List<Integer> currentLevelWeights = new ArrayList<>();

        for(Program prog : program.getSubprograms()) {
            Program currentProgram = null;
            currentProgram = convertToProgramWithWeight(prog);

            int weight = currentProgram.getWeight();
            List<Integer> bottomLevelWeights = findCurrentLevelWeights(currentProgram);
            for(int bottomWeight : bottomLevelWeights) {
                weight += bottomWeight;
            }
            currentLevelWeights.add(weight);
        }

        return currentLevelWeights;
    }

    private Program convertToProgramWithWeight(Program prog) {

        for(Program p : programs) {
            if(p.equals(prog)) {
                return p;
            }
        }

        return null;
    }

    private void convertInputToPrograms(List<String> input) {
        ListIterator<String> i = input.listIterator();
        while (i.hasNext()) {
            String line = i.next();
            Scanner lineScanner = new Scanner(line);
            programs.add(parseProgram(lineScanner));

        }
    }

    private Program parseProgram(Scanner lineScanner) {
        String name = lineScanner.next();
        String weightString = lineScanner.next();
        weightString = removeBrackets(weightString);
        int weight = Integer.parseInt(weightString);
        List<Program> currentProgramSubprograms = findSubprograms(lineScanner);

        return new Program(name, weight, currentProgramSubprograms);
    }

    private List<Program> findSubprograms(Scanner lineScanner) {
        List<Program> currentProgramSubprograms = new ArrayList<>();
        if(lineScanner.hasNext()) {
            String arrow = lineScanner.next();
            while(lineScanner.hasNext()) {
                String subprogramName = lineScanner.next();
                subprogramName = subprogramName.replace(",", "");
                Program subprogram = new Program(subprogramName, 0);
                currentProgramSubprograms.add(subprogram);
                subprograms.add(subprogram);
            }

        }

        return currentProgramSubprograms;
    }

    private void findBottomProgram() {
        Set<Program> difference = new HashSet<>(programs);
        difference.removeAll(subprograms);

        for(Program p : difference) {
            bottomProgram = p;
        }
    }

    private String removeBrackets(String weightString) {
        weightString = weightString.replace("(", "");
        weightString = weightString.replace(")", "");

        return weightString;
    }

    public Program getBottomProgram() {
        return bottomProgram;
    }
}
