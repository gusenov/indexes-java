package com.github.gusenov.indexes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntervalTest {
    @Before
    public void before() {
        // Do nothing.
    }

    /**
     * Тестирование клонирования промежутка.
     *
     * @param interval
     * @throws CloneNotSupportedException
     */
    private void testClone(Interval interval) throws CloneNotSupportedException {
        Object clone = interval.clone();

        // При клонировании должно выполняться 3 условия:

        // 1) x.clone() != x will be true
        assertTrue(clone != interval);

        // 2) x.clone().getClass() == x.getClass() will be true, but these are not absolute requirements.
        assertTrue(clone.getClass() == interval.getClass());

        // 3) x.clone().equals(x) will be true, this is not an absolute requirement.
        assertEquals(interval, clone);
    }

    @Test
    public void test() throws CloneNotSupportedException {
        Interval interval;

        interval = new Interval(3, 6, IntervalType.OPEN);  // (3,6)
        assertEquals(3, interval.getEndpoint1());
        assertEquals(6, interval.getEndpoint2());
        assertEquals(IntervalType.OPEN, interval.getType());
        assertEquals("(3,6)", interval.toString());
        assertEquals(2, interval.length());  // |{4,5}|=2
        assertEquals(new Interval(3, 6, IntervalType.OPEN), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.CLOSED), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.LEFT_OPEN_RIGHT_CLOSED), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.LEFT_CLOSED_RIGHT_OPEN), interval);
        testClone(interval);

        interval = new Interval(3, 6, IntervalType.CLOSED);  // [3,6]
        assertEquals(3, interval.getEndpoint1());
        assertEquals(6, interval.getEndpoint2());
        assertEquals(IntervalType.CLOSED, interval.getType());
        assertEquals("[3,6]", interval.toString());
        assertEquals(4, interval.length());  // |{3,4,5,6}|=4
        assertEquals(new Interval(3, 6, IntervalType.CLOSED), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.OPEN), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.LEFT_OPEN_RIGHT_CLOSED), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.LEFT_CLOSED_RIGHT_OPEN), interval);
        testClone(interval);

        interval = new Interval(3, 6, IntervalType.LEFT_OPEN_RIGHT_CLOSED);  // (3,6]
        assertEquals(3, interval.getEndpoint1());
        assertEquals(6, interval.getEndpoint2());
        assertEquals(IntervalType.LEFT_OPEN_RIGHT_CLOSED, interval.getType());
        assertEquals("(3,6]", interval.toString());
        assertEquals(3, interval.length());  // |{4,5,6}|=2
        assertEquals(new Interval(3, 6, IntervalType.LEFT_OPEN_RIGHT_CLOSED), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.OPEN), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.CLOSED), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.LEFT_CLOSED_RIGHT_OPEN), interval);
        testClone(interval);

        interval = new Interval(3, 6, IntervalType.LEFT_CLOSED_RIGHT_OPEN);  // [3,6)
        assertEquals(3, interval.getEndpoint1());
        assertEquals(6, interval.getEndpoint2());
        assertEquals(IntervalType.LEFT_CLOSED_RIGHT_OPEN, interval.getType());
        assertEquals("[3,6)", interval.toString());
        assertEquals(3, interval.length());  // |{3,4,5}|=2
        assertEquals(new Interval(3, 6, IntervalType.LEFT_CLOSED_RIGHT_OPEN), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.OPEN), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.CLOSED), interval);
        assertNotEquals(new Interval(3, 6, IntervalType.LEFT_OPEN_RIGHT_CLOSED), interval);
        testClone(interval);
    }
}
