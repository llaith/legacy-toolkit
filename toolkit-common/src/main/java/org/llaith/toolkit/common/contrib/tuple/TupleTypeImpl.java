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
package org.llaith.toolkit.common.contrib.tuple;


class TupleTypeImpl implements TupleType {


    final Class<?>[] types;

    TupleTypeImpl(final Class<?>[] types) {
        this.types = (types != null ? types : new Class<?>[0]);
    }

    @Override
    public int size() {
        return this.types.length;
    }

    // WRONG
    // public <T> Class<T> getNthType(int i)

    // RIGHT - thanks Emil
    @Override
    public Class<?> getNthType(final int i) {
        return this.types[i];
    }


    @SuppressWarnings("boxing")
    @Override
    public Tuple createTuple(final Object... values) {
        if ((values == null && this.types.length == 0) ||
                (values != null && values.length != this.types.length)) {
            throw new IllegalArgumentException(
                    "Expected " + this.types.length + " values, not " +
                            (values == null ? "(null)" : values.length) + " values");
        }

        if (values != null) {
            for (int i = 0; i < this.types.length; i++) {
                final Class<?> nthType = this.types[i];
                final Object nthValue = values[i];
                if (nthValue != null && !nthType.isAssignableFrom(nthValue.getClass())) {
                    throw new IllegalArgumentException(
                            "Expected value #" + i + " ('" +
                                    nthValue + "') of new Tuple to be " +
                                    nthType + ", not " +
                                    (nthValue != null ? nthValue.getClass() : "(null type)"));
                }
            }
        }

        return new TupleImpl(this,values);
    }
}
