package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

import java.awt.*;
import java.io.*;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public long seed;
    public TETile[][] savedGame;
    public TETile[][] world;
    public Position player;
    public Position mouse;
    public Position destDoor;
    public boolean playing;
    public MapGenerator mg;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        enterMainMenu();

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }

            char key = StdDraw.nextKeyTyped();
            if (key == 'n') {
                seed = enterSeedMenu();
                displayTransition(seed);
                mg = new MapGenerator(seed);
                world = mg.generateWorld(seed);
                player = mg.player;
                destDoor = mg.destDoor;
                playing = true;
                drawFrame(world);
                mouse = new Position((int)StdDraw.mouseX(), (int)StdDraw.mouseY());
                gamePlay();
                displayEndMenu();
            }

            if (key == 'l') {
                Font font = new Font("Monaco", Font.BOLD, 14);
                StdDraw.setFont(font);
                world = loadWorld();
                playing = true;
                player = findPlayer(world);
                destDoor = findDestDoor(world);
                mouse = new Position((int)StdDraw.mouseX(), (int)StdDraw.mouseY());
                StdDraw.clear(Color.BLACK);
                StdDraw.textLeft(WIDTH / 2.0 - 15, HEIGHT / 2.0, "Save Loaded, initializing world");
                StdDraw.show();
                StdDraw.pause(1000);
                drawFrame(world);
                gamePlay();
                displayEndMenu();
            }

            if (key == 'q') {
                System.exit(0);
            }
        }
    }

    private Position findDestDoor(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (world[x][y].equals(Tileset.UNLOCKED_DOOR)) return new Position(x, y);
            }
        }
        return null;
    }

    private Position findPlayer(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (world[x][y].equals(Tileset.PLAYER)) return new Position(x, y);
            }
        }
        return null;
    }

    private TETile[][] loadWorld() {
        File f = new File("./world.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                TETile[][] world = (TETile[][]) os.readObject();
                os.close();
                return world;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        /* In the case no World has been saved yet, we return a new one. */
        return null;
    }

    private void saveGame(TETile[][] world) {
        File f = new File("./world.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(world);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private void displayEndMenu() {
        StdDraw.clear(Color.BLACK);
        Font biggerFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(biggerFont);
        StdDraw.text(WIDTH/2.0, HEIGHT/2.0, "Congratulations! You have found the exit!");
        StdDraw.show();
        StdDraw.pause(2000);
        System.exit(0);
    }

    private boolean isMouseMoved() {
        Position current = new Position((int)StdDraw.mouseX(), (int)StdDraw.mouseY());
        boolean res =  mouse.equals(current);
        mouse = current;
        return res;
    }

    private void gamePlay() {
        char lastKey = 'a';
        while (playing) {
            if (!isMouseMoved()) drawFrame(world);
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if (lastKey == ':' && key == 'q') {
                saveGame(world);
                StdDraw.clear(Color.BLACK);
                StdDraw.textLeft(WIDTH / 2.0 - 15, HEIGHT / 2.0, "Saving completed. Game will be closed in 2s");
                StdDraw.show();
                StdDraw.pause(1000);
                StdDraw.clear(Color.BLACK);
                StdDraw.textLeft(WIDTH / 2.0 - 15, HEIGHT / 2.0, "Saving completed. Game will be closed in 1s");
                StdDraw.show();
                StdDraw.pause(1000);
                System.exit(0);
            }
            movePlayer(key);
            lastKey = key;
        }
    }

    private void drawFrame(TETile[][] world) {
        ter.renderFrame(world);
        StdDraw.setPenColor(Color.WHITE);
        String text = world[(int)StdDraw.mouseX()][(int)StdDraw.mouseY()].description();
        StdDraw.textLeft(1, HEIGHT - 1, text);
        StdDraw.show();
    }

    private void movePlayer(char key) {
        if (!detectWalls(key)) {
            world[player.x][player.y] = Tileset.FLOOR;
            player.move(key);
            world[player.x][player.y] = Tileset.PLAYER;
            if (player.equals(destDoor)) playing = false;
            drawFrame(world);
            StdDraw.show();
        }
    }

    private boolean detectWalls(char key) {
        Position p = new Position(player.x, player.y);
        p.move(key);
        return world[p.x][p.y].equals(Tileset.WALL) || world[p.x][p.y].equals(Tileset.LOCKED_DOOR);
    }

    private void enterMainMenu() {
        Font biggerFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(biggerFont);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 10, "CS61B : Project 2 The Game");
        Font smallerFont = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(smallerFont);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 10 - 5, "New Game (N)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 10 - 10, "Load Game (L)");
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 + 10 - 15, "Quit (Q)");
        StdDraw.show();
    }

    private void displaySeedMenu(String input) {
        StdDraw.clear(Color.BLACK);
        StdDraw.textLeft(WIDTH / 2.0 - 15, HEIGHT / 2.0, "Please enter a seed: " + input);
        StdDraw.show();
    }

    private void displayTransition(long seed) {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, "Generating game world with seed " + seed);
        StdDraw.show();
        StdDraw.pause(2000);
        Font font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);
    }

    private long enterSeedMenu() {
        displaySeedMenu("");
        boolean finished = false;
        String input = "";

        while (!finished) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if (key == 's') {
                finished = true;
                continue;
            }
            input += String.valueOf(key);
            displaySeedMenu(input.replaceAll("[^0-9]", ""));
        }
        if (input.replaceAll("[^0-9]", "").length() == 0) return 0L;
        else return Long.valueOf(input.replaceAll("[^0-9]", ""));
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        if (input.equals("l") && savedGame != null) return savedGame;  // If the input is "l",load the saved game.
        long seed = getSeed(input);
        MapGenerator mg  = new MapGenerator(seed);
        TETile[][] initialWorld = mg.generateWorld(seed);
        return initialWorld;
    }

    public long getSeed(String input) {
        int sIndex = input.indexOf('s');
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < sIndex; i += 1) {
            sb.append(input.charAt(i));
        }
        return Long.valueOf(sb.toString());
    }

    public static void main(String[] args) {
        MapGenerator mg = new MapGenerator(123);
        TETile[][] t = mg.generateWorld(123);
        StdOut.println(mg.player.toString());

    }
}
