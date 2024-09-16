package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<>(10);
        assertEquals(10, arb.capacity());
        arb.enqueue(33.1); // 33.1 null null  null
        assertEquals(1, arb.fillCount);
        arb.enqueue(44.8); // 33.1 44.8 null  null
        assertEquals(2, arb.fillCount);
        arb.enqueue(62.3); // 33.1 44.8 62.3  null
        assertEquals(3, arb.fillCount);
        arb.enqueue(-3.4); // 33.1 44.8 62.3 -3.4
        assertEquals(4, arb.fillCount);
        assertEquals(33.1, arb.dequeue(), 1e-10);   // 44.8 62.3 -3.4  null (returns 33.1)
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
