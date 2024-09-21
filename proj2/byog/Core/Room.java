package byog.Core;
import byog.TileEngine.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A Room Class, includes room's width,height(floor+wall) and pos(wall).
 */
public class Room extends Pixel {

    private static final int[] PICK = {5, 7, 9};
    private static final int BASE = 20 + new Random().nextInt(10);

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

    public static void roomGenerator(TETile[][] world, Random RAND) {
        List<Room> rooms = new ArrayList<>(BASE);
        while (rooms.size() < BASE && MAXTRIES-- != 0) {
            Room cand = new Room(world, RAND);
            boolean conflict = false;
            for (Room er : rooms) {
                if (cand.isOverlap(er)) {
                    conflict = true;
                    break;
                }
            }
            if (!conflict) {
                rooms.add(cand);
                cand.add2World(world);
            }
        }
    }

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

    public boolean isOverlap(Room r) {
        // 获取当前房间的四个边界
        int thisLeft = this.corners[2].x;
        int thisRight = this.corners[1].x;
        int thisBottom = this.corners[2].y;
        int thisTop = this.corners[3].y;

        // 获取比较房间的四个边界
        int rLeft = r.corners[2].x;
        int rRight = r.corners[1].x;
        int rBottom = r.corners[2].y;
        int rTop = r.corners[3].y;

        // 使用轴对齐边界框（AABB）方法检查是否重叠
        // 如果两个房间不重叠的条件是：一个房间完全在另一个房间的左边、右边、上边或下边
        // 所以只要不满足这些条件，就说明两个房间重叠
        return thisRight >= rLeft && thisLeft <= rRight && thisTop >= rBottom && thisBottom <= rTop;
    }
}
