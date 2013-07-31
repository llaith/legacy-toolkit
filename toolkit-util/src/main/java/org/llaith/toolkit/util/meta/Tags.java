/*
 * Copyright (c) 2013 Nos Doughty
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
package org.llaith.toolkit.util.meta;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class Tags {

    private final Set<String> tags = new HashSet<>();

    public boolean containsTag(final String tag) {
        return this.tags.contains(tag);
    }

    public void addTag(final String tag) {
        this.tags.add(tag);
    }

    public void removeTag(final String tag) {
        this.tags.remove(tag);
    }

}
