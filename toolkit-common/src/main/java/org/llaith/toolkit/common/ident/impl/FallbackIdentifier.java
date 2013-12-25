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
