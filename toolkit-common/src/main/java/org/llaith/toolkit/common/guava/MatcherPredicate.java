package org.llaith.toolkit.common.guava;

import com.google.common.base.Predicate;
import org.hamcrest.Matcher;

/**
 *
 */
public class MatcherPredicate<T> implements Predicate<T> {

    public static <X> MatcherPredicate<X> match(final Matcher<X> matcher) {
        return new MatcherPredicate<>(matcher);
    }

    private final Matcher<T> matcher;

    public MatcherPredicate(final Matcher<T> matcher) {
        this.matcher = matcher;
    }

    public boolean apply(final T input) {
        return matcher.matches(input);
    }
}
