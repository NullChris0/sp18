package byog.Core;
import byog.TileEngine.*;

import java.util.Random;

import static byog.Core.Pixel.*;

public class WorldCreator {
    private static final Random RANDOM = new Random();

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
    public static void generateWorld(long seed, TETile[][] world) {
        RANDOM.setSeed(seed);
        /*
         * 生成逻辑：先确定房间，再在其余位置填充迷宫。
         */
        RoomContainer rg = new RoomContainer();
        rg.setBase(RANDOM);
        rg.generateRooms(world, RANDOM);
        Maze.mazeGenerator(world, RANDOM);
        rg.niceRooms(world);
        rg.connectRooms(world, RANDOM);
        Maze.removeDeadEnds(world, 500);
//        addRoadWalls(world);
        fullOfWall(world);
        removeInnerWalls(world);
        easyDoor(world, RANDOM);
    }

    public static void addRoadWalls(TETile[][] world) {
        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[0].length; y++) {
                if (world[x][y].equals(Tileset.FLOOR)) {
                    for (Position p: surroundings(new Position(x, y), 1)) {
                        if (isValid(p, world, Tileset.NOTHING)) {
                            world[p.x][p.y] = Tileset.WALL;
                        }
                    }
                }
            }
        }
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
                world[j][i] = Tileset.NOTHING;
            }
        }
    }

    private static void easyDoor(TETile[][] world, Random RANDOM) {
        Position r = new Position(RANDOM.nextInt(world.length), RANDOM.nextInt(world[0].length));
        if (isValid(r, world, Tileset.WALL)) {
            world[r.x][r.y] = Tileset.LOCKED_DOOR;
        } else {
            easyDoor(world, RANDOM);
        }
    }

    public static void main(String[] args) {
        long seed = 23456758;
        Game tg = new Game();
        tg.ter.initialize(Game.WIDTH, Game.HEIGHT);
        TETile[][] w = Game.initializeTiles();
        generateWorld(seed, w);
        tg.ter.renderFrame(w);
    }
}
