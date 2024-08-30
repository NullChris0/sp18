import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    public static class TestNormal {
        @Test
        public void testWordToDeque() {
            Deque<Character> d = palindrome.wordToDeque("persiflage");
            String actual = "";
            for (int i = 0; i < "persiflage".length(); i++) {
                actual += d.removeFirst();
            }
            assertEquals("persiflage", actual);
        }

        @Test
        public void testEmptyString() {
            assertTrue(palindrome.isPalindrome(""));
        }

        @Test
        public void testSingleCharacter() {
            assertTrue(palindrome.isPalindrome("a"));
        }

        @Test
        public void testPalindrome() {
            assertTrue(palindrome.isPalindrome("racecar"));
            assertTrue(palindrome.isPalindrome("madam"));
            assertTrue(palindrome.isPalindrome("abba"));
        }

        @Test
        public void testNonPalindrome() {
            assertFalse(palindrome.isPalindrome("hello"));
            assertFalse(palindrome.isPalindrome("world"));
        }

        @Test
        public void testPalindromeWithMixedCase() {
            assertFalse(palindrome.isPalindrome("RaceCar"));
        }

        @Test
        public void testPalindromeWithSpaces() {
            assertFalse(palindrome.isPalindrome("nurses run"));
        }
    }

    public static class TestOffByBone {
        @Test
        public void testSingleCharacter() {
            CharacterComparator offByOne = new OffByOne();
            assertTrue(palindrome.isPalindrome("a", offByOne));
        }

        @Test
        public void testEmptyString() {
            CharacterComparator offByOne = new OffByOne();
            assertTrue(palindrome.isPalindrome("", offByOne));
        }

        @Test
        public void testOddLengthPalindrome() {
            CharacterComparator offByOne = new OffByOne();
            assertTrue(palindrome.isPalindrome("flake", offByOne));
            assertTrue(palindrome.isPalindrome("abcb", offByOne));
        }

        @Test
        public void testEvenLengthPalindrome() {
            CharacterComparator offByOne = new OffByOne();
            assertTrue(palindrome.isPalindrome("abab", offByOne));
            assertFalse(palindrome.isPalindrome("bcdb", offByOne));
        }

        @Test
        public void testNonPalindrome() {
            CharacterComparator offByOne = new OffByOne();
            assertFalse(palindrome.isPalindrome("hello", offByOne));
            assertFalse(palindrome.isPalindrome("abcd", offByOne));
        }
    }
}
