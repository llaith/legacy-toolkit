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
package org.llaith.toolkit.common.etc;

/**
 *
 */
public class Counter {

    private final int start;

    private int counter;

    public Counter() {
        this(0);
    }

    public Counter(final int start) {
        this.start = start;
        this.counter = start;
    }

    public int nextValue() {
        return this.counter++;
    }

    public int currentValue() {
        return this.counter;
    }

    public boolean isFirst() {
        return this.start == this.counter;
    }

    public boolean notFirst() {
        // not delegate to isFirst because already quite a performance impact from normal anyway.
        return this.start != this.counter;
    }

}
