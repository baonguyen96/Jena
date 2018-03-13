package constraints;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Constraint {

    private String predicate;
    private int mutationOption;
    private LinkedList<String> mutationParameters;


    public Constraint(String predicate, int mutationOption, String... mutationParameters) {
        this.predicate = predicate;
        this.mutationOption = mutationOption;
        this.mutationParameters = new LinkedList<>(Arrays.asList(mutationParameters));
    }


    public String getPredicate() {
        return predicate;
    }


    public int getMutationOption() {
        return mutationOption;
    }


    public void appendMutationParameter(String newParameter) {
        this.mutationParameters.add(newParameter);
    }


    public String[] getMutationParameters() {
        return mutationParameters.toArray(new String[mutationParameters.size()]);
    }
}
