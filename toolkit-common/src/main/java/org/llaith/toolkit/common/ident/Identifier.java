package org.llaith.toolkit.common.ident;

import com.google.common.base.Function;

/**
 * Various small reasons why I like this rather than just use Function<Object,String>
 *     directly. Extra typing, and refactoring support.
 *
 *
 */
public interface Identifier extends Function<Object,String> {
    // as is.
}
