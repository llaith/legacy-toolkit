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
package org.llaith.toolkit.core.repository.filter;

import com.google.common.base.Predicate;
import org.llaith.toolkit.common.meta.MetadataContainer;

import javax.annotation.Nullable;

/**
 *
 */
public class WithMetadataFilter<T extends MetadataContainer> implements Predicate<T> {

    public static <X extends MetadataContainer> WithMetadataFilter<X> withMetadata(final Class<?> metadataClass) {
        return new WithMetadataFilter<>(metadataClass);
    }

    private final Class<?> metadataClass;

    public WithMetadataFilter(final Class<?> metadataClass) {
        this.metadataClass = metadataClass;
    }

    @Override
    public boolean apply(@Nullable final T input) {
        return (input != null) && input.hasMetadata(this.metadataClass);
    }

}
