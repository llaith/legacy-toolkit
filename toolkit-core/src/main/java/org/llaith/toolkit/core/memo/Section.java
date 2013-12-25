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
public class Section {

    private final int level;
    private final String heading;

    private final List<Block> blocks = new ArrayList<>();

    public Section(final int level) {
        this(level,(Block[])null);
    }

    public Section(final int level, final Block... blocks) {
        this(level,null,blocks);
    }

    public Section(final int level, final String heading, final Block... blocks) {
        this.level = level;

        this.heading = heading; // ok as null

        if (blocks != null) this.blocks.addAll(Arrays.asList(blocks));
    }

    public int level() {
        return level;
    }

    public boolean hasHeading() {
        return heading != null;
    }

    public String heading() {
        return heading;
    }

    public boolean hasBlocks() {
        return !this.blocks.isEmpty();
    }

    public List<Block> blocks() {
        return Collections.unmodifiableList(blocks);
    }

    public Section withBlock(final Block block) {
        this.blocks.add(block);
        return this;
    }

    public <X extends Block> X addBlock(final X block) {
        this.blocks.add(block);
        return block;
    }

    public void addBlocks(final List<Block> blocks) {
        this.blocks.addAll(blocks);
    }

}
