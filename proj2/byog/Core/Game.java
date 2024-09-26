package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * SOURCE:<p>
 * THIS PROJECT GET Idea FROM:<p>
 * <a href="https://blog.csdn.net/fourier_transformer/article/details/105340529">CSDN</a>
 * <a href="https://indienova.com/indie-game-development/rooms-and-mazes-a-procedural-dungeon-generator/#iah-5">INDIENOVA</a>
 */
public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 107;
    public static final int HEIGHT = 53;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
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
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        long seed = Long.parseLong(input.replaceAll("[^0-9]", ""));
        TETile[][] finalWorldFrame = initializeTiles("Autograder");
        return WorldCreator.generateWorld(new Random(seed), finalWorldFrame);
    }

    public TETile[][] initializeTiles(String prompt) {
        if (prompt.compareTo("Autograder") != 0) {
            ter.initialize(WIDTH, HEIGHT);
        }
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }
}
