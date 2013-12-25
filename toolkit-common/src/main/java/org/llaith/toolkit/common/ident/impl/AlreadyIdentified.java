package org.llaith.toolkit.common.ident.impl;

import org.llaith.toolkit.common.ident.Identified;
import org.llaith.toolkit.common.ident.Identifier;

/**
 *
 */
public class AlreadyIdentified implements Identifier {

    @Override
    public String apply(final Object o) {
        if (o instanceof Identified) return ((Identified)o).identifier();
        return null;
    }

}
