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
package org.llaith.toolkit.common.etc;

import com.google.common.base.Strings;
import org.llaith.toolkit.common.util.lang.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class TextParser {

    private final int tabSize;

    private final LinkedList<String> lines = new LinkedList<>();

    public TextParser(final int tabSize) {
        this.tabSize = tabSize;
    }

    public TextParser parse(final String text) {

        this.lines.addAll(Arrays.asList(this.replaceTabs(
                StringUtil.splitIntoLines(text),
                Strings.repeat(" ",this.tabSize))));

        return this;
    }

    public List<String> lines() {
        return Collections.unmodifiableList(this.lines);
    }

    private String[] replaceTabs(final String[] lines, final String replace) {

        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\t",replace);
        }

        return lines;
    }

}
