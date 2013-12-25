package org.llaith.toolkit.common.guard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.llaith.toolkit.common.util.lang.StringUtil.isBlank;

/**
 *
 */
public class Guard {

    @Nonnull
    public static <X> X notNull(@Nullable final X x) {
        if (x == null) throw new IllegalArgumentException("Value cannot be null.");
        return x;
    }

    public static <X> Expect<IllegalStateException,X> checkState(@Nonnull final X x) {
        return Expect.expectState(x);
    }

    @Nonnull
    public static <X> Expect<IllegalArgumentException,X> checkArg(@Nullable final X x) {
        return Expect.expectArg(x);
    }

    @Nullable
    public static String toNull(@Nullable final String s) {
        if (isBlank(s)) return null;
        return s;
    }

    @Nonnull
    public static String toEmpty(@Nullable final String s) {
        if (isBlank(s)) return "";
        return notNull(s);
    }

    @Nonnull
    public static String defaultIfNull(@Nullable final String s, @Nonnull final String defaultStr) {
        return Guard.toNull(s) != null ?
                notNull(s) :
                notNull(defaultStr);
    }

    @Nonnull
    public static String defaultIfNull(@Nonnull final String template, @Nullable final Object o, @Nonnull final String defaultStr) {
        if (o == null) return defaultStr;

        return String.format(template,o);
    }

    @Nullable
    public static <T> T[] toNull(@Nullable final T[] arr) {
        if (arr == null) return arr;
        if (arr.length < 1) return null;
        return arr;
    }

    @Nonnull
    public static <T> Iterable<T> toEmpty(@Nullable final Iterable<T> it) {
        if (it != null) return it;
        return (Iterable<T>)Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    public static <T> T[] toEmpty(@Nullable final T[] arr) {
        if (arr == null) return (T[])new ArrayList<T>().toArray();
        return arr;
    }

    @Nullable
    public static <T>List<T> toListOrNull(@Nullable final T[] arr) {
        if (arr == null) return null;
        return Arrays.asList(arr);
    }

    @Nonnull
    public static <T>List<T> toListOrEmpty(@Nullable final T[] arr) {
        if (arr == null) return new ArrayList<>();
        return Arrays.asList(arr);
    }

    public static void main(String[] args) {

        try {
            Guard.checkArg("Hello").is(equalTo("There")).thenReturnExpected();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Guard.checkArg("Hello").is(equalTo("There")).thenReturnOptional();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Guard.checkArg((String)null).is(equalTo("There")).thenReturnExpected();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
