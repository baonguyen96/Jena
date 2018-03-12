package constraints;

import java.text.NumberFormat;
import java.util.Locale;


class NumberMutation {
    public static String mutate(final int OPTION, String... changeData) {
        String number = "";

        if(OPTION == Mutation.CREATE_NEGATIVE_INTEGER) {
            number = createNegativeInteger();
        }
        else if(OPTION == Mutation.CREATE_POSITIVE_INTEGER) {
            number = createPositiveInteger();
        }
        else if(OPTION == Mutation.CREATE_NEGATIVE_OVERFLOW_INTEGER) {
            number = createNegativeOverflowInteger();
        }
        else if(OPTION == Mutation.CREATE_POSITIVE_OVERFLOW_INTEGER) {
            number = createPositiveOverflowInteger();
        }
        else if(OPTION == Mutation.CREATE_NEGATIVE_FLOATING_POINT) {
            number = createNegativeFloatingPoint();
        }
        else if(OPTION == Mutation.CREATE_POSITIVE_FLOATING_POINT) {
            number = createPositiveFloatingPoint();
        }
        else if(OPTION == Mutation.CREATE_NEGATIVE_INTEGER_WITH_COMMA) {
            number = createNegativeIntegerWithComma();
        }
        else if(OPTION == Mutation.CREATE_POSITIVE_INTEGER_WITH_COMMA) {
            number = createPositiveIntegerWithComma();
        }
        else if(OPTION == Mutation.CREATE_NEGATIVE_FLOATING_POINT_WITH_COMMA) {
            number = createNegativeFloatingPointWithComma();
        }
        else if(OPTION == Mutation.CREATE_POSITIVE_FLOATING_POINT_WITH_COMMA) {
            number = createPositiveFloatingPointWithComma();
        }
        else if(OPTION == Mutation.CREATE_SCIENTIFIC_NOTATION_NUMBER) {
            number = createScientificNotationNumber();
        }
        else if(OPTION == Mutation.CREATE_ZERO) {
            number = createZero();
        }
        else if(OPTION == Mutation.CONVERT_TO_EUROPEAN_STYLE) {
            number = convertToEuropeanStyle(changeData[0]);
        }
        else {
            number = "";
        }

        return number;
    }


    public static String createNegativeInteger() {
        int number = (int)(Math.random() * Integer.MAX_VALUE);
        return Integer.toString(-1 * number);
    }

    public static String createPositiveInteger() {
        int number = (int)(Math.random() * Integer.MAX_VALUE);
        return Integer.toString(number);
    }

    public static String createNegativeOverflowInteger() {
        String negativeInteger = createNegativeInteger();
        String positiveInteger = createPositiveInteger();
        return negativeInteger + positiveInteger + positiveInteger;
    }

    public static String createPositiveOverflowInteger() {
        String positiveInteger = createPositiveInteger();
        return positiveInteger + positiveInteger + positiveInteger;
    }

    public static String createNegativeFloatingPoint() {
        return Double.toString(-1 * (Math.random() * Double.MAX_VALUE));
    }

    public static String createPositiveFloatingPoint() {
        return Double.toString(Math.random() * Double.MAX_VALUE);
    }

    public static String createNegativeIntegerWithComma() {
        int number = (int)(Math.random() * Integer.MAX_VALUE);
        return NumberFormat.getNumberInstance(Locale.US).format(-1 * number);
    }

    public static String createPositiveIntegerWithComma() {
        int number = (int)(Math.random() * Integer.MAX_VALUE);
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

    public static String createNegativeFloatingPointWithComma() {
        double number = (Math.random() * Integer.MAX_VALUE);
        return NumberFormat.getNumberInstance(Locale.ENGLISH).format(-1 * number);
    }

    public static String createPositiveFloatingPointWithComma() {
        double number = (Math.random() * Integer.MAX_VALUE);
        return NumberFormat.getNumberInstance(Locale.ENGLISH).format(number);
    }

    public static String createScientificNotationNumber() {
        return Double.toString(Math.random() * Double.MAX_VALUE);
    }


    public static String createZero() {
        return "0";
    }


    public static String convertToEuropeanStyle(String number) {
        double realNumber = Double.parseDouble(number);
        String europeanStyle = NumberFormat.getNumberInstance(Locale.ENGLISH).format(realNumber);
        europeanStyle = europeanStyle.replaceAll("\\.", "a");
        europeanStyle = europeanStyle.replaceAll(",", ".");
        europeanStyle = europeanStyle.replaceAll("a", ",");
        return europeanStyle;
    }
    
}
