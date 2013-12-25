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
public class Memo {

    private final List<Section> sections = new ArrayList<>();

    public Memo(Section... sections) {
        if (sections != null) {
            this.sections.addAll(Arrays.asList(sections));
        }
    }

    public List<Section> sections() {
        return Collections.unmodifiableList(sections);
    }

    public Memo withSection(final Section section) {
        this.sections.add(section);
        return this;
    }

    public <X extends Section> X addSection(final X section) {
        this.sections.add(section);
        return section;
    }

    public void addSections(final List<Section> sections) {
        this.sections.addAll(sections);
    }

}
