package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    public static class Position {
        int x, y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public Position() {
            this(0, 0);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Position other = (Position) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public String toString() {
            return "Position [x=" + x + ", y=" + y + "]";
        }
    }

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        for (Position p : getPos(new Position(25, 25), 3, 3)) {
            addHexagon(tiles, p, 3, randomTile());
        }
        ter.renderFrame(tiles);
    }

    public static TETile randomTile() {
        int tileNum = RANDOM.nextInt(10);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.TREE;
            case 2: return Tileset.FLOWER;
            case 3: return Tileset.GRASS;
            case 4: return Tileset.PLAYER;
            case 5: return Tileset.LOCKED_DOOR;
            case 6: return Tileset.UNLOCKED_DOOR;
            case 7: return Tileset.SAND;
            case 8: return Tileset.MOUNTAIN;
            case 9: return Tileset.FLOOR;
            default: return Tileset.SAND;
        }
    }

    /**
     * calculate number of small hexagons in the land
     * @param size the radius of hexagon land
     * @return how many small hexagons
     */
    public static int getSizeNum(int size) {
        if (size == 0) return 1;
        else return 6 * size + getSizeNum(size - 1);
    }

    /**
     * Get the origin coordinate for each part of the hexagon land.
     * @param origin The center of hexagon land.
     * @param radius The radius of hexagon land.
     * @param size Each part of small hexagon's size
     * @return List of points.
     */
    public static List<Position> getPos(Position origin, int radius, int size) {
        List<Position> ret = new ArrayList<>(getSizeNum(radius));
        Set<Position>[] pos = new HashSet[radius + 1];
        for (int i = 0; i <= radius; i++) {
            pos[i] = new HashSet<>();
        }
        pos[0].add(origin);

        for (int i = 1; i <= radius; i++) {
            Set<Position> curOrigin = pos[i - 1];
            for (Position t : curOrigin) {
                Position p1 = new Position(t.x, t.y + 2 * size);
                Position p2 = new Position(t.x + 2 * size - 1, t.y + size);
                Position p3 = new Position(t.x + 2 * size - 1, t.y - size);
                Position p4 = new Position(t.x, t.y - 2 * size);
                Position p6 = new Position(t.x - 2 * size + 1, t.y + size);
                Position p5 = new Position(t.x - 2 * size + 1, t.y - size);
                pos[i].addAll(new ArrayList<Position>(Arrays.asList(p1, p2, p3, p4, p5, p6)));
            }
        }
        for (Set<Position> set : pos) {
            ret.addAll(set);
        }
        return ret;
    }

    /**
      Print a single hexagon in terminal for test.
     */
    public static void addHexagon(int size) {
        for (int yi = 2 * size - 1; yi >=0; yi -= 1) {
            int xOffset = yi >= size ?
                    Math.abs(hexRowOffset(size, yi- size)) :
                    Math.abs(hexRowOffset(size, yi + size));
            for (int x = 0; x < xOffset; x += 1) {
                System.out.print(' ');
            }
            int rowWidth = hexRowWidth(size, yi);
            for (int xi = 0; xi < rowWidth; xi += 1) {
                System.out.print('a');
            }
            System.out.println();
        }
    }

    /**
     * Computes the width of row i for a size s hexagon.
     * @param size The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     */
    public static int hexRowWidth(int size, int i) {
        int effectiveI = i;
        if (i >= size) {  // means here is top-half part of hexagon
            effectiveI = 2 * size - 1 - effectiveI;
        }

        return size + 2 * effectiveI;
    }

    /**
     * Computes relative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position, e.g.
     *   xxxx
     *  xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
     *  xxxxxx
     *   yxxx <-- y is origin of coordinate system
     *
     * @param size size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     */
    public static int hexRowOffset(int size, int i) {
        int effectiveI = i;
        if (i >= size) {
            effectiveI = 2 * size - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /** Adds a row of the same tile.
     * @param world the world to draw on
     * @param p the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t the tile to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param p the bottom left coordinate of the hexagon
     * @param s the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {

        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        // hexagons have 2*s rows. this code iterates up from the bottom row,
        // which we call row 0.
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;

            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, yi);

            addRow(world, rowStartP, rowWidth, t);

        }
    }

}
