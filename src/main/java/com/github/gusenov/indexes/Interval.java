package com.github.gusenov.indexes;

import java.io.Serializable;
import java.util.Objects;

/**
 * Промежуток.
 */
public class Interval implements Cloneable, Serializable {
    /**
     * Левый конец промежутка.
     */
    private int endpoint1;

    public int getEndpoint1() {
        return this.endpoint1;
    }

    public void setEndpoint1(int value) {
        assert (value <= this.endpoint2);
        this.endpoint1 = value;
    }

    /**
     * Изменить значение {@link Interval#endpoint1} на величину <code>delta</code>.
     *
     * @param delta разность для изменения значения свойства {@link Interval#endpoint1}.
     */
    public void incEndpoint1(int delta) {
        assert (this.endpoint1 + delta <= this.endpoint2);
        this.endpoint1 += delta;
    }

    /**
     * Правый конец промежутка.
     */
    private int endpoint2;

    public int getEndpoint2() {
        return this.endpoint2;
    }

    public void setEndpoint2(int value) {
        assert (value >= this.endpoint1);
        this.endpoint2 = value;
    }

    /**
     * Изменить значение {@link Interval#endpoint2} на величину <code>delta</code>.
     *
     * @param delta разность для изменения значения свойства {@link Interval#endpoint2}.
     */
    public void incEndpoint2(int delta) {
        this.endpoint2 += delta;
    }

    /**
     * Тип промежутка.
     */
    private IntervalType type;

    public IntervalType getType() {
        return this.type;
    }

    public void setType(IntervalType value) {
        this.type = value;
    }

    public Interval(int endpoint1, int endpoint2, IntervalType type) {
        assert (endpoint1 <= endpoint2);
        this.endpoint1 = endpoint1;
        this.endpoint2 = endpoint2;
        this.type = type;
    }

    public Interval(Interval otherInterval) {
        this.endpoint1 = otherInterval.getEndpoint1();
        this.endpoint2 = otherInterval.getEndpoint2();
        this.type = otherInterval.getType();
    }

    public int length() {
        switch (this.getType()) {
            case OPEN:
                // (4,8)={5,6,7}
                // |{5,6,7}|=3
                // 8-4-1=3
                return this.getEndpoint2() - this.getEndpoint1() - 1;
            case CLOSED:
                // [4,8]={4,5,6,7,8}
                // |{4,5,6,7,8}|=5
                // 8-4+1=5
                return this.getEndpoint2() - this.getEndpoint1() + 1;
            case LEFT_CLOSED_RIGHT_OPEN:
                // [4,8)={4,5,6,7}
                // |{4,5,6,7}|=4
                // 8-4=4
            case LEFT_OPEN_RIGHT_CLOSED:
                // (4,8]={5,6,7,8}
                // |{5,6,7,8}|=4
                // 8-4=4
                return this.getEndpoint2() - this.getEndpoint1();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {  // self check
            return true;
        }
        if (o == null) {  // null check
            return false;
        }
        if (getClass() != o.getClass()) {  // type check and cast
            return false;
        }
        Interval otherInterval = (Interval) o;
        // field comparison
        return Objects.equals(this.getEndpoint1(), otherInterval.getEndpoint1())
            && Objects.equals(this.getEndpoint2(), otherInterval.getEndpoint2())
            && Objects.equals(this.getType(), otherInterval.getType());
    }

    public boolean notEqualTo(Interval otherInterval) {
        return !this.equals(otherInterval);
    }

    @Override
    public String toString() {
        char left, right;
        switch (this.getType()) {
            case OPEN:
                left = '('; right = ')';
                break;
            case CLOSED:
                left = '['; right = ']';
                break;
            case LEFT_CLOSED_RIGHT_OPEN:
                left = '['; right = ')';
                break;
            case LEFT_OPEN_RIGHT_CLOSED:
                left = '('; right = ']';
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return String.format("%c%d,%d%c", left, this.getEndpoint1(), this.getEndpoint2(), right);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Interval cloned = (Interval)super.clone();
        return cloned;
    }

}
