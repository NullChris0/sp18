package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import static byog.Core.Pixel.isValid;

import java.util.Random;

public class Avatar {
    private Position currentP;
    boolean hasWin = false;
    transient TETile[][] world;

    public Avatar(long seed, TETile[][] world) {
        this.world = world;
        Random gen = new Random(seed);
        Position p;
        do {
            p = new Position(gen.nextInt(world.length), gen.nextInt(world[0].length));
        } while (!isValid(p, world, Tileset.FLOOR));
        currentP = p;
        draw();
    }

    public void move(int[] directs) {
        Position newP = new Position(currentP.x + directs[0], currentP.y + directs[1]);
        if (isValid(newP, world, Tileset.FLOOR)) {
            world[currentP.x][currentP.y] = Tileset.FLOOR;
            currentP = newP;
        }
        if (isValid(newP, world, Tileset.LOCKED_DOOR)) {
            currentP = newP;
            hasWin = true;
        }
        draw();
    }

    private void draw() {
        world[currentP.x][currentP.y] = Tileset.PLAYER;
    }
}
