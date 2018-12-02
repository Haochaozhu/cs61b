package byog.Core;


import byog.TileEngine.TETile;
import edu.princeton.cs.algs4.StdOut;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

import static byog.Core.Game.HEIGHT;

public class Position {
     public int x;
     public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Iterable<Position> getNeighbors() {
        HashSet<Position> neighbs = new HashSet<>();
        Position up = new Position(this.x, this.y + 1);
        Position down = new Position(this.x, this.y - 1);
        Position left = new Position(this.x - 1, this.y);
        Position right = new Position(this.x + 1, this.y);
        Position upLeft = new Position(this.x - 1, this.y + 1);
        Position downLeft = new Position(this.x - 1, this.y - 1);
        Position upRight = new Position(this.x + 1, this.y + 1);
        Position downRight = new Position(this.x + 1, this.y - 1);

        neighbs.add(up);
        neighbs.add(down);
        neighbs.add(left);
        neighbs.add(right);
        neighbs.add(upLeft);
        neighbs.add(downLeft);
        neighbs.add(upRight);
        neighbs.add(downRight);

        return neighbs;
    }

    public void move(char key) {
        if (key == 'a') {
            this.x = this.x - 1;
        }
        if (key == 'w') {
            this.y = this.y + 1;
        }
        if (key == 's') {
            this.y = this.y - 1;
        }
        if (key == 'd') {
            this.x = this.x + 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Position p1 = (Position) o;
        return this.x == p1.x && this.y == p1.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "X: " + this.x + " Y: " + this.y;
    }

    public static void main(String[] args) {
        Position p1 = new Position(5, 6);
        StdOut.println(p1.toString());
        p1.move('a');
        StdOut.println(p1.toString());
    }
}
