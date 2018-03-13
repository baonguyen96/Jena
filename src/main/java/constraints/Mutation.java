/*
 * assume at one time user can only apply 1 mutation rule to a particular predicate
 * i.e.
 *      allow:      zip -> 6 characters, age -> negative float with comma
 *      not allow:  zip -> {6 characters, change characters}, age -> {zero, overflow}
 */

package constraints;

public class Mutation {

    private static int mutationOptions = 0;

    // number mutations
    public static final int CREATE_NEGATIVE_INTEGER = mutationOptions++;
    public static final int CREATE_POSITIVE_INTEGER = mutationOptions++;
    public static final int CREATE_NEGATIVE_OVERFLOW_INTEGER = mutationOptions++;
    public static final int CREATE_POSITIVE_OVERFLOW_INTEGER = mutationOptions++;
    public static final int CREATE_NEGATIVE_FLOATING_POINT = mutationOptions++;
    public static final int CREATE_POSITIVE_FLOATING_POINT = mutationOptions++;
    public static final int CREATE_NEGATIVE_INTEGER_WITH_COMMA = mutationOptions++;
    public static final int CREATE_POSITIVE_INTEGER_WITH_COMMA = mutationOptions++;
    public static final int CREATE_NEGATIVE_FLOATING_POINT_WITH_COMMA= mutationOptions++;
    public static final int CREATE_POSITIVE_FLOATING_POINT_WITH_COMMA = mutationOptions++;
    public static final int CREATE_SCIENTIFIC_NOTATION_NUMBER = mutationOptions++;
    public static final int CREATE_ZERO = mutationOptions++;
    public static final int CONVERT_TO_EUROPEAN_STYLE = mutationOptions;
    private static final int NUMER_MUTATION_MARKER = mutationOptions++;


    // string mutations
    public static final int REPLACE_CHARACTER = mutationOptions++;
    public static final int APPEND_NUMBER_OF_CHARACTERS = mutationOptions++;
    public static final int TRUNCATE_NUMBER_OF_CHARACTERS = mutationOptions++;
    public static final int EMPTIFY = mutationOptions++;
    public static final int CONVERT_TO_MULTIPLE_SPACES = mutationOptions++;
    public static final int ADD_LEADING_SPACE = mutationOptions++;
    public static final int ADD_TRAILING_SPACE = mutationOptions++;
    public static final int CREATE_RANDOM_STRING = mutationOptions++;
    public static final int ADD_SPECIAL_CHARACTERS = mutationOptions;
    private static final int STRING_MUTATION_MARKER = mutationOptions++;


    // date-time mutations
    public static final int CREATE_TODAY_DATE = mutationOptions++;
    public static final int CREATE_PAST_OR_FUTURE_DATE = mutationOptions++;
    public static final int CREATE_ALWAYS_INVALID_DATES = mutationOptions++;
    public static final int CREATE_DATE_IN_ALL_FORMATS_EXCEPT = mutationOptions++;
    public static final int CREATE_DATE_IN_FORMAT = mutationOptions++;
    public static final int CREATE_LEAP_DATES = mutationOptions;
    private static final int DATE_TIME_MUTATION_MARKER = mutationOptions++;


    public static String mutate(int mutationOption, String... mutationData) {
        String generatedData = "";

        if(mutationOption <= NUMER_MUTATION_MARKER) {
            generatedData = NumberMutation.mutate(mutationOption, mutationData);
        }
        else if(mutationOption > NUMER_MUTATION_MARKER && mutationOption <= STRING_MUTATION_MARKER) {
            generatedData = StringMutation.mutate(mutationOption, mutationData);
        }
        else if(mutationOption > STRING_MUTATION_MARKER && mutationOption <= DATE_TIME_MUTATION_MARKER) {
            generatedData = DateTimeMutation.mutate(mutationOption, mutationData);
        }
        else {
            generatedData = "";
        }

        return generatedData;
    }




}
