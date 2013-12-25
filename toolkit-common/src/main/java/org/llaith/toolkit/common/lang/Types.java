/*******************************************************************************

  * Copyright (c) 2005 - 2013 Nos Doughty
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.

 ******************************************************************************/
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
