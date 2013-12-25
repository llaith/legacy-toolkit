package org.llaith.toolkit.common.pattern.impl;

import org.llaith.toolkit.common.pattern.Consumer;
import org.llaith.toolkit.common.guard.Guard;

/**
 * Idea from JDK 8 Consumer.
 */
public abstract class AbstractConsumer<T> implements Consumer<T> {

    @Override
    public abstract void accept(final T t);

    public Consumer<T> andThen(final Consumer<? super T> after) {
        Guard.notNull(after);
        return new Consumer<T>() {
            @Override
            public void accept(final T t) {
                AbstractConsumer.this.accept(t);
                after.accept(t);
            }
        };
    }

}
