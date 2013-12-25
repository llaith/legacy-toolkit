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
