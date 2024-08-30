public class OffByOne implements CharacterComparator {
    /**
     * Check characters' ASCII value.
     * @return true for characters that are different by exactly one
     */
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }
}
