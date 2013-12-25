package org.llaith.toolkit.common.guard;

import com.google.common.base.Function;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Some common static helpers for extraction until jdk8 comes along.
 */
public class Extract {

    public static <T> Function<List<T>,T> firstResult() {
        return new Function<List<T>,T>() {
            @Nullable
            @Override
            public T apply(@Nullable final List<T> input) {
                if (input == null) return null;
                return input.get(0);
            }
        };
    }

}
