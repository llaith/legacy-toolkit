package org.llaith.toolkit.common.ident.impl;

import org.llaith.toolkit.common.ident.Identifier;
import org.llaith.toolkit.common.guard.Guard;

import java.util.Arrays;
import java.util.List;

/**
 * Common enough usecase to get it's own impl.
 */
public class FallbackIdentifier implements Identifier {

    private final Identifier fallback;
    private final CompoundIdentifier identifiers;

    public FallbackIdentifier(final Identifier fallback, final Identifier identifier) {
        this(fallback,Arrays.asList(identifier));
    }

    public FallbackIdentifier(final Identifier fallback, final List<? extends Identifier> identifiers) {
        this.fallback = fallback;
        this.identifiers = new CompoundIdentifier(identifiers);
    }

    @Override
    public String apply(final Object input) {
        final String id = this.identifiers.apply(Guard.notNull(input));
        if (id != null) return id;
        return this.fallback.apply(input);
    }

}
