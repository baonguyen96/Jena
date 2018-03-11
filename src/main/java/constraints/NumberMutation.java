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


    public static String mutate(Options option) {
        String number = "";

        switch (option) {
            case NegativeInteger:
                number = getNegativeInteger();
                break;
            case PositiveInteger:
                number = getPositiveInteger();
                break;
            case NegativeOutOfRangeInteger:
                number = getNegativeOutOfRangeInteger();
                break;
            case PositiveOutOfRangeInteger:
                number = getPositiveOfOutRangeInteger();
                break;
            case NegativeFloatingPoint:
                number = getNegativeFloatingPoint();
                break;
            case PositiveFloatingPoint:
                number = getPositiveFloatingPoint();
                break;
            case NegativeIntegerWithComma:
                number = getNegativeIntegerWithComma();
                break;
            case PositiveIntegerWithComma:
                number = getPositiveIntegerWithComma();
                break;
            case NegativeFloatingPointWithComma:
                number = getNegativeFloatingPointWithComma();
                break;
            case PositiveFloatingPointWithComma:
                number = getPositiveFloatingPointWithComma();
                break;
            case ScientificNotation:
                number = getScientificNotationNumber();
                break;
            case Zero:
                number = getZero();
                break;
            default:
                break;
        }

        return number;
    }


    public static String getNegativeInteger() {
        int number = (int)(Math.random() * Integer.MAX_VALUE);
        return Integer.toString(-1 * number);
    }

    public static String getPositiveInteger() {
        int number = (int)(Math.random() * Integer.MAX_VALUE);
        return Integer.toString(number);
    }

    public static String getNegativeOutOfRangeInteger() {
        String negativeInteger = getNegativeInteger();
        String positiveInteger = getPositiveInteger();
        return negativeInteger + positiveInteger + positiveInteger;
    }

    public static String getPositiveOfOutRangeInteger() {
        String positiveInteger = getPositiveInteger();
        return positiveInteger + positiveInteger + positiveInteger;
    }

    public static String getNegativeFloatingPoint() {
        return Double.toString(-1 * (Math.random() * Double.MAX_VALUE));
    }

    public static String getPositiveFloatingPoint() {
        return Double.toString(Math.random() * Double.MAX_VALUE);
    }

    public static String getNegativeIntegerWithComma() {
        int number = (int)(Math.random() * Integer.MAX_VALUE);
        return NumberFormat.getNumberInstance(Locale.US).format(-1 * number);
    }

    public static String getPositiveIntegerWithComma() {
        int number = (int)(Math.random() * Integer.MAX_VALUE);
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

    public static String getNegativeFloatingPointWithComma() {
        double number = (Math.random() * Integer.MAX_VALUE);
        return NumberFormat.getNumberInstance(Locale.ENGLISH).format(-1 * number);
    }

    public static String getPositiveFloatingPointWithComma() {
        double number = (Math.random() * Integer.MAX_VALUE);
        return NumberFormat.getNumberInstance(Locale.ENGLISH).format(number);
    }

    public static String getScientificNotationNumber() {
        return Double.toString(Math.random() * Double.MAX_VALUE);
    }


    public static String getZero() {
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
