package constraints;

public class Constraint {

    private String predicate;
    private NumberMutation.Options numberMutationOption;
    private StringMutation.Options stringMutationOption;
    private DateTimeMutation.Options dateTimeMutationOption;
    private String[] mutationParameters;
    public enum MutationType {
        DateTime, Number, String
    }
    private MutationType mutationType;


    Constraint(String predicate,
               NumberMutation.Options numberMutationOption,
               String... mutationParameters) {
        this.predicate = predicate;
        this.numberMutationOption = numberMutationOption;
        this.stringMutationOption = null;
        this.dateTimeMutationOption = null;
        this.mutationParameters = mutationParameters;
        this.mutationType = MutationType.Number;
    }


    Constraint(String predicate,
               StringMutation.Options stringMutationOption,
               String... mutationParameters) {
        this.predicate = predicate;
        this.numberMutationOption = null;
        this.stringMutationOption = stringMutationOption;
        this.dateTimeMutationOption = null;
        this.mutationParameters = mutationParameters;
        this.mutationType = MutationType.String;
    }


    Constraint(String predicate,
               DateTimeMutation.Options dateTimeMutationOption,
               String... mutationParameters) {
        this.predicate = predicate;
        this.numberMutationOption = null;
        this.stringMutationOption = null;
        this.dateTimeMutationOption = dateTimeMutationOption;
        this.mutationParameters = mutationParameters;
        this.mutationType = MutationType.DateTime;
    }


    public String getPredicate() {
        return this.predicate;
    }


    public MutationType getMutationType() {
        return this.mutationType;
    }


    public Object getMutationOption() {
        if(numberMutationOption != null) {
            return numberMutationOption;
        }
        else if(dateTimeMutationOption != null) {
            return dateTimeMutationOption;
        }
        else {
            return stringMutationOption;
        }
    }


    public String[] getMutationParameters() {
        return this.mutationParameters;
    }


}
