package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.io.*;

/**
 * The GameState Class used to control the Game's Behavior and Interactivity.
 * It should be Serializable as we want to save the previous Game.
 * The main idea of GameState is State Switch with enum type.
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 114514114514114L;
    public transient TERenderer t;  // TERenderer if playing with keyboard
    transient TETile[][] world;
    transient int xMouse, yMouse;
    Avatar thePlayer;
    private long seed;
    private State currentState;
    private enum State {
        MENU,           // 主菜单
        SEED_ENTRY,    // 输入种子
        PLAYING,       // 游戏进行中
        SAVING,        // 已保存
        GAMEOVER
    }

    public GameState() {
        this.currentState = State.MENU;
    }

    public GameState(TERenderer ter) {
        t = ter;
        this.currentState = State.MENU;
        xMouse = (int) StdDraw.mouseX();
        yMouse = (int) StdDraw.mouseY();
    }

    public boolean isGameOver() {
        return currentState == State.GAMEOVER;
    }

    public void processInputString(char key) {
        switch (currentState) {
            case MENU:
                handleMenuInput(key);
                break;
            case SEED_ENTRY:
            case GAMEOVER:
                handleSeedInput(key);
                break;
            case PLAYING:
            case SAVING:
                handleGameInput(key);
                if (t != null) t.renderFrame(world);
                break;
        }
    }

    /**
     * Deal with actions while showing a menu.
     */
    private void handleMenuInput(char key) {
        switch (key) {
            case 'N':
                currentState = State.SEED_ENTRY;
//                displaySeedPrompt();
                break;
            case 'Q':
                break;
        }
    }

    /**
     * Deal with seed input and start generate the world.
     * Changed: different behavior between load game and new game.
     */
    private void handleSeedInput(char key) {
        if (key == 'S') {  // when seed is complete, always re create world array
            world = Game.initializeTiles();
            WorldCreator.generateWorld(seed, world);
            if (currentState == State.SEED_ENTRY) {  // create new avatar for new game
                thePlayer = new Avatar(seed, world);
            } else {
                thePlayer.world = world;
            }
            currentState = State.PLAYING;
            if (t != null) t.renderFrame(world);
        } else if (Character.isDigit(key)) {
            seed = seed * 10 + (key - '0');
//            updateSeedDisplay();
        }
    }

    /**
     * Deal with KeyBoard input with Player's actions.
     */
    private void handleGameInput(char key) {
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
        }
    }

    /**
     * Quit the game.If State.SAVING, then save the GameState instance.
     * The Game should start(again) with State.GAMEOVER or State.SEEDINPUT.
     */
    private void quiting() {
        if (currentState == State.SAVING) {
            currentState = State.GAMEOVER;
            Game.saveGame(this);
        }
        currentState = State.GAMEOVER;
    }
}
