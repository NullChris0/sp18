package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;

public class Maze extends Pixel {

    private static final Deque<Position> mazeList = new LinkedList<>(); // 用于存储当前路径

    public static void mazeGenerator(TETile[][] world, Random RAND) {
        Position start = randomStart(RAND, world); // 随机起点
        mazeList.addLast(start);
        world[start.x][start.y] = Tileset.FLOOR; // 标记起点为路径

        while (!mazeList.isEmpty() && MAXTRIES-- != 0) {
            Position current = mazeList.getLast(); // 获取当前路径中的最后一个点
            List<Position> neighbors = getAccessible(current, world); // 获取未访问的邻居

            if (!neighbors.isEmpty()) {
                Position next = neighbors.get(RAND.nextInt(neighbors.size())); // 随机选择一个未访问的邻居
                // 打通当前单元格与选中的邻居之间的墙
                Position mid = current.middlePosition(next);
                world[mid.x][mid.y] = Tileset.FLOOR;
                world[next.x][next.y] = Tileset.FLOOR;
                mazeList.addLast(next); // 将新单元格加入路径
            } else {
                mazeList.removeLast(); // 如果没有未访问的邻居，则回溯
            }
        }
    }

    // 随机选择一个起点，起点必须是尚未访问过的单元格
    private static Position randomStart(Random RAND, TETile[][] world) {
        Position ret;
        while (true) {
            ret = new Position(RAND.nextInt(world.length), RAND.nextInt(world[0].length));
            if (world[ret.x][ret.y].equals(Tileset.NOTHING)) {
                return ret;
            }
        }
    }

    // 获取当前单元格周围未访问的邻居
    private static List<Position> getAccessible(Position current, TETile[][] world) {
        List<Position> neighbors = new ArrayList<>();
        for (Position p: surroundings(current, 2)) {
            // 检查新坐标是否在迷宫范围内，且该单元格尚未访问
            if (isValid(p, world, Tileset.NOTHING)) {
                neighbors.add(p);
            }
        }
        return neighbors;
    }

    // 找到所有的死胡同
    public static List<Position> findDeadEnds(TETile[][] world) {
        List<Position> deadEnds = new ArrayList<>();
        for (int x = 1; x < world.length - 1; x++) {
            for (int y = 1; y < world[0].length - 1; y++) {
                if (world[x][y].equals(Tileset.FLOOR) && isDeadEnd(new Position(x, y), world)) {
                    deadEnds.add(new Position(x, y));
                }
            }
        }
        return deadEnds;
    }

    // 判断某个位置是否是死胡同
    private static boolean isDeadEnd(Position input, TETile[][] world) {
        int pathCount = 0;
        for (Position p: surroundings(input, 1)) {
            if (isValid(p, world) && world[p.x][p.y].equals(Tileset.FLOOR)) {
                pathCount++;
            }
        }
        return pathCount == 1; // 如果只有一个方向是路径，则为死胡同
    }

    // 递归移除死胡同，步数限制
    public static void removeDeadEnds(List<Position> deadEnds, TETile[][] world, int steps) {
        for (Position pos : deadEnds) {
            removeDeadEnd(pos, world, steps);
        }
    }

    // 递归移除某个死胡同
    private static void removeDeadEnd(Position pos, TETile[][] world, int steps) {
        if (steps <= 0) return; // 达到步数限制，停止
        world[pos.x][pos.y] = Tileset.NOTHING;

        // 继续检查相邻的路径，如果它们变成死胡同则继续移除
        for (Position p: surroundings(pos, 1)) {
            if (isValid(p, world) && isDeadEnd(p, world)) {
                removeDeadEnd(p, world, steps - 1); // 递归移除相邻的死胡同
            }
        }
    }
}