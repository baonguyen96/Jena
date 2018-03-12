package constraints;

public class Constraint {

    private String predicate;
    private int mutationOption;
    private String[] mutationParameters;


    public Constraint(String predicate, int mutationOption, String... mutationParameters) {
        this.predicate = predicate;
        this.mutationOption = mutationOption;
        this.mutationParameters = mutationParameters;
    }


    public String getPredicate() {
        return predicate;
    }


    public int getMutationOption() {
        return mutationOption;
    }


    public String[] getMutationParameters() {
        return mutationParameters;
    }
}
