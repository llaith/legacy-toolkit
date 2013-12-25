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
package org.llaith.toolkit.common.lang;

import com.google.common.collect.ImmutableMap;
import org.llaith.toolkit.common.guard.Guard;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Little class to make it neat to make interpolated strings like:
 *     "this is the ${amount} for the ${order}."
 */
public final class ParamString {

    public static String replaceParams(final String template, final Params params) {
        return new ParamString(template).completeWith(params);
    }

    public static class Params {
        final ImmutableMap.Builder<String,Object> delegate = ImmutableMap.builder();
        public Params set(final String param, final Object value) {
            if (value != null) this.delegate.put(param,value);
            else this.delegate.put(param,"${"+param+"}");
            return this;
        }
        private Map<String,Object> build() {
            return this.delegate.build();
        }
    }

    public static ParamString templateOf(final String template) {
        return new ParamString(template);
    }

    public static Params paramsOf() {
        return new Params();
    }

    private final String template;

    private ParamString(final String template) {
        this.template = Guard.notNull(template);
    }

    /**
     * http://www.coderanch.com/t/375438/java/java/appendReplacement-appendTail
     * Henry Wong
     */
    public String completeWith(final Params params) {

        final Map<String,Object> replacements = Guard.notNull(params.build());

        final Matcher m = Pattern.compile("\\$\\{([^}]*)\\}").matcher(template);

        final StringBuffer sb = new StringBuffer(); // api needs a stringbuffer!
        while (m.find()) {
            final Object value = replacements.get(m.group(1));
            if (value != null) m.appendReplacement(sb,value.toString());
        }
        m.appendTail(sb);

        return sb.toString();
    }

}
