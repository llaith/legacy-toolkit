package org.llaith.toolkit.common.depends;


public class MissingReverseDependencyException extends Exception {

    private final Object missing;

    public MissingReverseDependencyException(final Object missing) {
        super("Missing dependency information.");
        this.missing = missing;
    }

    public Object getMissing() {
        return missing;
    }
}
