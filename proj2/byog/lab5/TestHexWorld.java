package byog.lab5;

import org.junit.Test;
import static org.junit.Assert.*;
import static byog.lab5.HexWorld.*;

public class TestHexWorld {

    @Test
    public void testHexRowWidth() {
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));

        assertEquals(3, hexRowWidth(3, 0));
        assertEquals(5, hexRowWidth(3, 1));
        assertEquals(7, hexRowWidth(3, 2));
        assertEquals(7, hexRowWidth(3, 3));
        assertEquals(5, hexRowWidth(3, 4));
        assertEquals(3, hexRowWidth(3, 5));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(0, hexRowOffset(3, 5));
    }

    @Test
    public void testHexPrint() {
        addHexagon(6);
    }

    @Test
    public void testGetSizeNum() {
        assertEquals(1, getSizeNum(0));
        assertEquals(7, getSizeNum(1));
        assertEquals(19, getSizeNum(2));
        for (Position p : getPos(new Position(50, 50), 1, 3)) {
            System.out.println(p);
        }
    }
}
