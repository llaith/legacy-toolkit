/*
 * Copyright (c) 2005 - 2013 Nos Doughty
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
package org.llaith.toolkit.common.guard;

import com.google.common.base.Function;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.llaith.toolkit.common.exception.ThrowableFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * The hamcrest not(nullValue()) check isn't as useful as the thenReturnRequiredValue because
 * it won't take the @NonNull into account.
 */
public class Expect<E extends Throwable,T> { // throwables to include error for assertionerror.

    public static <X> Expect<IllegalArgumentException,X> expectArg(final X target) {
        return new Expect<>(IllegalArgumentException.class,target);
    }

    public static <X> Expect<IllegalStateException,X> expectState(final X target) {
        return new Expect<>(IllegalStateException.class,target);
    }

    public static <X> Expect<AssertionError,X> assertThat(final X target) {
        return new Expect<>(AssertionError.class,target);
    }

    private final ThrowableFactory<E> throwableFactory;

    private final T target;

    private String description = "The passed in argument";

    private Description failed;

    protected Expect(final Class<E> throwableClass, @Nullable final T target) {
        this.throwableFactory = new ThrowableFactory<>(throwableClass);
        this.target = target;
    }

    public Expect<E,T> describedAs(@Nonnull final String reason) {
        this.description = reason;

        return this;
    }

    @Nonnull
    public Expect<E,T> is(@Nonnull final Matcher<? super T> matcher) {

        if (this.hasFailed()) return this;

        if (!matcher.matches(this.target)) {
            this.failed = this.buildDescription(this.description,null,this.target,matcher);
        }

        return this;
    }

    // This is almost completely useless until JDK8, for two reasons. 1) without lambdas, it only works to have a static
    // imported extraction function, but 2) type inference in jdk7 doesn't take into account a parameter type. So it's
    // of extremely limited use at the moment, but is here to remind me to use it in jdk8.
    @Nonnull
    public <X> Expect<E,T> with(@Nonnull final String subDescription, @Nonnull final Function<T,X> extraction, @Nonnull final Matcher<? super X> matcher) {

        if (this.hasFailed()) return this;

        final X test = extraction.apply(this.target);

        if (!matcher.matches(test)) {
            this.failed = this.buildDescription(this.description,subDescription,test,matcher);
        }

        return this;
    }

    @Nonnull
    public T thenReturnExpected() throws E {

        // if null, override railed with hamcrest error
        if (this.target == null) this.failed = this.buildDescription(this.description,null,null,not(nullValue()));

        if (this.hasFailed()) {
            // repeated from thenReturnOptional() because of stack-trace level
            throw this.throwableFactory.throwableFor(1,""+this.failed);
        }

        return Guard.notNull(this.target); // purely for @Nonnull conversion
    }

    @Nullable
    public T thenReturnOptional() throws E {

        if (this.hasFailed()) {
            throw this.throwableFactory.throwableFor(1,""+this.failed);
        }

        return this.target;
    }

    private <X> Description buildDescription(@Nonnull final String description, @Nullable final String subDescription,
                                             @Nullable final X target, @Nonnull final Matcher<? super X> matcher) {

        final Description failure = new StringDescription()
                .appendText(String.format("%s is invalid",description))
                .appendText(Guard.defaultIfNull(" (It has an invalid '%s')",subDescription,""))
                .appendText(": ")
                .appendText("\n\tExpected: ")
                .appendDescriptionOf(matcher)
                .appendText("\n\t     but: ");

        matcher.describeMismatch(target,failure);

        return failure;

    }

    private boolean hasFailed() {
        return this.failed != null;
    }

}
