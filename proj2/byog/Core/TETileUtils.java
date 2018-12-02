package byog.Core;

import byog.TileEngine.TETile;

import java.util.*;

public class TETileUtils {

    public static ArrayList<TETile> getNeighbors(Position p, TETile[][] world) {
        List<TETile> neighbs = new ArrayList<>();

        Position up = new Position(p.x, p.y + 1);
        Position down = new Position(p.x, p.y - 1);
        Position left = new Position(p.x - 1, p.y);
        Position right = new Position(p.x + 1, p.y);
        Position upLeft = new Position(p.x - 1, p.y + 1);
        Position downLeft = new Position(p.x - 1, p.y - 1);
        Position upRight = new Position(p.x + 1, p.y + 1);
        Position downRight = new Position(p.x + 1, p.y - 1);

        return null;
    }
}
