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
