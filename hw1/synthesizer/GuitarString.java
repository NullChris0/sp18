package synthesizer;

import java.util.HashSet;
import java.util.Set;

public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private final BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int) (Math.round(SR / frequency));
        buffer = new ArrayRingBuffer<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }
        Set<Double> set = new HashSet<>(buffer.capacity());
        while (set.size() < buffer.capacity()) {
            set.add(Math.random() - 0.5);
        }
        for (Double e: set) {
            buffer.enqueue(e);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        double res = (buffer.dequeue() + sample()) * DECAY * 0.5;
        /*
         * Flipping the sign of the new value before enqueueing it in tic() will
         * change the sound from guitar-like to harp-like:
         * buffer.enqueue(-res);
         */
        buffer.enqueue(res);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
