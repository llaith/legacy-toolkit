/*******************************************************************************

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

 ******************************************************************************/
package org.llaith.toolkit.common.pattern.impl;

import org.llaith.toolkit.common.pattern.ResourceLoan;

/**
 *
 */
public class ResourceLoanReleaseWrapper<T> implements ResourceLoan<T> {

    public static <T> ResourceLoan<T> wrap(final ResourceLoan<T> delegate) {
        return new ResourceLoanReleaseWrapper<>(delegate);
    }

    private final ResourceLoan<T> delegate;

    private boolean released = false;

    public ResourceLoanReleaseWrapper(final ResourceLoan<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T target() {
        if (released) return null;
        return this.delegate.target();
    }

    @Override
    public void releaseTarget() {
        this.delegate.releaseTarget();
        this.released = true;
    }

}
