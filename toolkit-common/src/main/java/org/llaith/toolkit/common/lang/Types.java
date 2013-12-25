package org.llaith.toolkit.common.lang;

import com.google.common.reflect.TypeToken;

/**
 * Used to cheat around the TypeToken not allowing a cast down to <T>, which of course
 * they do for a very good reason (safety) it's just that, basically, I value prettiness
 * above true type safety! ;)
 */
public class Types {

    /*
     * NOTE: Important. When you use this in a selector, which is the initial use case, it doesn't
     * really restrict it past the raw class (which is determined at runtime), it just helps with
     * the compiler signature casts. For that reason, it's probably better to use the TypeToken
     * directly and I will be adding that to the repository when the FluentIterable implements a
     * filter based on TypeTokens.
     */
    public static <X> Class<X> coerce(final TypeToken<X> token) {
        // convert from <?> to 'raw' to <T>
        @SuppressWarnings({"rawtypes","UnnecessaryLocalVariable"})
        final Class rawClazz = token.getRawType();
        @SuppressWarnings({"unchecked","UnnecessaryLocalVariable"})
        final Class<X> ret = rawClazz;
        return ret;
    }

}
