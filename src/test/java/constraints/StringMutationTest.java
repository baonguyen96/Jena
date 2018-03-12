package constraints;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class StringMutationTest {

    private String originalString = "";
    private String mutatedString = "";


    @BeforeEach
    void resetAllStrings() {
        originalString = mutatedString = "";
    }


    @Test
    void testMutationOptions() {
        originalString = "Hello";
        mutatedString = StringMutation.mutate(Mutation.APPEND_NUMBER_OF_CHARACTERS, originalString, "2");
        assertEquals(7, mutatedString.length());

        mutatedString = StringMutation.mutate(Mutation.REPLACE_CHARACTER, originalString, "l", "j");
        assertEquals("Hejjo", mutatedString);
    }


    @Test
    void testReplaceCharacter() {
        originalString = "Hello this is some string.";
        mutatedString = StringMutation.replaceCharacter(originalString, "i", "j");
        assertEquals("Hello thjs js some strjng.", mutatedString);
    }


    @Test
    void testAppendNumberOfCharacter() {
        int numberOfCharactersToAppend = 128;
        originalString = "Hi";
        mutatedString = StringMutation.appendNumberOfCharacters(originalString, numberOfCharactersToAppend);

        assertEquals(originalString.length() + numberOfCharactersToAppend, mutatedString.length());
    }


    @Test
    void testTruncate() {
        originalString = "Hello";
        mutatedString = StringMutation.truncateNumberOfCharacters(originalString, 1);
        assertEquals("Hell", mutatedString);

        mutatedString = StringMutation.truncateNumberOfCharacters(originalString, 5);
        assertEquals("", mutatedString);

        mutatedString = StringMutation.truncateNumberOfCharacters(originalString, 6);
        assertEquals("", mutatedString);
    }


    @Test
    void testEmptify() {
        originalString = "This is some string that is going to be emptied.";
        mutatedString = StringMutation.emptify();
        assertEquals("", mutatedString);
    }


    @Test
    void testChangeToMultiSpaces() {
        originalString = "";
        mutatedString = StringMutation.changeToMultiSpaces(originalString);
        assertEquals("   ", mutatedString);

        originalString = " ";
        mutatedString = StringMutation.changeToMultiSpaces(originalString);
        assertEquals("   ", mutatedString);

        originalString = "H";
        mutatedString = StringMutation.changeToMultiSpaces(originalString);
        assertEquals("   H", mutatedString);
    }


    @Test
    void testAddingLeadingSpace() {
        originalString = "H";
        mutatedString = StringMutation.addLeadingSpace(originalString);
        assertEquals(" H", mutatedString);

        originalString = " H";
        mutatedString = StringMutation.addLeadingSpace(originalString);
        assertEquals("  H", mutatedString);
    }


    @Test
    void testAddingTrailingSpace() {
        originalString = "H";
        mutatedString = StringMutation.addTrailingSpace(originalString);
        assertEquals("H ", mutatedString);

        originalString = " H";
        mutatedString = StringMutation.addTrailingSpace(originalString);
        assertEquals(" H ", mutatedString);
    }


    @Test
    void testInsertSpecialCharacters() {
        originalString = "Hello world";
        mutatedString = StringMutation.addSpecialCharacters(originalString);
        assertNotEquals(originalString, mutatedString);
        assertTrue(originalString.length() < mutatedString.length());
        assertFalse(mutatedString.matches("\\A\\p{ASCII}*\\z"));
    }
}