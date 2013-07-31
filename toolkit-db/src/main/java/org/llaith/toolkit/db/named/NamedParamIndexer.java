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
package org.llaith.toolkit.db.named;

import java.util.Map;

/**
 *
 */
public class NamedParamIndexer {

    private final String sql;
    private final Map<String,int[]> indexes;

    public NamedParamIndexer(String sql, Map<String, int[]> indexes) {
        this.sql = sql;
        this.indexes = indexes;
    }

    public String getSql() {
        return sql;
    }

    public Map<String, int[]> getIndexes() {
        return indexes;
    }

    /**
     * Returns the indexes for a parameter.
     *
     * @param name parameter name
     * @return parameter indexes
     * @throws IllegalArgumentException if the parameter does not exist
     */
    public int[] getIndexes(String name) {
        int[] indexes = this.indexes.get(name);
        if (indexes == null) {
            throw new IllegalArgumentException("Parameter not found: " + name);
        }
        return indexes;
    }

}
