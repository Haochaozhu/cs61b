package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Random;

import static byog.Core.Game.HEIGHT;
import static byog.Core.Game.WIDTH;

public class RoomPrinter {

    public static void draw(Room room, TETile[][] world) {
        int width = room.width;
        int height = room.height;
        int xPosition = room.p.x;
        int yPosition = room.p.y;

        for (int x = xPosition; x <xPosition + width; x += 1) {
            for (int y = yPosition; y < yPosition + height; y += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        }
    }

    public static void drawRoomOverlapsHallways(Room room, TETile[][] world) {

    }

    public static void buildWalls(TETile[][] world) {
        for (int x = 1; x < WIDTH - 1; x += 1) {
            for (int y = 1; y < HEIGHT - 1; y += 1) {
                Position current = new Position(x, y);
                if (world[x][y] == Tileset.FLOOR) {
                    for (Position p : current.getNeighbors()) {
                        if (world[p.x][p.y] == Tileset.NOTHING) {
                            world[p.x][p.y] = Tileset.WALL;
                        }
                    }
                }
            }
        }

    }

    public static void generateAHallWay(Room current, Room previous, TETile[][] world) {
        if (previous == null || current == null) return;
        Position p1 = current.central;
        Position p2 = previous.central;
        Position xStart = PositionUtils.smallerX(p1, p2);
        Position xDest = PositionUtils.biggerX(p1, p2);
        Position yStart = PositionUtils.smallerY(p1, p2);
        Position yDest = PositionUtils.biggerY(p1, p2);

        if (current.smaller(previous) || previous.smaller(current)) {
            dugHorizontalTunnel(xStart, xDest, world);
            dugVerticalTunnel(yStart, yDest, world);
        } else {
            dugHorizontalTunnel(xStart, xDest, world);
            dugVerticalTunnel2(yStart, yDest, world);
        }
    }

    private static void dugVerticalTunnel(Position start, Position dest, TETile[][] world) {
        for (int y = start.y; y < dest.y; y += 1) {
            if (world[dest.x][y] != Tileset.WALL) world[dest.x][y] = Tileset.FLOOR;
        }
    }

    private static void dugVerticalTunnel2(Position start, Position dest, TETile[][] world) {
        for (int y = start.y; y < dest.y; y += 1) {
            if (world[start.x][y] != Tileset.WALL) world[start.x][y] = Tileset.FLOOR;
        }
    }

    private static void dugHorizontalTunnel(Position start, Position dest, TETile[][] world) {
        for (int x = start.x; x <= dest.x; x += 1) {
            if (world[x][start.y] != Tileset.WALL) world[x][start.y] = Tileset.FLOOR;
        }
    }

    public static void drawHallWays(HallWay h, TETile[][] world) {
        for (Position p : h.pSet) {
            world[p.x][p.y] = Tileset.FLOOR;
        }
    }

    public static void main(String[] args) {

    }
}
