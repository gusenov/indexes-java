package com.github.gusenov.indexes;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Collection;

/**
 * Числовая ось.
 */
public class NumberLine implements Collection<Interval> {
    private List<Interval> intervals;

    public NumberLine() {
        this.intervals = new LinkedList<>();
    }

    /**
     * Добавить промежуток.
     *
     * @param interval промежуток.
     */
    public boolean add(Interval interval) {
        // Не будем добавлять промежуток, если он уже есть на оси.

//        for (Interval otherInterval : this.intervals) {
//            if (otherInterval.equals(interval)) {
//                return false;
//            }
//        }

        if (this.intervals.contains(interval)) {
            // Returns false if this collection does not permit duplicates and already contains the specified element.
            return false;
        }

        return this.intervals.add(interval);
    }

    /**
     * Удалить промежуток.
     *
     * @param interval промежуток.
     */
    public boolean remove(Object interval) {
//        ListIterator it = this.intervals.listIterator();
//        while (it.hasNext()) {
//            Interval otherInterval = (Interval) it.next();
//            if (interval.equals(otherInterval)) {
//                it.remove();
//                // Returns true if this collection contained the specified element
//                // (or equivalently, if this collection changed as a result of the call).
//                return true;
//            }
//        }
//        return false;

        return this.intervals.remove(interval);
    }

    public void changeInterval(Interval interval, int newLength) {
        // Для начала удалим промежуток с числовой оси.
        // Это нужно для того чтобы он не мешался.
        this.remove(interval);

        // Если новая длина промежутка не равна старой, то нужно исправить промежутки на которые это повлияло.
        if (newLength != interval.length()) {
            // Величина на которую нужно изменить затронутые промежутки.
            Integer correction = newLength - interval.length();

            ListIterator it = this.intervals.listIterator();
            while (it.hasNext()) {
                Interval otherInterval = (Interval) it.next();
                switch (interval.getType()) {
                    case OPEN:  // (a',b')
                        switch (otherInterval.getType()) {
                            case OPEN:  // (a,b)
                                throw new UnsupportedOperationException();
                            case CLOSED:  // [a,b]
                                throw new UnsupportedOperationException();
                            case LEFT_CLOSED_RIGHT_OPEN:  // [a,b)
                                throw new UnsupportedOperationException();
                            case LEFT_OPEN_RIGHT_CLOSED:  // (a,b]
                                throw new UnsupportedOperationException();
                        }
                        break;
                    case CLOSED:  // [a',b']
                        switch (otherInterval.getType()) {
                            case OPEN:  // (a,b)
                                throw new UnsupportedOperationException();
                            case CLOSED:  // [a,b]
                                // Если другой промежуток заканчивается после изменяемого:
                                // ...a...a'...b'...b...
                                // ...a'...b'...a...b...
                                if (otherInterval.getEndpoint2() > interval.getEndpoint2()) {
                                    otherInterval.incEndpoint2(correction);  // корректируем конечный индекс промежутка
                                }
                                // Если другой промежуток начинается после изменяемого:
                                // ...a'...b'...a...b...
                                if (otherInterval.getEndpoint1() > interval.getEndpoint2()) {
                                    otherInterval.incEndpoint1(correction);  // корректируем начальный индекс промежутка
                                }
                                break;
                            case LEFT_CLOSED_RIGHT_OPEN:  // [a,b)
                                throw new UnsupportedOperationException();
                            case LEFT_OPEN_RIGHT_CLOSED:  // (a,b]
                                throw new UnsupportedOperationException();
                        }
                        break;
                    case LEFT_CLOSED_RIGHT_OPEN:  // [a',b')
                        switch (otherInterval.getType()) {
                            case OPEN:  // (a,b)
                                throw new UnsupportedOperationException();
                            case CLOSED:  // [a,b]
                                throw new UnsupportedOperationException();
                            case LEFT_CLOSED_RIGHT_OPEN:  // [a,b)
                                throw new UnsupportedOperationException();
                            case LEFT_OPEN_RIGHT_CLOSED:  // (a,b]
                                throw new UnsupportedOperationException();
                        }
                        break;
                    case LEFT_OPEN_RIGHT_CLOSED:  // (a',b']
                        switch (otherInterval.getType()) {
                            case OPEN:  // (a,b)
                                throw new UnsupportedOperationException();
                            case CLOSED:  // [a,b]
                                throw new UnsupportedOperationException();
                            case LEFT_CLOSED_RIGHT_OPEN:  // [a,b)
                                throw new UnsupportedOperationException();
                            case LEFT_OPEN_RIGHT_CLOSED:  // (a,b]
                                throw new UnsupportedOperationException();
                        }
                        break;
                } // switch
            }  // while
        }  // if

        // Теперь освободив пространство изменяем непосредственно сам промежуток.
        // Увеличиваем его так чтобы получить заданную длину.
        interval.incEndpoint2(newLength - interval.length());

        // Нужно добавить промежуток в список потому что в последующем изменения других промежуток могут его коснуться.
        this.add(interval);
    }

    public List<Interval> getSubset(Interval interval, boolean isStrict) {
        List<Interval> subset = new LinkedList<>();
        for (Interval otherInterval : this) {
            switch (interval.getType()) {
                case OPEN:  // (a',b')
                    switch (otherInterval.getType()) {
                        case OPEN:  // (a,b)
                            throw new UnsupportedOperationException();
                        case CLOSED:  // [a,b]
                            throw new UnsupportedOperationException();
                        case LEFT_CLOSED_RIGHT_OPEN:  // [a,b)
                            throw new UnsupportedOperationException();
                        case LEFT_OPEN_RIGHT_CLOSED:  // (a,b]
                            throw new UnsupportedOperationException();
                    }
                    break;
                case CLOSED:  // [a',b']
                    switch (otherInterval.getType()) {
                        case OPEN:  // (a,b)
                            throw new UnsupportedOperationException();
                        case CLOSED:  // [a,b]
                            if (otherInterval.getEndpoint1() >= interval.getEndpoint1()
                                    && otherInterval.getEndpoint2() <= interval.getEndpoint2()) {
                                if (isStrict && otherInterval.equals(interval)) {
                                    continue;
                                }
                                subset.add(otherInterval);
                            }
                            break;
                        case LEFT_CLOSED_RIGHT_OPEN:  // [a,b)
                            throw new UnsupportedOperationException();
                        case LEFT_OPEN_RIGHT_CLOSED:  // (a,b]
                            throw new UnsupportedOperationException();
                    }
                    break;
                case LEFT_CLOSED_RIGHT_OPEN:  // [a',b')
                    switch (otherInterval.getType()) {
                        case OPEN:  // (a,b)
                            throw new UnsupportedOperationException();
                        case CLOSED:  // [a,b]
                            throw new UnsupportedOperationException();
                        case LEFT_CLOSED_RIGHT_OPEN:  // [a,b)
                            throw new UnsupportedOperationException();
                        case LEFT_OPEN_RIGHT_CLOSED:  // (a,b]
                            throw new UnsupportedOperationException();
                    }
                    break;
                case LEFT_OPEN_RIGHT_CLOSED:  // (a',b']
                    switch (otherInterval.getType()) {
                        case OPEN:  // (a,b)
                            throw new UnsupportedOperationException();
                        case CLOSED:  // [a,b]
                            throw new UnsupportedOperationException();
                        case LEFT_CLOSED_RIGHT_OPEN:  // [a,b)
                            throw new UnsupportedOperationException();
                        case LEFT_OPEN_RIGHT_CLOSED:  // (a,b]
                            throw new UnsupportedOperationException();
                    }
                    break;
            } // switch
        }
        return subset;
    }

    public void removeSubset(Interval interval, boolean isStrict) {
        List<Interval> subset = this.getSubset(interval, isStrict);
        for(Interval otherInterval : subset) {
            this.remove(otherInterval);
        }
    }

    public void printIntervals() {
        for (Interval interval : this.intervals) {
            System.out.println(interval.toString());
        }
    }

    public Object[] toArray() {
        return this.intervals.toArray();
    }

    public <T> T[] toArray(T[] var1) {
        return this.intervals.toArray(var1);
    }

    public boolean removeAll(Collection<?> var1) {
        return this.intervals.removeAll(var1);
    }

    public boolean retainAll(Collection<?> var1) {
        return this.intervals.retainAll(var1);
    }

    public Iterator<Interval> iterator() {
        return this.intervals.iterator();
    }

    public void clear() {
        this.intervals.clear();
    }

    public boolean contains(Object var1) {
        return this.intervals.contains(var1);
    }

    public int size() {
        return this.intervals.size();
    }

    public boolean isEmpty() {
        return this.intervals.isEmpty();
    }

    public boolean containsAll(Collection<?> var1) {
        return this.intervals.containsAll(var1);
    }

    public boolean addAll(Collection<? extends Interval> var1) {
        return this.intervals.addAll(var1);
    }
}
