package byog.Core;

import byog.TileEngine.TETile;

import java.util.*;

public class RoomContainer {
    private static final int BASE = 10 + new Random().nextInt(10);  // 生成房间数量
    private static int MAXTRIES = Pixel.MAXTRIES;  // 最大尝试生成次数
    public List<Room> rooms;

    public void generateRooms(TETile[][] world, Random RAND) {
        rooms = new ArrayList<>(BASE);
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

    /**
     * Connect rooms with maze roads.We add all rooms' connectors at first,
     * when we're done to connect a Room, we remove its candidate connectors.
     * Once there are remained connectors, repeat procedure above.
     */
    public void connectRooms(TETile[][] world, Random RAND) {
        List<Position> mainPositions = new ArrayList<>();
        for (Room room : rooms) {
            mainPositions.addAll(room.getConnectors(world));
        }
        while (!mainPositions.isEmpty()) {
            Position randP = mainPositions.get(RAND.nextInt(mainPositions.size()));
            Room tar = connector2Room(randP, rooms);
            tar.useConnector(randP, world);
            mainPositions.remove(randP);
            mainPositions.removeAll(randomRetain(tar, world, RAND, 0.7));
        }
    }

    /**
     * 当连通一个房间的某个connector后，删除此房间其余未使用的connectors，但是以一定概率保留
     * @param p 保留概率，越小越容易使得一个房间被多次打通
     * @return 给连接算法提供的将要在一轮中删除的connectors
     */
    public List<Position> randomRetain(Room r, TETile[][] world, Random RAND, double p) {
        List<Position> retained = new ArrayList<>();
        for (Position connector : r.getConnectors(world)) {
            if (RAND.nextDouble() < p) { // 20% 几率保留
                retained.add(connector);
            }
        }
        return retained;
    }

    /**
     * 给定需要打通的连接点坐标（可能连接了房间与迷宫或是房间与房间），判断这个连接点属于哪一个房间
     * 注：当两个房间间隔1Tile厚度时，可能会产生问题
     * @return A Room instance in List<Room>
     */
    private Room connector2Room(Position connector, List<Room> listOfRooms) throws RuntimeException {
        for (Room room : listOfRooms) {
            if (room.isContainPos(connector)) {
                return room;
            }
        }
        throw new RuntimeException("No room found for connector " + connector);
    }

    /**
     * Public interface to remove double-layers rooms.
     */
    public void niceRooms(TETile[][] world) {
        for (Room room : rooms) {
            room.removeExtraWalls(world);
        }
    }
}
