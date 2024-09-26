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

    private static Position randomStart(Random RAND, TETile[][] world) {
        Position ret;
        while (true) {
            ret = new Position(RAND.nextInt(world.length), RAND.nextInt(world[0].length));
            if (world[ret.x][ret.y].equals(Tileset.NOTHING)) {
                return ret;
            }
        }
    }

    /** 根据间隔一格位置处的状态，返回可供延伸道路的坐标列表 */
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

    /**
     * Find Origin DeadEnds in generated maze world.
     * (DeadEnd is a FLOOR which has connected-FLOOR in only ONE direction)
     * @param world 2D Tile Array
     * @return List of Origin DeadEnds
     */
    public static List<Position> findDeadEnds(TETile[][] world) {
        List<Position> deadEnds = new ArrayList<>();
        for (int x = 1; x < world.length - 1; x++) {
            for (int y = 1; y < world[0].length - 1; y++) {
                if (isDeadEnd(new Position(x, y), world)) {
                    deadEnds.add(new Position(x, y));
                }
            }
        }
        return deadEnds;
    }

    /**  Determine whether a coordinate is a dead end */
    private static boolean isDeadEnd(Position input, TETile[][] world) {
        if (!world[input.x][input.y].equals(Tileset.FLOOR)) {
            return false;
        }
        int pathCount = 0;
        for (Position p: surroundings(input, 1)) {
            if (isValid(p, world) && world[p.x][p.y].equals(Tileset.FLOOR)) {
                pathCount++;
            }
        }
        return pathCount == 1; // If only one direction is a path, it is a dead end
    }

    /** (No Recursively) remove origin Deadends */
    public static void removeDeadEnds(TETile[][] world, int steps) {
        for (int i = 0; i < steps; i++) {
            for (Position p: findDeadEnds(world)) {
                world[p.x][p.y] = Tileset.NOTHING;
            }
        }
    }

    @Deprecated
    private static void _removeDeadEnds(Position pos, TETile[][] world, int steps) {
        if (steps <= 0) return;
        world[pos.x][pos.y] = Tileset.NOTHING;

        // 继续检查相邻的路径，如果它们变成死胡同则继续移除
        for (Position p: surroundings(pos, 1)) {
            if (isValid(p, world) && isDeadEnd(p, world)) {
                _removeDeadEnds(p, world, steps - 1);
            }
        }
    }
}