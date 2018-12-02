package byog.Core;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SymbolDigraph;

import java.util.HashSet;
import java.util.Random;

public class Room {
    public HashSet<Position> positionSet;  //Positions of all wall tiles;
    public int width;
    public int height;
    public Position p;
    public Position central;

    public Room(HashSet<Position> positionSet, int width, int height, Position p) {
        this.positionSet = positionSet;
        this.width = width;
        this.height = height;
        this.p = p;
        central = new Position((p.x + width - 1 - p.x) / 2 + p.x, (p.y + height - 1 - p.y) / 2 + p.y);
    }


    public boolean overlaps(Room room) {
        if (room == null) return false;
        for (Position p : room.positionSet) {
            if (this.positionSet.contains(p)) return true;
        }

        return false;
    }

    public boolean overlaps(HallWay h) {
        for (Position p : h.pSet) {
            if (this.positionSet.contains(p)) return true;
        }
        return false;
    }

    //Returns true if both x and y are smaller than the given room.
    public boolean smaller(Room room) {
        return this.central.x < room.central.x && this.central.y < room.central.y;
    }

    //Check if the given position falls into the scope of the room
    public boolean contains(Position p) {
        return p.x > this.p.x && p.x < this.p.x + this.width - 1 && p.y > this.p.y && p.y < this.p.y + this.height - 1;
    }

    @Override
    public String toString() {
        return "Width: " + this.width + " Height: " + this.height + " X: " + this.p.x + " Y: " + this.p.y;
    }

    public static void main(String[] args) {
        Random random = new Random();
        Room r1 = RoomFactory.generateARoom(random);
        Room r2 = RoomFactory.generateARoom(random);
        StdOut.println(r1.toString());
        StdOut.println(r2.toString());
        StdOut.println(r1.smaller(r2));
    }
}
