package byog.Core;
import byog.TileEngine.*;

import java.util.List;
import java.util.Random;

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
    public static TETile[][] generateWorld(Random RAND, TETile[][] world, TERenderer ter) {
        Maze.mazeGenerator(world, RAND);
        Maze.removeDeadEnds(Maze.findDeadEnds(world), world, 5);
        Room.roomGenerator(world, RAND);
        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[0].length; y++) {
                if (world[x][y].equals(Tileset.NOTHING)) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
        ter.renderFrame(world);
        return world;
    }
}
