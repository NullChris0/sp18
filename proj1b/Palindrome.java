public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    public boolean isPalindrome(String word) {
        return palindromeHelper(wordToDeque(word));
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        return palindromeHelper(wordToDeque(word), cc);
    }
    
    private boolean palindromeHelper(Deque<Character> d) {
        if (d.isEmpty() || d.size() == 1) {
            return true;
        }
        return d.removeFirst() == d.removeLast() && palindromeHelper(d);
    }

    private boolean palindromeHelper(Deque<Character> d, CharacterComparator cc) {
        if (d.isEmpty() || d.size() == 1) {
            return true;
        }
        return cc.equalChars(d.removeFirst(), d.removeLast()) && palindromeHelper(d, cc);
    }
}
