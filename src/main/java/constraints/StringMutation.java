package constraints;

public class StringMutation {

    public enum Options {
        ReplaceCharacter, AppendNumberOfCharacters, Truncate, Emptify,
        MultipleSpaces, LeadingSpace, TrailingSpace, Random, InsertSpecialCharacters
    }

    public static String mutate(String originalString, Options option, String... changeData)
            throws Exception {
        String mutatedString = "";
        int changeDataIndex = 0;

        switch (option) {
            case ReplaceCharacter:
                mutatedString = replaceCharacter(originalString, changeData[changeDataIndex++], changeData[changeDataIndex++]);
                break;
            case AppendNumberOfCharacters:
                mutatedString = appendNumberOfCharacter(originalString, Integer.parseInt(changeData[changeDataIndex++]));
                break;
            case Truncate:
                mutatedString = truncate(originalString, Integer.parseInt(changeData[changeDataIndex++]));
                break;
            case Emptify:
                mutatedString = emptify();
                break;
            case MultipleSpaces:
                mutatedString = changeToMultiSpaces(originalString);
                break;
            case LeadingSpace:
                mutatedString = addLeadingSpace(originalString);
                break;
            case TrailingSpace:
                mutatedString = addTrailingSpace(originalString);
                break;
            case Random:
                mutatedString = randomize(Integer.parseInt(changeData[changeDataIndex++]));
                break;
            case InsertSpecialCharacters:
                mutatedString = insertSpecialCharacters(changeData[changeDataIndex++]);
                break;
            default:
                break;
        }

        return mutatedString;
    }


    public static String replaceCharacter(String originalString, String characterToBeReplaced, String characterToReplace) {
        return originalString.replaceAll(characterToBeReplaced, characterToReplace);
    }

    public static String appendNumberOfCharacter(String originalString, int howManyCharactersToAppend) {
        return originalString + randomize(howManyCharactersToAppend);
    }


    public static String truncate(String originalString, int howManyCharactersToTruncate) {
        if(howManyCharactersToTruncate >= originalString.length()) {
            return emptify();
        }
        else {
            return originalString.substring(0, originalString.length() - howManyCharactersToTruncate);
        }
    }


    public static String emptify() {
        return "";
    }


    public static String changeToMultiSpaces(String originalString) {
        String threeEmptySpaces = "   ";
        String mutatedString = "";

        if(originalString.contains(" ")) {
            mutatedString = originalString.replaceAll(" ", threeEmptySpaces);
        }
        else if(originalString.length() == 0) {
            mutatedString = threeEmptySpaces;
        }
        else {
            mutatedString = threeEmptySpaces + originalString;
        }

        return mutatedString;
    }


    public static String addLeadingSpace(String originalString) {
        return " " + originalString;
    }

    public static String addTrailingSpace(String originalString) {
        return originalString + " ";
    }


    public static String randomize(int length) {
        int randomNumber = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < length; i++) {
            randomNumber = (int)(Math.random() * 256);
            stringBuilder.append(Character.toString((char) randomNumber));
        }

        return stringBuilder.toString();
    }


    public static String insertSpecialCharacters(String originalString) {
        StringBuilder stringBuilder = new StringBuilder(originalString);
        String[] specialCharacters = {
                "奥", "и", "모", "á", ":", "'", "?", "Ø", "\t", "\r\n"
        };
        int randomNumber = 0;

        for(String character : specialCharacters) {
            randomNumber = (int)(Math.random() * stringBuilder.length());
            stringBuilder.insert(randomNumber, character);
        }

        return stringBuilder.toString();
    }

}
