package byog.Core;
import byog.TileEngine.*;

import java.util.Random;

public abstract class Pixel {

    protected static int MAXTRIES = 100;
    protected static final int[][] DIRECTIONS = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} }; // 四个方向
    protected int width, height;
    protected Position[] corners = new Position[4];

    /** Return four corner points.
     * 3 . . . 0
     * . inner .
     * . inner .
     * . inner .
     * 2 . . . 1
     */
    protected static Position[] getCorners(Position o, int width, int height) {
        Position[] pArray = new Position[4];
        pArray[2] = new Position(o.x, o.y);
        pArray[1] = new Position(o.x + width - 1, o.y);
        pArray[0] = new Position(o.x + width - 1, o.y + height - 1);
        pArray[3] = new Position(o.x, o.y + height - 1);
        return pArray;
    }

    /** Return four orthogonally-adjacent positions.
     *      3
     *    0   2
     *      1
     */
    // For connectPath
    public static Position[] surroundings(Position p, int offset) {
        Position[] pArray = new Position[4];
        int i = 0;
        for (int[] direction : DIRECTIONS) {
            int newX = p.x + direction[0] * offset;
            int newY = p.y + direction[1] * offset;
            pArray[i++] = new Position(newX, newY);
        }
        return pArray;
    }

    // 判断位置是否有效且未被访问
    public static boolean isValid(Position p, TETile[][] world, TETile eq) {
        return p.x >= 0 && p.x < world.length
                && p.y >= 0 && p.y < world[0].length
                && world[p.x][p.y].equals(eq);
    }

    // 判断位置是否有效
    public static boolean isValid(Position p, TETile[][] world) {
        return p.x >= 0 && p.x < world.length
                && p.y >= 0 && p.y < world[0].length;
    }

    /** 3   2
     *    x
     *  0   1
     */
    public static Position[] aroundCornerPositions(Position p) {
        Position[] pArray = new Position[4];
        pArray[0] = new Position(p.x - 1, p.y - 1);
        pArray[1] = new Position(p.x + 1, p.y - 1);
        pArray[2] = new Position(p.x + 1, p.y + 1);
        pArray[3] = new Position(p.x - 1, p.y + 1);
        return pArray;
    }

    public static boolean isInDeadEnd(Position p, TETile[][] world) {
        int exits = 0;
        Position[] pArray = surroundings(p, 1);
        for (int i = 0; i < 4; i++) {
            if (world[pArray[i].x][pArray[i].y] == Tileset.FLOOR) {
                exits++;
            }
        }
        return exits == 1;
    }

    public static boolean isInnerWall(Position p, TETile[][] world) {
        Position[] pArray = aroundCornerPositions(p);
        for (int i = 0; i < 4; i++) {
            if (world[pArray[i].x][pArray[i].y] == Tileset.FLOOR) {
                return false;
            }
        }
        return true;
    }
}
