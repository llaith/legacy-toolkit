/*
 * Copyright (c) 2013 Nos Doughty
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.llaith.toolkit.util.misc;

import org.llaith.toolkit.util.guard.Guard;

/**
 *
 */
public final class Bound<T extends Comparable<T>> {

    public static <X extends Comparable<X>> Bound<X> newBound(final X min, final X max) {
        return new Bound<>(
                Guard.expectArg(min,"A value is expected for min."),
                Guard.expectArg(max,"A value is expected for max"));
    }

    public static <X extends Comparable<X>> Bound<X> newUpperBound(final X max) {
        return new Bound<>(
                null,
                Guard.expectArg(max,"A value is expected for max"));
    }

    public static <X extends Comparable<X>> Bound<X> newLowerBound(final X min) {
        return new Bound<>(
                Guard.expectArg(min,"A value is expected for min."),
                null);
    }

    private final T min;

    private final T max;

    /**
     * Must use named ctors to ensure null safety
     */
    private Bound(final T min, final T max) {
        if ( (min != null) && (max != null) && (min.compareTo(max) > 0)) throw new IllegalArgumentException("The min value of a range cannot be greater than the max value.");
        this.min = min;
        this.max = max;
    }

    public T min() {
        return min;
    }

    public T max() {
        return max;
    }

    public boolean includes(final T value) {
        Guard.expect(value,"value");
        return !((this.min != null) && (value.compareTo(this.min) < 0)) && !((this.max != null) && (value.compareTo(this.max) > 0));
    }

    public boolean includes(final Bound<T> bound) {
        Guard.expect(bound, "bound");
        return !(this.min != null && (bound.min == null || bound.min.compareTo(this.min) < 0)) && !(this.max != null && (bound.max == null || bound.max.compareTo(this.max) > 0));
    }

    public boolean intersects(final Bound<T> bound) {
        Guard.expect(bound, "bound");
        return !(this.max != null && bound.min != null && this.max.compareTo(bound.min) < 0) && !(this.min != null && bound.max != null && this.min.compareTo(bound.max) > 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bound bound = (Bound) o;

        return !(max != null ? !max.equals(bound.max) : bound.max != null) && !(min != null ? !min.equals(bound.min) : bound.min != null);
    }

    @Override
    public int hashCode() {
        int result = min != null ? min.hashCode() : 0;
        result = 31 * result + (max != null ? max.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bound{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }

}
