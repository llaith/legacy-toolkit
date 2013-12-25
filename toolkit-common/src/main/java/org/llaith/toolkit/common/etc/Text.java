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

import java.util.LinkedList;
import java.util.List;

/**
 * Replace leading tabs with spaces, output is extremely variable with them.
 */
public class Text {

    public enum BreakMode {
        NONE, //
        SAME, //
        EXTRA // extra tab-break
    }

    private final int maxWidth;

    private final LinkedList<String> lines = new LinkedList<>();

    public Text(final int maxWidth) {
        this.maxWidth = maxWidth < 1 ? 240 : maxWidth;
    }

    public void append(final int indent, final String text) {
        this.newline(indent, this.lines.removeLast() + text);
    }

    public void newline(final int indentChars, final String text) {

        // first line with passed in indent
        // any overflows with passed in indent + mode.
        // change indent to tabs and store tabspace?

        for (final String line : StringUtil.reflowTextToLines(text, Math.max(maxWidth - indentChars, 1))) {
            lines.add(Strings.repeat(" ", indentChars) + line);
        }

    }

    public void newline() {
        lines.add("");
    }

    public List<String> lines() {
        return lines;
    }

}
