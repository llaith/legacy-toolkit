/*
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
 */
package org.llaith.toolkit.common.util.lang;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.llaith.toolkit.common.guard.Guard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

/**
 * I prefer my own to the ones in the other libs.
 */
public final class StringUtil {

    private StringUtil() {
    }

    public static boolean notBlank(@Nullable final String s) {
        return !isEmpty(s);
    }

    public static boolean isBlank(@Nullable final String s) {
        return s == null || s.trim().length() < 1;
    }

    public static boolean notEmpty(@Nullable final String s) {
        return !isEmpty(s);
    }

    public static boolean isEmpty(@Nullable final String s) {
        return s == null || s.length() < 1;
    }

    @Nonnull
    public static String uppercaseFirstLetter(@Nonnull final String s) {
        return Guard.notNull(s).substring(0,1).toUpperCase() + s.substring(1);
    }

    @Nonnull
    public static String dropLastLetter(@Nonnull final String s) {
        return Guard.notNull(s).substring(0,s.length() - 1);
    }

    @Nonnull
    public static String depluralize(@Nonnull final String s) {
        if (s.endsWith("ies")) return s.substring(0,s.length() - 3) + "y"; // propert(ies)y
        if (s.endsWith("ses")) return s.substring(0,s.length() - 2); // Process(es)
        else return dropLastLetter(s);
    }

    @Nullable
    public static String propertyizeMethodName(@Nonnull final String s) {
        if (s.startsWith("get")) return s.substring(3);
        if (s.startsWith("set")) return s.substring(3);
        if (s.startsWith("is")) return s.substring(2);
        return null;
    }

    public static boolean isValidJavaIdentifier(@Nonnull final String str) {

        if (Guard.toNull(str) == null) return false;

        final char[] arr = str.toCharArray();
        if (!Character.isJavaIdentifierStart(arr[0])) return false;

        for (int i = 1; i < arr.length; i++) {
            if (!Character.isJavaIdentifierPart(arr[i])) return false;
        }

        return true;
    }

    public static int countLeadingSpaces(final String line) {
        int count = 0;

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ') count++;
            else break;
        }

        return count;
    }

    public static int countLeadingWhitepace(final String line) {
        int count = 0;

        for (int i = 0; i < line.length(); i++) {
            if (Character.isWhitespace(line.charAt(i))) count++;
            else break;
        }

        return count;
    }

    /**
     * Replaced my own util with this. Use directly except where compatibility is required.
     */
    @Nonnull
    public static List<String> splitAtLength(@Nonnull final String s, final int charLimit) {
        return Lists.newArrayList(Splitter.fixedLength(charLimit).split(s));
    }

    public static String[] splitIntoLines(final String s) {
        return splitIntoLines(s, false);
    }

    public static String[] splitIntoLines(final String s, final boolean skipBlankLines) {
        return skipBlankLines ?
                s.split("[\\r\\n]+") :
                s.split("\\r?\\n");
    }

    public static List<String> wrap(final String text, final int maxLength) {

        final int max = maxLength < 1 ? 255 : maxLength;
        final int length = Guard.notNull(text).length();

        final List<String> lines = new LinkedList<>();

        int pos = 0;
        while ((length - pos) > max) {

            if (text.charAt(pos) == ' ') {
                pos++;
                continue;
            }

            int nextBreak = text.lastIndexOf(' ', max + pos);

            if (nextBreak >= pos) {
                lines.add(text.substring(pos, nextBreak));
                pos = nextBreak + 1;
            } else { // long words get split at length
                lines.add(text.substring(pos, max + pos));
                pos += max;
            }
        }

        // left over
        lines.add(text.substring(pos));

        return lines;
    }

    public static List<String> reflowTextToLines(final String text, final int maxLength) {

        final List<String> lines = new LinkedList<>();

        for (final String line : StringUtil.splitIntoLines(text)) {
            lines.addAll(wrap(line, maxLength));
        }

        return lines;
    }

    public static String reflowText(final String text, final int maxLength) {
        return Joiner.on("\n").join(reflowTextToLines(text, maxLength));
    }

}
