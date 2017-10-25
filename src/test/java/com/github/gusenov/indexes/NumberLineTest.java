package com.github.gusenov.indexes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NumberLineTest {
    @Before
    public void before() {
    }

    private String replace(String text, Interval interval, String newValue) {
        switch (interval.getType()) {
            case CLOSED:
                return text.substring(0, interval.getEndpoint1() - 1)
                    + newValue
                    + text.substring(interval.getEndpoint2());
            case OPEN:
            case LEFT_CLOSED_RIGHT_OPEN:
            case LEFT_OPEN_RIGHT_CLOSED:
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Test
    public void test() {
        NumberLine line = new NumberLine();

        Interval[][] intervals = new Interval[32][32];

        // 0 1 2 3 4 5 6 7 8 9  10 11 12 13 14 15 16 17 18 19 20
        // 6 + ( 1 8 - 7 ) + (  1  5  -  7  -  4  -  2  )  -  9
        // 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21
        String expression = "6+(18-7)+(15-7-4-2)-9";

        intervals[1][21] = new Interval(1, 21, IntervalType.CLOSED);
        line.add(intervals[1][21]);
        assertFalse(line.add(intervals[1][21]));
        assertFalse(line.add(new Interval(1, 21, IntervalType.CLOSED)));
        assertEquals(1, line.size());

        // 3 4 5 6 7 8
        // ( 1 8 - 7 )
        intervals[3][8] = new Interval(3, 8, IntervalType.CLOSED);
        line.add(intervals[3][8]);
        assertFalse(line.add(intervals[3][8]));
        assertFalse(line.add(new Interval(3, 8, IntervalType.CLOSED)));
        assertEquals(2, line.size());

        // 10 11 12 13 14 15 16 17 18 19
        // (  1  5  -  7  -  4  -  2  )
        intervals[10][19] = new Interval(10, 19, IntervalType.CLOSED);
        line.add(intervals[10][19]);
        assertFalse(line.add(intervals[10][19]));
        assertFalse(line.add(new Interval(10, 19, IntervalType.CLOSED)));
        assertEquals(3, line.size());

        String replacement = "11";
        expression = replace(expression, intervals[3][8], replacement);
        assertEquals("6+11+(15-7-4-2)-9", expression);

        line.changeInterval(intervals[3][8], replacement.length());
        assertEquals(1, intervals[1][21].getEndpoint1());
        assertEquals(17, intervals[1][21].getEndpoint2());
        intervals[1][17] = intervals[1][21];
        assertEquals(6, intervals[10][19].getEndpoint1());
        assertEquals(15, intervals[10][19].getEndpoint2());
        intervals[6][15] = intervals[10][19];

        // 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
        // 6 + 1 1 + ( 1 5 - 7  -  4  -  2  )  -  9

        replacement = "2";
        expression = replace(expression, intervals[6][15], replacement);
        assertEquals("6+11+2-9", expression);

        line.changeInterval(intervals[6][15], replacement.length());
        assertEquals(1, intervals[1][17].getEndpoint1());
        assertEquals(8, intervals[1][17].getEndpoint2());
        intervals[1][8] = intervals[1][17];

        // 1 2 3 4 5 6 7 8
        // 6 + 1 1 + 2 - 9

        line.printIntervals();
    }
}
