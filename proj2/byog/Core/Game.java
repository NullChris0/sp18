package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * SOURCE:<p>
 * THIS PROJECT GET Idea FROM:<p>
 * @see
 * <a href="https://blog.csdn.net/fourier_transformer/article/details/105340529">CSDN</a>
 * <a href=
 * "https://indienova.com/indie-game-development/rooms-and-mazes-a-procedural-dungeon-generator/"
 * >INDIENOVA</a>
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
        char[] inputArray = loadGame(input.toUpperCase());
        GameState interaction = new GameState();
        for (int i = 0; i < inputArray.length && !interaction.isGameOver(); i++) {
            interaction.processInputString(inputArray[i]);
        }
        return interaction.world;
    }

    public static TETile[][] initializeTiles() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    /**
     * Load the game by reading a previously saved String from a text file.
     * In the case that a player attempts to load a game with no previous save,
     * the game ends and the game window closes with no errors produced.
     * @param input The input String this time provide.
     * @return A completed character array which loaded contents.
     */
    public char[] loadGame(String input) {
        if (input.charAt(0) == 'L') {
            try {
                Scanner in = new Scanner(new File("./World.txt"));
                while (in.hasNextLine()) {
                    String lastPlay = in.nextLine();
                    input = lastPlay + input.substring(1);
                }
            } catch (FileNotFoundException e) {
                e.getStackTrace();
            }
        }
        return input.toUpperCase().toCharArray();
    }
}
