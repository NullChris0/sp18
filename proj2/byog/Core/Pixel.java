package byog.Core;
import byog.TileEngine.*;

public abstract class Pixel {

    protected static int MAXTRIES = 10000;  // 最大尝试生成次数
    protected static final int[][] DIRECTIONS = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
    protected int width, height;  // 用于房间类继承
    protected Position[] corners = new Position[4];

    /** Return four corner points, Using a origin point to cal.
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

    /**
     * Find a Room's WALL Positions.
     * @param p Room's origin point(down-left)
     * @return array of Positions
     */
    public Position[] getWalls(Position p) {
        Position[] pArray = new Position[2 * width + 2 * height - 8];
        int i = 0;
        for (int j = 1; j <= height - 2; j++) {
            pArray[i++] = new Position(p.x, p.y + j);
        }
        for (int j = 1; j <= width - 2; j++) {
            pArray[i++] = new Position(p.x + j, p.y + height - 1);
        }
        for (int j = 1; j <= height - 2; j++) {
            pArray[i++] = new Position(p.x + width - 1, p.y + j);
        }
        for (int j = 1; j <= width - 2; j++) {
            pArray[i++] = new Position(p.x + j, p.y);
        }
        return pArray;
    }

    /**
     * 判断位置是否合法并且等于某种瓷砖（Tile）
     * @param p 输入位置
     * @param eq 目标瓷砖类型
     * @return true 合法
     */
    public static boolean isValid(Position p, TETile[][] world, TETile eq) {
        return p.x >= 0 && p.x < world.length
                && p.y >= 0 && p.y < world[0].length
                && world[p.x][p.y].equals(eq);
    }

    /** 判断位置是否合法 */
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
