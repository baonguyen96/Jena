package constraints;

import annotations.Note;


class StringMutation {

    @Note(message = "Some parameters appear to be out-of-order. DO NOT CHANGE THE ORDER.")
    public static String mutate(int option, String... data) {
        String mutatedString = "";

        if (option == Mutation.REPLACE_CHARACTER) {
            mutatedString = replaceCharacter(data[2], data[0], data[1]);
        }
        else if (option == Mutation.APPEND_NUMBER_OF_CHARACTERS) {
            mutatedString = appendNumberOfCharacters(data[1], Integer.parseInt(data[0]));
        }
        else if (option == Mutation.TRUNCATE_NUMBER_OF_CHARACTERS) {
            mutatedString = truncateNumberOfCharacters(data[1], Integer.parseInt(data[0]));
        }
        else if (option == Mutation.EMPTIFY) {
            mutatedString = emptify();
        }
        else if (option == Mutation.CONVERT_TO_MULTIPLE_SPACES) {
            mutatedString = changeToMultiSpaces(data[0]);
        }
        else if (option == Mutation.ADD_LEADING_SPACE) {
            mutatedString = addLeadingSpace(data[0]);
        }
        else if (option == Mutation.ADD_TRAILING_SPACE) {
            mutatedString = addTrailingSpace(data[0]);
        }
        else if (option == Mutation.CREATE_RANDOM_STRING) {
            mutatedString = createRandomString(Integer.parseInt(data[0]));
        }
        else if (option == Mutation.ADD_SPECIAL_CHARACTERS) {
            mutatedString = addSpecialCharacters(data[0]);
        }
        else {
            mutatedString = "";
        }

        return mutatedString;
    }


    public static String replaceCharacter(String originalString, String characterToBeReplaced, String characterToReplace) {
        return originalString.replaceAll(characterToBeReplaced, characterToReplace);
    }

    public static String appendNumberOfCharacters(String originalString, int howManyCharactersToAppend) {
        return originalString + createRandomString(howManyCharactersToAppend);
    }


    public static String truncateNumberOfCharacters(String originalString, int howManyCharactersToTruncate) {
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


    public static String createRandomString(int length) {
        int randomNumber = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < length; i++) {
            randomNumber = (int)(Math.random() * 256);
            stringBuilder.append(Character.toString((char) randomNumber));
        }

        return stringBuilder.toString();
    }


    public static String addSpecialCharacters(String originalString) {
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
