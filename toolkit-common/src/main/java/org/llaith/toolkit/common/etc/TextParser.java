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
