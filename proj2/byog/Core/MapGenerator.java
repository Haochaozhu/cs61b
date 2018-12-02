package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdOut;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


import static byog.Core.Game.HEIGHT;
import static byog.Core.Game.WIDTH;

public class MapGenerator {
    Position startDoor;
    Position destDoor;
    Position player;
    TETile[][] world;

    public MapGenerator(long seed) {
        world = generateWorld(seed);
    }

    public TETile[][] generateWorld(long seed) {
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<HallWay> hallWays = new ArrayList<>();

        //Initialize the world.
        Random random = new Random(seed);
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }

        Room first = RoomFactory.generateARoom(random);
        Room second = RoomFactory.generateARoom(random);

        rooms.add(first);
        rooms.add(second);

        RoomPrinter.draw(first, finalWorldFrame);
        RoomPrinter.draw(second, finalWorldFrame);

        HallWay h = HallWayFactory.generateAHallWay(first, second);
        hallWays.add(h);
        RoomPrinter.drawHallWays(h, finalWorldFrame);

        Room previous = second;

        for (int i = 0; i < 20; i += 1) {
            boolean flag = false;

            Room current = RoomFactory.generateARoom(random);

            for (Room roomsInStack : rooms) {
                if (current.overlaps(roomsInStack)) flag = true;
            }

            if (!flag) {
                rooms.add(current);
                RoomPrinter.draw(current, finalWorldFrame);
                HallWay hw = HallWayFactory.generateAHallWay(current, previous);
                hallWays.add(hw);
                RoomPrinter.drawHallWays(hw, finalWorldFrame);
                previous = current;
            }
        }

        RoomPrinter.buildWalls(finalWorldFrame);
        startDoor = generateDoors(finalWorldFrame, random);
        destDoor = generateDestDoor(finalWorldFrame, random);
        player = generatePlayer(finalWorldFrame, startDoor);
        return finalWorldFrame;
    }

    private Position generateDestDoor(TETile[][] world, Random random) {
        boolean found = false;
        Position destDoor = new Position(RandomUtils.uniform(random, 0, WIDTH), RandomUtils.uniform(random, 0, HEIGHT));
        while (!found) {
            if (world[destDoor.x][destDoor.y] == Tileset.WALL && world[destDoor.x][destDoor.y] != Tileset.LOCKED_DOOR) {
                found = true;
                world[destDoor.x][destDoor.y] = Tileset.UNLOCKED_DOOR;
                continue;
            }
            destDoor = new Position(RandomUtils.uniform(random, 0, WIDTH), RandomUtils.uniform(random, 0, HEIGHT));
        }
        return destDoor;
    }

    private Position generateDoors(TETile[][] world, Random random) {
        boolean found = false;
        Position startDoor = new Position(RandomUtils.uniform(random, 0, WIDTH), RandomUtils.uniform(random, 0, HEIGHT));
        while (!found) {
            if (world[startDoor.x][startDoor.y] == Tileset.WALL) {
                found = true;
                world[startDoor.x][startDoor.y] = Tileset.LOCKED_DOOR;
//                if (world[startDoor.x - 1][startDoor.y] == Tileset.FLOOR) world[startDoor.x - 1][startDoor.y] = Tileset.PLAYER;
//                else if (world[startDoor.x + 1][startDoor.y] == Tileset.FLOOR) world[startDoor.x + 1][startDoor.y] = Tileset.PLAYER;
//                else if (world[startDoor.x][startDoor.y + 1] == Tileset.FLOOR) world[startDoor.x][startDoor.y + 1] = Tileset.PLAYER;
//                else if (world[startDoor.x][startDoor.y - 1] == Tileset.FLOOR) world[startDoor.x][startDoor.y - 1] = Tileset.PLAYER;
                continue;
            }
            startDoor = new Position(RandomUtils.uniform(random, 0, WIDTH), RandomUtils.uniform(random, 0, HEIGHT));
        }
        return startDoor;
    }

    private Position generatePlayer(TETile[][] world, Position startDoor) {
        if (world[startDoor.x - 1][startDoor.y] == Tileset.FLOOR) {
            world[startDoor.x - 1][startDoor.y] = Tileset.PLAYER;
            return new Position(startDoor.x - 1, startDoor.y);
        }
        else if (world[startDoor.x + 1][startDoor.y] == Tileset.FLOOR) {
            world[startDoor.x + 1][startDoor.y] = Tileset.PLAYER;
            return new Position(startDoor.x + 1, startDoor.y);
        }
        else if (world[startDoor.x][startDoor.y + 1] == Tileset.FLOOR) {
            world[startDoor.x][startDoor.y + 1] = Tileset.PLAYER;
            return new Position(startDoor.x, startDoor.y + 1);
        }
        else if (world[startDoor.x][startDoor.y - 1] == Tileset.FLOOR) {
            world[startDoor.x][startDoor.y - 1] = Tileset.PLAYER;
            return new Position(startDoor.x,startDoor.y - 1);
        }
        return null;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(50,20);
        TETile[][] t = new TETile[50][20];

        for (int x = 0; x < 50; x += 1) {
            for (int y = 0; y < 20; y += 1) {
                t[x][y] = Tileset.NOTHING;
            }
        }
        for (int x = 0; x < 50; x += 1) {
            t[x][10] = TETile.colorVariant(Tileset.WALL, 255, 255, 255, new Random());
        }
        ter.renderFrame(t);
    }
}
