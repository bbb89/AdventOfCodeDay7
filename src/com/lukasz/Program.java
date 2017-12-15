package com.lukasz;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private String name;
    private int weight;
    private List<Program> subprograms;

    public Program(String name, int weight) {
        this.name = name;
        this.weight = weight;
        subprograms = new ArrayList<>();
    }

    public Program(String name, int weight, List<Program> subprograms) {
        this.name = name;
        this.weight = weight;
        this.subprograms = subprograms;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void addSubprogram(Program subprogram) {
        subprograms.add(subprogram);
    }

    public List<Program> getSubprograms() {
        return subprograms;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals( ((Program) obj).getName() );
    }

    @Override
    public String toString() {
        return name + "(" + weight + ")";
    }
}
