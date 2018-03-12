package constraints;

import java.text.NumberFormat;
import java.util.Locale;


public class NumberMutation {

    public enum Options {
        NegativeInteger, PositiveInteger,
        NegativeOutOfRangeInteger, PositiveOutOfRangeInteger,
        NegativeFloatingPoint, PositiveFloatingPoint,
        NegativeIntegerWithComma, PositiveIntegerWithComma,
        NegativeFloatingPointWithComma, PositiveFloatingPointWithComma,
        ScientificNotation, Zero, EuropeanStyle
    }


    public static String mutate(Options option, String... changeData) throws Exception {
        String number = "";
        int index = 0;

        switch (option) {
            case NegativeInteger:
                number = createNegativeInteger();
                break;
            case PositiveInteger:
                number = createPositiveInteger();
                break;
            case NegativeOutOfRangeInteger:
                number = createNegativeOutOfRangeInteger();
                break;
            case PositiveOutOfRangeInteger:
                number = createPositiveOfOutRangeInteger();
                break;
            case NegativeFloatingPoint:
                number = createNegativeFloatingPoint();
                break;
            case PositiveFloatingPoint:
                number = createPositiveFloatingPoint();
                break;
            case NegativeIntegerWithComma:
                number = createNegativeIntegerWithComma();
                break;
            case PositiveIntegerWithComma:
                number = createPositiveIntegerWithComma();
                break;
            case NegativeFloatingPointWithComma:
                number = createNegativeFloatingPointWithComma();
                break;
            case PositiveFloatingPointWithComma:
                number = createPositiveFloatingPointWithComma();
                break;
            case ScientificNotation:
                number = createScientificNotationNumber();
                break;
            case Zero:
                number = createZero();
                break;
            case EuropeanStyle:
                number = convertToEuropeanStyle(changeData[index++]);
            default:
                break;
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

    public static String createNegativeOutOfRangeInteger() {
        String negativeInteger = createNegativeInteger();
        String positiveInteger = createPositiveInteger();
        return negativeInteger + positiveInteger + positiveInteger;
    }

    public static String createPositiveOfOutRangeInteger() {
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
