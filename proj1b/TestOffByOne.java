import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testLowercaseLetters() {
        CharacterComparator offByOne = new OffByOne();
        // Test true cases
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertTrue(offByOne.equalChars('x', 'y'));

        // Test false cases
        assertFalse(offByOne.equalChars('a', 'c'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('x', 'z'));
    }

    @Test
    public void testUppercaseLetters() {
        CharacterComparator offByOne = new OffByOne();
        // Test true cases
        assertTrue(offByOne.equalChars('A', 'B'));
        assertTrue(offByOne.equalChars('B', 'A'));
        assertTrue(offByOne.equalChars('X', 'Y'));

        // Test false cases
        assertFalse(offByOne.equalChars('A', 'C'));
        assertFalse(offByOne.equalChars('A', 'A'));
        assertFalse(offByOne.equalChars('X', 'Z'));
    }

    @Test
    public void testMixedCaseLetters() {
        CharacterComparator offByOne = new OffByOne();
        // Test false cases for mixed case letters
        assertFalse(offByOne.equalChars('a', 'B'));
        assertFalse(offByOne.equalChars('A', 'b'));
        assertFalse(offByOne.equalChars('x', 'Y'));
    }

    @Test
    public void testDigits() {
        CharacterComparator offByOne = new OffByOne();
        // Test true cases
        assertTrue(offByOne.equalChars('1', '2'));
        assertTrue(offByOne.equalChars('2', '1'));
        assertTrue(offByOne.equalChars('8', '9'));

        // Test false cases
        assertFalse(offByOne.equalChars('0', '2'));
        assertFalse(offByOne.equalChars('5', '5'));
        assertFalse(offByOne.equalChars('7', '9'));
    }

    @Test
    public void testSpecialCharacters() {
        CharacterComparator offByOne = new OffByOne();
        // Test true cases
        assertTrue(offByOne.equalChars('!', '"'));  // ASCII 33 and 34
        assertTrue(offByOne.equalChars('#', '$'));  // ASCII 35 and 36

        // Test false cases
        assertFalse(offByOne.equalChars('!', '#'));
        assertFalse(offByOne.equalChars('&', '&'));
    }

    @Test
    public void testBoundaryConditions() {
        CharacterComparator offByOne = new OffByOne();
        // Test boundary values (e.g., end of ASCII range)
        assertTrue(offByOne.equalChars('~', '}'));  // ASCII 126 and 125
        assertFalse(offByOne.equalChars('~', '|')); // ASCII 126 and 124
    }

    @Test
    public void testNonAdjacentCharacters() {
        CharacterComparator offByOne = new OffByOne();
        // Test non-adjacent characters that should return false
        assertFalse(offByOne.equalChars('a', 'z'));
        assertFalse(offByOne.equalChars('b', 'x'));
        assertFalse(offByOne.equalChars('A', 'Z'));
        assertFalse(offByOne.equalChars('1', '5'));
    }

    @Test
    public void testSameCharacter() {
        CharacterComparator offByOne = new OffByOne();
        // Test same characters that should return false
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('Z', 'Z'));
        assertFalse(offByOne.equalChars('1', '1'));
        assertFalse(offByOne.equalChars('#', '#'));
    }
}
