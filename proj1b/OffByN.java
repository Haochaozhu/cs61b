public class OffByN {

    int difference = 0;

    OffByN(int n) {
        difference = n;
    }

    public boolean equalChars(char x, char y) {
        return Math.abs((x - y)) == difference;
    }
}
