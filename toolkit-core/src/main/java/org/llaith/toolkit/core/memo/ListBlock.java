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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class ListBlock extends Block {

    private final boolean numeric;
    private final List<ListItem> items = new ArrayList<>();

    public ListBlock(final boolean numeric) {
        super();
        this.numeric = numeric;
    }

    public ListBlock(final boolean numeric, final String title) {
        super(title);
        this.numeric = numeric;
    }

    public ListBlock(final boolean numeric, final ListItem... items) {
        super();

        this.numeric = numeric;

        if (items != null) this.items.addAll(Arrays.asList(items));
    }

    public ListBlock(final boolean numeric, final String title, final ListItem... items) {
        super(title);

        this.numeric = numeric;

        if (items != null) this.items.addAll(Arrays.asList(items));
    }

    public boolean numeric() {
        return numeric;
    }

    public List<ListItem> items() {
        return Collections.unmodifiableList(items);
    }

    public ListBlock withItem(final ListItem item) {
        this.items.add(item);
        return this;
    }

    public <X extends ListItem> X addItem(final X item) {
        this.items.add(item);
        return item;
    }

    public void addItems(final List<ListItem> items) {
        this.items.addAll(items);
    }

}
