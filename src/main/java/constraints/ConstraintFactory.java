package constraints;

public class ConstraintFactory {

    public static Constraint createDateTimeConstraint(
            String predicate,
            DateTimeMutation.Options options,
            String... mutationParameters) {
        return new Constraint(predicate, options, mutationParameters);
    }


    public static Constraint createNumberConstraint(
            String predicate,
            NumberMutation.Options options,
            String... mutationParameters) {
        return new Constraint(predicate, options, mutationParameters);
    }


    public static Constraint createStringConstraint(
            String predicate,
            StringMutation.Options options,
            String... mutationParameters) {
        return new Constraint(predicate, options, mutationParameters);
    }

}
