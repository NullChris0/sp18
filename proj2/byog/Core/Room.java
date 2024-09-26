package byog.Core;
import byog.TileEngine.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A Room Class, includes room's width,height(floor+wall) and pos(wall).
 */
public class Room extends Pixel {
    private static final int[] PICK = {5, 7, 9};  // 房间大小种类
    private List<Position> cachedConnectors;

    public Room(TETile[][] world, Random RAND) {
        int WIDTH = world.length;
        int HEIGHT = world[0].length;
        // 随机奇数
        RandomUtils.shuffle(RAND, PICK);
        this.width = PICK[0];
        RandomUtils.shuffle(RAND, PICK);
        this.height = PICK[0];
        int startX = RAND.nextInt(WIDTH - this.width);
        int startY = RAND.nextInt(HEIGHT - this.height);
        this.corners = getCorners(new Position(startX, startY), this.width, this.height);
    }

    /**
     * A Room method that print itself to world, include FLOOR and WALL
     */
    public void add2World(TETile[][] world) {
        for (int i = 0; i < this.height; i++) {
            world[corners[2].x][corners[2].y + i] = Tileset.WALL;
            world[corners[2].x + width - 1][corners[2].y + i] = Tileset.WALL;
        }
        for (int i = 0; i < width; i++) {
            world[corners[2].x + i][corners[2].y] = Tileset.WALL;
            world[corners[2].x + i][corners[2].y + height - 1] = Tileset.WALL;
        }
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                world[corners[2].x + j][corners[2].y + i] = Tileset.FLOOR;
            }
        }
    }
    @Deprecated
    public void _add2World(TETile[][] world, String prompt) {
        // 避免生成过厚的房间墙壁（由于迷宫生成的特性），所以在迷宫准备完毕后才填充墙壁
        // 实际上，仅生成房间地板后生成迷宫，使得迷宫可以利用房间地板进行随机生成
        // 在实际填充房间墙壁后，就会产生破碎的迷宫（不需要由于房间隔断而多次调用迷宫生成）
        if (prompt.compareTo("Maze first") != 0) {
            for (int i = 0; i < this.height; i++) {
                world[corners[2].x][corners[2].y + i] = Tileset.WALL;
                world[corners[2].x + width - 1][corners[2].y + i] = Tileset.WALL;
            }
            for (int i = 0; i < width; i++) {
                world[corners[2].x + i][corners[2].y] = Tileset.WALL;
                world[corners[2].x + i][corners[2].y + height - 1] = Tileset.WALL;
            }
        }
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                world[corners[2].x + j][corners[2].y + i] = Tileset.FLOOR;
            }
        }
    }

    /**
     * A Room method that check itself to every existed rooms.
     * This method just compare to single one Room, need loop over all rooms.
     * @param r target room
     * @return self is overlap to r ?
     */
    public boolean isOverlap(Room r) {
        // 获取当前房间的边界
        int thisLeft = this.corners[2].x;
        int thisRight = this.corners[0].x;
        int thisBottom = this.corners[2].y;
        int thisTop = this.corners[0].y;

        // 获取目标房间的边界
        int otherLeft = r.corners[2].x;
        int otherRight = r.corners[0].x;
        int otherBottom = r.corners[2].y;
        int otherTop = r.corners[0].y;

        // 检查是否重叠，允许 1 Tile 的间隔
        boolean noOverlapX = thisRight < otherLeft - 1 || thisLeft > otherRight + 1;
        boolean noOverlapY = thisTop < otherBottom - 1 || thisBottom > otherTop + 1;

        // 如果在 X 或 Y 轴上不重叠，则没有碰撞
        return !(noOverlapX || noOverlapY);
    }

    /**
     * A Room method to get its edge positions, exclude corners and unconnected edges(pathCount != 2).
     * <p>New change: Use a private attr to represent Room's connectors.
     * @return List of Positions
     */
    public List<Position> getConnectors(TETile[][] world) {
        if (cachedConnectors == null) {
            cachedConnectors = new ArrayList<>();
            for (Position p: getWalls(corners[2])) {
                int pathCount = 0;
                for (Position pt : surroundings(p, 1)) {
                    if (isValid(pt, world) && world[pt.x][pt.y].equals(Tileset.FLOOR)) {
                        pathCount++;
                    }
                }
                if (pathCount == 2) {
                    cachedConnectors.add(p);
                }
            }
        }
        return cachedConnectors;
    }

    /**
     * Determine whether a coordinate is in a room
     * @return true if in this room
     */
    public boolean isContainPos(Position p) {
        return corners[2].x <= p.x && corners[1].x >= p.x
                && corners[2].y <= p.y && corners[3].y >= p.y;
    }

    /**
     * Let a Room's Connector position to be TileSet.FLOOR and remove it from cache.
     */
    public void useConnector(Position p, TETile[][] world) {
        world[p.x][p.y] = Tileset.FLOOR;
        if (cachedConnectors != null) {
            cachedConnectors.remove(p);
        }
    }

    private boolean isExtraWall(char direct, TETile[][] world) {
        Position o;
        switch (direct) {
            case 'W':
                o = new Position(this.corners[3].x, this.corners[3].y + 1);
                for (int i = 0; i < this.width; i++) {
                    if (world[o.x + i][o.y] != Tileset.WALL) {
                        return false;
                    }
//                    world[o.x + i][o.y] = Tileset.TREE;
                }
                break;
            case 'R':
                o = new Position(this.corners[0].x + 1, this.corners[0].y);
                for (int i = 0; i < this.height; i++) {
                    if (world[o.x][o.y - i] != Tileset.WALL) {
                        return false;
                    }
//                    world[o.x][o.y - i] = Tileset.TREE;
                }
                break;
            case 'S':
                o = new Position(this.corners[1].x, this.corners[1].y - 1);
                for (int i = 0; i < this.width; i++) {
                    if (world[o.x - i][o.y] != Tileset.WALL) {
                        return false;
                    }
//                    world[o.x - i][o.y] = Tileset.TREE;
                }
                break;
            case 'L':
                o = new Position(this.corners[2].x - 1, this.corners[2].y);
                for (int i = 0; i < this.height; i++) {
                    if (world[o.x][o.y + i] != Tileset.WALL) {
                        return false;
                    }
//                    world[o.x][o.y + i] = Tileset.TREE;
                }
                break;
        }
        return true;
    }

    private void removeExtraWall(char direct, TETile[][] world) {
        Position o;
        switch (direct) {
            case 'W':
                o = corners[3];
                for (int i = 0; i < this.width; i++) {
                    world[o.x + i][o.y] = Tileset.FLOOR;
                }
                break;
            case 'R':
                o = corners[0];
                for (int i = 0; i < this.height; i++) {
                    world[o.x][o.y - i] = Tileset.FLOOR;
                }
                break;
            case 'S':
                o = corners[1];
                for (int i = 0; i < this.width; i++) {
                    world[o.x - i][o.y] = Tileset.FLOOR;
                }
                break;
            case 'L':
                o = corners[2];
                for (int i = 0; i < this.height; i++) {
                    world[o.x][o.y + i] = Tileset.FLOOR;
                }
                break;
        }
    }

    public void removeExtraWalls(TETile[][] world) {
        if (isExtraWall('W', world)) {
            removeExtraWall('W', world);
            this.corners[3] = new Position(this.corners[3].x, this.corners[3].y + 1);
            this.corners[0] = new Position(this.corners[0].x, this.corners[0].y + 1);
            this.height++;
        }
        if (isExtraWall('R', world)) {
            removeExtraWall('R', world);
            this.corners[0] = new Position(this.corners[0].x + 1, this.corners[0].y);
            this.corners[1] = new Position(this.corners[1].x + 1, this.corners[1].y);
            this.width++;
        }
        if (isExtraWall('S', world)) {
            removeExtraWall('S', world);
            this.corners[1] = new Position(this.corners[1].x, this.corners[1].y - 1);
            this.corners[2] = new Position(this.corners[2].x, this.corners[2].y - 1);
            this.height++;
        }
        if (isExtraWall('L', world)) {
            removeExtraWall('L', world);
            this.corners[2] = new Position(this.corners[2].x - 1, this.corners[2].y);
            this.corners[3] = new Position(this.corners[3].x - 1, this.corners[3].y);
            this.width++;
        }
        this.add2World(world);
    }
}
