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
package org.llaith.toolkit.common.meta;

/**
 * Distinction between add & replace because metadata is designed to be added
 * between modules that don't know of each other, and that can make for
 * unfortunate conflicts. Also no clear, only remove so that a module can only
 * remove metadata it knows about.
 */
public interface MetadataContainer {

    <T> T addMetadata(T metadata);

    <T> T replaceMetadata(T metadata);

    <T> T metadata(Class<T> metadataClass);

    boolean hasMetadata(Class<?> metadataClass);

    <T> T removeMetadata(Class<T> metadataClass);

}
