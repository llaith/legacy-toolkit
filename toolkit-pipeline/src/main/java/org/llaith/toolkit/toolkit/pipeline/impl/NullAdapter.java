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
package org.llaith.toolkit.toolkit.pipeline.impl;

import org.llaith.toolkit.session.AbstractNullSession;
import org.llaith.toolkit.toolkit.pipeline.Adapter;
import org.llaith.toolkit.toolkit.pipeline.AdapterException;

/**
 *
 */
public class NullAdapter<T> extends AbstractNullSession implements Adapter<T,T> {

    @Override
    public T adapt(T in) throws AdapterException {
        return in;
    }

}
