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
package org.llaith.toolkit.core.memo;

/*
 * Note: a title on a text block is *not* the same as it's section heading (which
 * controls it's nesting and outline level). The title is more like a 'dictionary'
 * entry, group signifier, or sidebar title, and is completely optional.
 */
public abstract class Block {

    private final String title;

    public Block() {
        this(null);
    }

    public Block(final String title) {
        this.title = title;
    }

    public boolean hasTitle() {
        return this.title != null;
    }

    public String title() {
        return title;
    }

}
