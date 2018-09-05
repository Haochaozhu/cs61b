public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        int difference = x - y;
        return (Math.abs(difference) == 1);
    }

}
