package synthesizer;
import org.junit.Test;

import java.net.Inet4Address;
import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        arb.enqueue(10);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(9);

        for (int i: arb) {
            System.out.println(i);
        }

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
//        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        arb.enqueue(10);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(9);

        for (Integer i: arb) {
            System.out.println(i);
        }
    }
} 
