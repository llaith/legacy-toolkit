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
package org.llaith.toolkit.common.pattern;

import org.llaith.toolkit.common.lang.TypedAutoCloseable;

/**
 * Fundamental pattern for any object that needs to be borrowed and returned.
 * This can be anything from a single resource you want to lock before use,
 * or a traditional pool of something that is sessioned like connections, or
 * things you want to check in and out of some sort of repository.
 */
public interface ResourceManager<T> extends TypedAutoCloseable<RuntimeException> {

    ResourceLoan<T> acquire();

}
