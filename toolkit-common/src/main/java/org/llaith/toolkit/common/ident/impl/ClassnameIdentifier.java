package org.llaith.toolkit.common.ident.impl;

import org.llaith.toolkit.common.ident.Identifier;

/**
 * Common enough usecase to get it's own impl.
 */
public class ClassnameIdentifier implements Identifier {

    private final boolean fqn;

    public ClassnameIdentifier(final boolean fqn) {
        this.fqn = fqn;
    }

    @Override
    public String apply(final Object input) {
        return this.fqn ?
                input.getClass().getName() :
                input.getClass().getSimpleName();
    }

}
