package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
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
    GameState interaction = new GameState();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 101;
    public static final int HEIGHT = 51;

    /**
     * The Inner Menu class provides some static methods to play with Gui.
     */
    static class Menu {
        static final Font BIGFONT = new Font("Monaco", Font.BOLD, 45);
        static final Font SMALLFONT = new Font("Monaco", Font.BOLD, 30);
        public static final int MIDW = WIDTH / 4, MIDH = HEIGHT / 4;

        private static void startGui(Game g) {
            g.ter.initialize(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
            g.interaction.useGuiMode(g.ter);
        }

        /**
         * Draw the main Menu, deal with three types of User keyboard input.
         * @param g Game instance
         */
        private static void draw(Game g) {
            played:
            while (true) {  // menu loop to deal any type of user input
                StdDraw.clear();
                StdDraw.clear(Color.black);
                // Draw the actual text
                StdDraw.setFont(BIGFONT);
                StdDraw.setPenColor(Color.white);
                StdDraw.text(MIDW, MIDH + 4, "CS61B: The Game");
                StdDraw.setFont(SMALLFONT);
                StdDraw.text(MIDW, MIDH, "(N)EW GAME");
                StdDraw.text(MIDW, MIDH - 2, "(L)OAD GAME");
                StdDraw.text(MIDW, MIDH - 4, "(Q)UIT GAME");
                StdDraw.show();
                if (!StdDraw.hasNextKeyTyped()) {
                    continue;
                }
                char key = Character.toUpperCase(StdDraw.nextKeyTyped());
                switch (key) {
                    case 'N':
                        g.interaction.processInputString('N');  // main menu and seed input
                        // reload ter for world, specially for HUD
                        g.ter.initialize(WIDTH, HEIGHT + 3);
                        g.ter.renderFrame(g.interaction.world);  // render init world
                        drawPlaying(g);
                        break played;  // end program
                    case 'L':
                        g.interaction.cancelGuiMode();
                        g.playWithInputString("L");
                        g.interaction.useGuiMode(g.ter);
                        g.ter.initialize(WIDTH, HEIGHT + 3);
                        g.ter.renderFrame(g.interaction.world);  // render init world
                        drawPlaying(g);
                        break played;
                    case 'Q':
                        System.exit(0);
                        break;
                    default:
                }
                StdDraw.pause(100);
            }
        }

        /**
         * SeedPrompt Menu only called by {@link GameState#handleSeedInput(char)}
         * @param sg GameState instance to call {@code handleMenuInput(char)}
         */
        static void drawSeedPrompt(GameState sg) {
            String seedString = "";
            boolean atLeastOne = false;
            while (true) {
                StdDraw.clear();
                StdDraw.clear(Color.black);
                StdDraw.setPenColor(Color.white);
                StdDraw.text(MIDW, MIDH + 2, "ENTER THE SEED");
                StdDraw.text(MIDW, MIDH, "YOUR SEED: " + seedString);
                StdDraw.show();
                if (!StdDraw.hasNextKeyTyped()) {
                    continue;
                }
                char key = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (key == 'S' && !atLeastOne) {  // prevent from directly start
                    continue;
                }
                // if atLeastOne is true, there has been correctly entered a digit
                seedString = String.valueOf(sg.handleSeedInput(key));
                if (seedString.equals("-2") && !atLeastOne) {  // prevent from just a zero
                    seedString = "";
                } else {
                    atLeastOne = true;
                }
                // finish seed seedString and mark start
                if (seedString.equals("-1")) {
                    StdDraw.clear();
                    break;
                }
                StdDraw.pause(100);
            }
        }

        /**
         * Drawing and Playing with keyboard loop.
         * We changed {@link TERenderer#renderFrame(TETile[][], int...)} to support HUD.
         * @param g Game instance
         */
        private static void drawPlaying(Game g) {
            while (g.interaction.isGameOver()) {
                g.interaction.updateMouse();
                if (!StdDraw.hasNextKeyTyped()) {
                    if (g.interaction.mouseMoved) {
                        g.ter.renderFrame(g.interaction.world,
                                g.interaction.xMouse, g.interaction.yMouse);
                    }
                    continue;
                }
                char key = Character.toUpperCase(StdDraw.nextKeyTyped());
                g.interaction.processInputString(key);  // can listen to quit action and break
                StdDraw.pause(5);
                if (g.interaction.getThePlayer().hasWin) {
                    StdDraw.setFont(BIGFONT);
                    StdDraw.text(WIDTH / 2, HEIGHT / 2, "You win!");
                    StdDraw.show();
                    StdDraw.pause(10000);
                    break;
                }
                g.ter.renderFrame(g.interaction.world, g.interaction.xMouse, g.interaction.yMouse);
            }
        }
    }
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        Menu.startGui(this);
        Menu.draw(this);  // call the Main Menu drawing method
        System.exit(0);
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
        for (int i = 0; i < inputArray.length && interaction.isGameOver(); i++) {
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
