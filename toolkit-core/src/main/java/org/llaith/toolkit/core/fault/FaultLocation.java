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
package org.llaith.toolkit.core.fault;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.llaith.toolkit.common.guard.Guard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 /**
 * Used to determine if another FaultLocation is included within the
 * current one. Eg.
 *
 * assertTrue(new FaultLocation("file1",100).includes(new FaultLocation("file1",100))
 * assertTrue(new FaultLocation("file1",100).includes(new FaultLocation("file1"))
 * assertFalse(new FaultLocation("file1",100).includes(new FaultLocation("file1",101))
 * assertFalse(new FaultLocation("file1",100).includes(new FaultLocation("file2"))
 *
 * and
 *
 * assertTrue(new FaultLocation(obj1,field1).includes(new FaultLocation(obj1,field1))
 * assertTrue(new FaultLocation(obj1,field1).includes(new FaultLocation(obj1))
 * assertFalse(new FaultLocation(obj1,field1).includes(new FaultLocation(obj1,field2))
 * assertFalse(new FaultLocation(obj1,field1).includes(new FaultLocation(obj2))
 *
 */
public class FaultLocation {

    public static final String JOIN_CHAR = "|";
    private static final Joiner joiner = Joiner.on(JOIN_CHAR);

    private final List<String> path;
    private final String fullPath;
    private final Set<String> subPaths;

    public FaultLocation(final String path) {
        this(Arrays.asList(path));
    }

    public FaultLocation(final String path, final char splitChar) {
        this(Lists.newArrayList(Guard.notNull(Splitter.on(splitChar).split(path))));
    }

    public FaultLocation(final List<String> path) {
        this.path = Guard.notNull(path);
        this.fullPath = this.initFullPath(this.path);
        this.subPaths = this.initSubPaths(this.path);
    }

    private String initFullPath(final List<String> path) {
        return joiner.join(Guard.notNull(path));
    }

    private Set<String> initSubPaths(final List<String> path) {

        final Set<String> paths = new HashSet<>();

        // store all sub paths for checks
        final StringBuilder buf = new StringBuilder();
        boolean first = true;
        for (final String s : path) {
            if (first) first = false;
            else buf.append(JOIN_CHAR);
            buf.append(s);
            paths.add(buf.toString());
        }

        return paths;
    }

    public List<String> getPath() {
        return this.path;
    }

    public String getFullPath() {
        return this.fullPath;
    }

    public boolean includes(final FaultLocation location) {
        return this.subPaths.contains(location.getFullPath());
    }

    public int compareTo(final FaultLocation o) {
        return this.getFullPath().compareTo(o.getFullPath());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        final FaultLocation that = (FaultLocation) o;

        if (this.path != null ? !this.path.equals(that.path) : that.path != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.path != null ? this.path.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.fullPath;
    }

}
