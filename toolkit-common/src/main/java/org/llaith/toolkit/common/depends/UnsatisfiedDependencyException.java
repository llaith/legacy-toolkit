package org.llaith.toolkit.common.depends;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 19/03/13
 */
public class UnsatisfiedDependencyException extends Exception {
    private final Collection<Dependency<?>> unsatisfied;

    public UnsatisfiedDependencyException(final Collection<Dependency<?>> unsatisfied) {
        this.unsatisfied = unsatisfied;
    }

    public Collection<Dependency<?>> getUnsatisfied() {
        return unsatisfied;
    }
}
