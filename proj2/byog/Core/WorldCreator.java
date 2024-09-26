package byog.Core;
import byog.TileEngine.*;

import java.util.Random;

import static byog.Core.Pixel.isInnerWall;

public class WorldCreator {

    /**
     * The tile rendering engine we provide takes in a 2D array of TETile objects
     * and draws it to the screen. Let’s call this TETile[][] world for now.
     * world[0][0] corresponds to the bottom left tile of the world.
     * The first coordinate is the x coordinate,
     * e.g. world[9][0] refers to the tile 9 spaces over to the right from the bottom left tile.
     * The second coordinate is the y coordinate, and the value increases as we move upwards,
     * e.g. world[0][5] is 5 tiles up from the bottom left tile.
     * All values should be non-null, i.e. make sure to fill them all in before calling renderFrame.
     */
    public static TETile[][] generateWorld(Random RAND, TETile[][] world) {
        /*
         * 生成逻辑：先确定房间，再在其余位置填充迷宫。
         */
        RoomContainer rg = new RoomContainer();
        rg.generateRooms(world, RAND);
        Maze.mazeGenerator(world, RAND);
        fullOfWall(world);
        rg.niceRooms(world);
        rg.connectRooms(world, RAND);
        Maze.removeDeadEnds(world, 0);
        fullOfWall(world);
        removeInnerWalls(world);
        return world;
    }

    public static void fullOfWall(TETile[][] world) {
        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[0].length; y++) {
                if (world[x][y].equals(Tileset.NOTHING)) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

    /** Remove all the inner walls.
     *  When all four corner positions of a wall aren't floor
     *  it's called a inner wall.
     */
    private static void removeInnerWalls(TETile[][] world) {
        for (int i = 1; i < world[0].length - 1; i++) {
            for (int j = 1; j < world.length - 1; j++) {
                if (world[j][i] != Tileset.WALL) {
                    continue;
                }
                if (!isInnerWall(new Position(j, i), world)) {
                    continue;
                }
                world[j][i] = Tileset.MOUNTAIN;
            }
        }
    }

    public static void main(String[] args) {
        int seed = 23456758;
        Game tg = new Game();
        TETile[][] w = tg.initializeTiles("Render");
        generateWorld(new Random(seed), w);
        tg.ter.renderFrame(w);
    }
}
