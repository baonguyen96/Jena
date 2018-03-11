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
        StringMutation.Options[] options = {
                StringMutation.Options.Emptify,
                StringMutation.Options.AppendNumberOfCharacters
        };
        String[] changeData = {"2"};

        mutatedString = StringMutation.mutate(originalString, options, changeData);
        assertEquals(2, mutatedString.length());

        options = new StringMutation.Options[]{
                StringMutation.Options.ReplaceCharacter,
                StringMutation.Options.LeadingSpace,
                StringMutation.Options.MultipleSpaces,
                StringMutation.Options.AppendNumberOfCharacters
        };
        changeData = new String[]{"l", "j", "2"};
        mutatedString = StringMutation.mutate(originalString, options, changeData);
        assertTrue(mutatedString.startsWith("   Hejjo"));
        assertEquals(originalString.length() + 5, mutatedString.length());
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
        mutatedString = StringMutation.appendNumberOfCharacter(originalString, numberOfCharactersToAppend);

        assertEquals(originalString.length() + numberOfCharactersToAppend, mutatedString.length());
    }


    @Test
    void testTruncate() {
        originalString = "Hello";
        mutatedString = StringMutation.truncate(originalString, 1);
        assertEquals("Hell", mutatedString);

        mutatedString = StringMutation.truncate(originalString, 5);
        assertEquals("", mutatedString);

        mutatedString = StringMutation.truncate(originalString, 6);
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
        mutatedString = StringMutation.insertSpecialCharacters(originalString);
        assertNotEquals(originalString, mutatedString);
        assertTrue(originalString.length() < mutatedString.length());
        assertFalse(mutatedString.matches("\\A\\p{ASCII}*\\z"));
    }
}