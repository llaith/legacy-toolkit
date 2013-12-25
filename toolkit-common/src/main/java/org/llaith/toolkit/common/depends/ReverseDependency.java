package org.llaith.toolkit.common.depends;

import org.llaith.toolkit.common.guard.Guard;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <S>
 */
public final class ReverseDependency<S> {

    private final Dependency<S> dependency;

    private final List<S> reverseDependants = new ArrayList<>();

    public ReverseDependency(final Dependency<S> dependency) {
        this.dependency = Guard.notNull(dependency);
    }

    public Dependency<S> dependency() {
        return this.dependency;
    }

    public List<S> reverseDependants() {
        return this.reverseDependants;
    }

}
