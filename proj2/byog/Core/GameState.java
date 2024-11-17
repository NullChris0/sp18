package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import static byog.Core.Game.Menu.drawSeedPrompt;


/**
 * The GameState Class used to control the Game's Behavior and Interactivity.
 * It should be Serializable as we want to save the previous Game.
 * The main idea of GameState is State Switch with enum type.
 */
public class GameState {
    transient TERenderer t;  // mark to use Gui
    transient TETile[][] world;
    transient int xMouse, yMouse;
    boolean mouseMoved = false;  // for mouse HUD
    private final StringBuilder lastPlay = new StringBuilder();  // for save game
    private Avatar thePlayer;
    private long seed = -2;  // placeholder before user input
    private State currentState;

    public Avatar getThePlayer() {
        return thePlayer;
    }

    private enum State {
        MENU,           // 主菜单
        SEED_ENTRY,    // 输入种子
        PLAYING,       // 游戏进行中
        SAVING,        // 已保存
        GAME_OVER      // 游戏结束标志
    }

    public GameState() {
        this.currentState = State.MENU;
    }

    public void useGuiMode(TERenderer ter) {
        this.t = ter;
        xMouse = (int) StdDraw.mouseX();
        yMouse = (int) StdDraw.mouseY();
    }

    public void cancelGuiMode() {
        this.t = null;
        xMouse = yMouse = 0;
    }
    public boolean isGameOver() {
        return currentState != State.GAME_OVER;
    }

    public void processInputString(char key) {
        switch (currentState) {
            case MENU:
                handleMenuInput(key);
                break;
            case SEED_ENTRY:
            case GAME_OVER:
                handleSeedInput(key);
                break;
            case PLAYING:
            case SAVING:
                handleGameInput(key);
                break;
            default:
        }
    }

    /**
     * Deal with actions while showing a menu.
     */
    private void handleMenuInput(char key) {
        switch (key) {
            case 'N':
                currentState = State.SEED_ENTRY;
                if (t != null) {
                    drawSeedPrompt(this);  // draw SeedMenu when using Gui mode, it will start game
                }
                break;
            case 'Q':
                break;
            default:  // for passing the autograder when inputString contains illegal Load behavior.
                currentState = State.GAME_OVER;
                world = Game.initializeTiles();
        }
    }

    /**
     * Deal with seed input and start generate the world.
     * Changed: different behavior between load game and new game.
     * Second Changed: return three state for Gui.(Notice that seed is initialized by 0)
     * @return <p>{@code seed}: temporary seed while dealing keyboard inputs
     * or illegal input pattern </p>
     *  <p>{@code -1}: the keyboard 'S' when finishing enter seed
     */
    long handleSeedInput(char key) {
        if (key == 'S') {  // when seed is complete, always re create world array
            world = Game.initializeTiles();
            WorldCreator.generateWorld(seed, world);
            if (currentState == State.SEED_ENTRY) {  // create new avatar for new game
                thePlayer = new Avatar(seed, world);
            } else {
                thePlayer.world = world;
            }
            currentState = State.PLAYING;
            return -1;
        } else if (Character.isDigit(key)) {
            seed = seed < 0 ? 0 : seed;
            seed = seed * 10 + (key - '0');
            return seed;
        }
        return seed;
    }

    /**
     * Deal with KeyBoard input with Player's actions.
     */
    private void handleGameInput(char key) {
        if (key != 'Q' && key != ':') {
            lastPlay.append(key);
        }
        switch (key) {
            case 'W':
                thePlayer.move(new int[]{0, 1});
                break;
            case 'S':
                thePlayer.move(new int[]{0, -1});
                break;
            case 'A':
                thePlayer.move(new int[]{-1, 0});
                break;
            case 'D':
                thePlayer.move(new int[]{1, 0});
                break;
            case ':':
                currentState = State.SAVING;  // 等待下一个输入是否为Q
                break;
            case 'Q':
                quiting();
                break;
            default:
        }
    }

    public void saveGame() {
        try {
            File outFile = new File("./World.txt");
            Writer out = new PrintWriter(outFile);
            out.write("N" + seed + "S" + lastPlay);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    /**
     * Quit the game.</p>If {@link State#SAVING}, then save the {@code GameState} instance.
     * The Game should start(again) with {@link State#GAME_OVER} or {@link State#SEED_ENTRY}
     */
    private void quiting() {
        if (currentState == State.SAVING) {
            currentState = State.GAME_OVER;
            saveGame();
        }
        currentState = State.GAME_OVER;
    }

    public void updateMouse() {
        int tempX = (int) StdDraw.mouseX();
        int tempY = (int) StdDraw.mouseY();
        if ((tempX == xMouse) && (tempY == yMouse)) {
            mouseMoved = false;
        } else {
            xMouse = tempX;
            yMouse = tempY;
            mouseMoved = true;
        }
    }
}
