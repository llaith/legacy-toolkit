package org.llaith.toolkit.common.ident.impl;

import org.llaith.toolkit.common.ident.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Returns first non-null identification.
 */
public class CompoundIdentifier implements Identifier {

    private List<Identifier> identifiers = new ArrayList<>();

    public CompoundIdentifier(final List<? extends Identifier> identifiers) {
        this.identifiers.addAll(identifiers);
    }

    public CompoundIdentifier addIdentifiers(final Identifier identifier) {
        this.identifiers.add(identifier);
        return this;
    }

    public CompoundIdentifier addAllIdentifiers(final List<? extends Identifier> identifiers) {
        this.identifiers.addAll(identifiers);
        return this;
    }

    @Override
    public String apply(final Object input) {
        for (final Identifier idfer : this.identifiers) {
            final String id = idfer.apply(input);
            if (id != null) return id;
        }
        return null;
    }

}
