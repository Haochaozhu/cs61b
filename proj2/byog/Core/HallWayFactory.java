package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashSet;

public class HallWayFactory {

    public static HallWay generateAHallWay(Room current, Room previous) {
        HashSet<Position> p = new HashSet<>();
        if (previous == null || current == null) return null;
        Position p1 = current.central;
        Position p2 = previous.central;
        Position xStart = PositionUtils.smallerX(p1, p2);
        Position xDest = PositionUtils.biggerX(p1, p2);
        Position yStart = PositionUtils.smallerY(p1, p2);
        Position yDest = PositionUtils.biggerY(p1, p2);

        if (current.smaller(previous) || previous.smaller(current)) {
            dugHorizontalTunnel(xStart, xDest, p);
            dugVerticalTunnel(yStart, yDest, p);
        } else {
            dugHorizontalTunnel(xStart, xDest, p);
            dugVerticalTunnel2(yStart, yDest, p);
        }

        return new HallWay(p);
    }

    private static void dugVerticalTunnel(Position start, Position dest, HashSet<Position> p) {
        for (int y = start.y; y < dest.y; y += 1) {
            p.add(new Position(dest.x, y));
        }
    }

    private static void dugVerticalTunnel2(Position start, Position dest, HashSet<Position> p) {
        for (int y = start.y; y < dest.y; y += 1) {
            p.add(new Position(start.x, y));
        }
    }

    private static void dugHorizontalTunnel(Position start, Position dest, HashSet<Position> p) {
        for (int x = start.x; x <= dest.x; x += 1) {
            p.add(new Position(x, start.y));
        }
    }
}
