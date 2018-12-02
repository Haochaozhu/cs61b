package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Random;

import static byog.Core.Game.HEIGHT;
import static byog.Core.Game.WIDTH;

// Includes static methods that generates random sized room with walls on a random position in the given world.
// Width and height include walls.
public class RoomFactory {

    public static Room generateARoom(Random random) {
        int width = RandomUtils.uniform(random, 4, 8);    // Generates a random position and size.
        int height = RandomUtils.uniform(random, 4,8);
        int xPosition = RandomUtils.uniform(random, 1, WIDTH - 7);
        int yPosition = RandomUtils.uniform(random, 1, HEIGHT - 7);
        Position p = new Position(xPosition, yPosition);

        HashSet<Position> pSet = new HashSet<>();

        for (int x = xPosition; x < xPosition + width; x += 1) {
            pSet.add(new Position(x, yPosition));
        }

        for (int x = xPosition; x < xPosition + width; x += 1) {
            int y = yPosition + height - 1 > HEIGHT - 1 ? HEIGHT - 1 : yPosition + height - 1;
            pSet.add(new Position(x, y));
        }

        for (int y = yPosition + 1; y < yPosition + height - 1; y += 1) {
            if (y == HEIGHT) y = HEIGHT - 1;
            pSet.add(new Position(xPosition, y));
        }

        for (int y = yPosition + 1; y < yPosition + height - 1; y += 1) {
            if (y == HEIGHT) y = HEIGHT - 1;
            int x = xPosition + width - 1 > WIDTH - 1 ? WIDTH - 1 : xPosition + width - 1;
            pSet.add(new Position(x, y));
        }

        return new Room(pSet, width, height, p);
    }

    public static void main(String[] args) {
        TETile[][] t = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                t[x][y] = Tileset.NOTHING;
            }
        }
        Random random = new Random();
        Room r1 = RoomFactory.generateARoom(random);
        Room r2 = RoomFactory.generateARoom(random);
        RoomPrinter.draw(r1, t);
        RoomPrinter.draw(r2, t);

//        RoomPrinter.generateAHallWay(r1, r2, t);

//        RoomFactory.dugHorizontalTunnel(r2.central, r1.central, t);
//        RoomFactory.dugVerticalTunnel(r2.central, r1.central,t);
        RoomPrinter.buildWalls(t);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(t);
    }
}









