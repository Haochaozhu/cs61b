package byog.Core;


import edu.princeton.cs.algs4.StdOut;

public class PositionUtils {

    //Compares x of two positions and returns the smaller one.
    public static Position smallerX(Position p1, Position p2) {
        if (p1.x < p2.x) return p1;
        else return p2;
    }

    public static Position biggerX(Position p1, Position p2) {
        if (p1.x > p2.x) return p1;
        else return p2;
    }

    public static Position smallerY(Position p1, Position p2) {
        if (p1.y < p2.y) return p1;
        else return p2;
    }

    public static Position biggerY(Position p1, Position p2) {
        if (p1.y > p2.y) return p1;
        else return p2;
    }

    public static void main(String[] args) {
        Position p1 = new Position(18, 20);
        Position p2 = new Position(35, 14);
        StdOut.println(smallerY(p1, p2));
        StdOut.println(biggerY(p1, p2));
    }
}
