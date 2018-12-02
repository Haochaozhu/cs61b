package byog.lab5;

import byog.Core.Position;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world that contains RANDOM tiles.
 */
public class RandomWorldDemo {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Fills the given 2D array of tiles with RANDOM tiles.
     * @param tiles
     */
    public static void fillWithRandomTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = randomTile();
            }
        }
    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            default: return Tileset.NOTHING;
        }
    }

//    Draw a hexagon with specified left lower position and size.
    public static void addHexagon(TETile[][] world, Position p, int size, TETile t) {
        Position upperPosition = calculateUpperPosition(p, size);
        drawUpperHalf(upperPosition, size, t, world);
        drawLowerHalf(p, size, t, world);
    }

    private static Position calculateUpperPosition(Position p, int size) {
        int upperX = p.x;
        int upperY = p.y + size * 2 - 1;
        return new Position(upperX, upperY);
    }

    private static void drawUpperHalf(Position p, int size, TETile t, TETile[][] world) {
        int increasedTiles = 0;
        for (int k = 0; k < size; k += 1) {
            for (int m = -increasedTiles; m < size + increasedTiles; m += 1) {
                int x = p.x + m;
                if (x < 0) x = 0;
                else if (x >= world.length) x = world.length - 1;

                int y = p.y - k;
                if (y < 0) y = 0;
                else if (y > world[0].length) y= world[0].length - 1;

                world[x][y] = t;
            }
            increasedTiles += 1;
        }
    }

    private static void drawLowerHalf(Position p, int size, TETile t, TETile[][] world) {
        int increasedTiles = 0;
        for (int k = 0; k < size; k += 1) {
            for (int m = -increasedTiles; m < size + increasedTiles; m += 1) {
                int x = p.x + m;
                if (x < 0) x= 0;
                else if (x >= world.length) x = world.length - 1;

                int y = p.y + k;
                if (y < 0) y = 0;
                else if (y > world[0].length) y = world[0].length - 1;

                world[x][y] = t;
            }
            increasedTiles += 1;
        }
    }

//    Draw a hexagon with specified position and size. Position indicates the left lower position of the whole tesselation.
    public static void drawTesselation(TETile[][] world, int size, Position p) {
        Position dummmy = p;
        Position dummy1 = p;
        drawTesselationUpwards(p, 5, size, world);
        int numbers = 4, numbers1 = 4;
        for (int i = 0; i < 2; i += 1) {
            dummmy = getLeftHexagon(dummmy, size);
            drawTesselationUpwards(dummmy, numbers, size, world);
            numbers -= 1;
        }

        for (int i = 0; i < 2; i+= 1) {
            dummy1 = getRightHexagon(dummy1, size);
            drawTesselationUpwards(dummy1, numbers1, size, world);
            numbers1 -= 1;
        }



    }

//    Draw hexagon on top of the previous one starting from the postion given.
    private static void drawTesselationUpwards(Position p, int numbers, int size, TETile[][] world) {
        for (int i = 0; i < numbers; i += 1) {
            TETile t = randomTile();
            addHexagon(world, p, size, t);
            p = getUpHexagon(p, size);
        }
    }



    private static Position getLeftHexagon(Position p, int size) {
        int x = p.x - (2 * size - 1);
        int y = p.y + size;
        return new Position(x, y);
    }

    private static Position getRightHexagon(Position p, int size) {
        int x = p.x + (2 * size - 1);
        int y = p.y + size;
        return new Position(x, y);
    }

    private static Position getUpHexagon(Position p, int size) {
        int x = p.x;
        int y = p.y + 2 * size;
        return new Position(x, y);
    }



    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Position p = new Position(25, 5);
        drawTesselation(world, 4, p);
        ter.renderFrame(world);
    }
}
