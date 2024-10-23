package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;

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
        char[] inputArray = input.toUpperCase().toCharArray();
        GameState interaction = new GameState();
        if (input.charAt(0) == 'L') {
            interaction = loadGame();
        }
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

    public static void saveGame(GameState g) {
        File f = new File("./worldSave.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(g);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    /**
     * Load a GameState instance from file and start the previous game.
     * @param ter Optional parameter if you want render.
     * @return A GameState instance if worldSave.ser is valid, otherwise return new instance.
     */
    public static GameState loadGame(TERenderer... ter) {
        File f = new File("./worldSave.ser");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                GameState g = (GameState) os.readObject();
                os.close();
                g.t = ter[0];
                g.processInputString('S');
                return g;
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
        return new GameState();
    }
}
