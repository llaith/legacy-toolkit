package org.llaith.toolkit.common.depends;


import java.util.Collection;
import java.util.HashSet;

public class CircularDependencyException extends Exception {

    private final Collection<Dependency<?>> circular = new HashSet<>();

    public CircularDependencyException(final Collection<Dependency<?>> circular) {
        super("Circular-dependency found");
        this.circular.addAll(circular);
    }

    public Collection<Dependency<?>> getCircular() {
        return this.circular;
    }

}
